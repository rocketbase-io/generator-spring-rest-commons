'use strict'
const Generator = require('yeoman-generator')
const chalk = require('chalk')
const yosay = require('yosay')
const _ = require('lodash')
const fs = require('fs')
const parseString = require('xml2js').parseString

module.exports = class extends Generator {

  constructor (args, opts) {
    super(args, opts)
    this.props = {fields: []}
    this.sayYo = _.has(opts, 'subCall')
    this.apiPathName = null
    this.serverPathName = null
    this.springData = null
  }

  initializing () {
    this.placeholder = {name: '', packageName: ''}
  }

  prompting () {
    if (!this.sayYo) {
      // Have Yeoman greet the user.
      this.log(yosay(
        'Welcome to majestic ' + chalk.red('spring-rest-commons:service') + ' generator!'
      ))
    }

    // detect modules
    try {
      if (fs.existsSync(this.destinationPath('pom.xml'))) {
        let pomContent = fs.readFileSync(this.destinationPath('pom.xml'), 'utf8')
        parseString(pomContent, (err, result) => {
          this.xmlJson = result
          let modules = result.project.modules[0].module
          try {
            this.placeholder.packageName = result.project.groupId
          } catch (e) {
          }
          if (modules.length === 2) {
            let apiModules = modules.filter(m => (/\-api/gi).test(m))
            if (apiModules.length === 1) {
              this.apiPathName = apiModules[0]
              this.serverPathName = modules.filter(m => m !== this.apiPathName)
            } else {
              let serverModules = modules.filter(m => (/\-(server|rest)/gi).test(m))
              if (serverModules.length === 1) {
                this.serverPathName = serverModules[0]
                this.apiPathName = modules.filter(m => m !== this.serverPathName)
              }
            }

            if (this.serverPathName !== null && fs.existsSync(this.destinationPath(this.serverPathName + '/pom.xml'))) {
              let pomServerContent = fs.readFileSync(this.destinationPath(this.serverPathName + '/pom.xml'), 'utf8')
              parseString(pomServerContent, (errServer, resultServer) => {
                let dependencies = resultServer.project.dependencies[0].dependency
                if (dependencies.filter(d => (/\-mongodb/gi).test(d.artifactId)).length >= 1) {
                  this.springData = 'mongodb'
                } else if (dependencies.filter(d => (/\-jpa/gi).test(d.artifactId)).length >= 1) {
                  this.springData = 'jpa'
                }
              })
            }

          }
        })
      }
    } catch (e) {
      console.error('couldn\'t parse pom.xml')
    }

    const prompts = [{
      when: () => {return this.apiPathName === null},
      type: 'input',
      name: 'projectName',
      message: 'Your project name (needs to be same with project-generator!)?',
      validate: (input) => {
        if (!(/^([a-z0-9_\-]*)$/.test(input))) {
          return chalk.red('The projectName cannot contain special characters and all lowercase')
        } else if (/^.*\-(api|server)$/.test(input)) {
          return chalk.red('api/server will get added automatically to submodules - please remove it!')
        } else if (input === '') {
          return chalk.red('The projectName name cannot be empty')
        }
        this.placeholder.projectName = input

        this.apiPathName = input + '-api'
        this.serverPathName = input + '-server'

        return true
      },
      store: true
    }, {
      default: () => {return this.placeholder.packageName},
      type: 'input',
      name: 'packageName',
      message: 'Base code package structure of project (needs to be same with project-generator!)?',
      validate: (input) => {
        if (!(/^([a-z0-9_\.]*)$/.test(input))) {
          return chalk.red('The packageName cannot contain special characters and all lowercase')
        } else if (input === '') {
          return chalk.red('The packageName name cannot be empty')
        }
        this.placeholder.packageName = input
        return true
      },
      store: true
    }, {
      when: () => {return this.springData === null},
      type: 'list',
      name: 'springData',
      message: 'Which Spring-Data version?',
      choices: [
        {
          value: 'mongodb',
          name: 'MongoDB'
        },
        {
          value: 'jpa',
          name: 'JPA (with MySQL)'
        }
      ],
      default: 'mongodb',
      store: true
    }, {
      type: 'input',
      name: 'entityName',
      message: 'Name of Entity?',
      validate: (input) => {
        if (!(/^([a-zA-Z0-9_]*)$/.test(input))) {
          return chalk.red('The entity name cannot contain special characters')
        } else if ((/^[0-9].*$/.test(input))) {
          return chalk.red('The entity name cannot start with a number')
        } else if ((/^[a-z]*$/.test(input.charAt(0)))) {
          return chalk.red('The entity name cannot with lowercase')
        } else if (input === '') {
          return chalk.red('The entity name cannot be empty')
        }
        this.placeholder.name = input
        return true
      }
    }, {
      type: 'confirm',
      name: 'isChild',
      message: 'Is entity child of other (different controller/resource layout)?',
      default: false
    }, {
      when: response => response.isChild,
      type: 'input',
      name: 'parentEntityName',
      message: 'Name of parent Entity?'
    }, {
      type: 'confirm',
      name: 'isCustom',
      message: 'Custom implementation without use of AbstractCrudController of commons-rest?',
      default: false
    }]

    return this.prompt(prompts)
      .then((answers) => {
        // To access props later use this.props.someAnswer;
        this.props = _.assign(answers, this.props)

        this.props.mongoDb = (this.springData !== null ? this.springData : answers.springData) === 'mongodb'
        this.props.idClass = this.props.mongoDb ? 'String' : 'Long'

        this.props.entityCamelCase = _.lowerFirst(answers.entityName)
        this.props.entityKebabCase = _.kebabCase(answers.entityName)
        this.props.entitySnakeCaseUpper = _.kebabCase(answers.entityName).replace('-', '_').toUpperCase()

        if (this.props.isChild) {
          this.props.parentName = answers.parentEntityName
          this.props.parentCamelCase = _.lowerFirst(answers.parentEntityName)
          this.props.parentKebabCase = _.kebabCase(answers.parentEntityName)
          this.props.parentSnakeCaseUpper = _.kebabCase(answers.parentEntityName).replace('-', '_').toUpperCase()
        }

        this.props.entityFolder = answers.entityName.toLowerCase()
        // some transformations
        this.props.basePath = this.props.packageName.replace(/\./g, '/')
      })
  }

  writing () {
    var props = this.props
    var copy = this.fs.copy.bind(this.fs)
    var copyTpl = this.fs.copyTpl.bind(this.fs)
    var tPath = this.templatePath.bind(this)
    var dPath = this.destinationPath.bind(this)

    // api
    copyTpl(tPath('api/java/package/dto/entity/_EntityRead.java'), dPath(this.apiPathName + '/src/main/java/' + props.basePath + '/dto/' + props.entityFolder + '/' + props.entityName + 'Read.java'), props)
    copyTpl(tPath('api/java/package/dto/entity/_EntityWrite.java'), dPath(this.apiPathName + '/src/main/java/' + props.basePath + '/dto/' + props.entityFolder + '/' + props.entityName + 'Write.java'), props)
    if (props.isChild) {
      copyTpl(tPath('api/java/package/resource/_EntityResourceChild.java'), dPath(this.apiPathName + '/src/main/java/' + props.basePath + '/resource/' + props.entityName + 'Resource.java'), props)
    } else {
      copyTpl(tPath('api/java/package/resource/_EntityResource.java'), dPath(this.apiPathName + '/src/main/java/' + props.basePath + '/resource/' + props.entityName + 'Resource.java'), props)
    }
    // server
    if (props.isChild) {
      if (props.isCustom) {
        copyTpl(tPath('server/java/package/controller/_EntityCustomControllerChild.java'), dPath(this.serverPathName + '/src/main/java/' + props.basePath + '/controller/' + props.entityName + 'Controller.java'), props)
      } else {
        copyTpl(tPath('server/java/package/controller/_EntityControllerChild.java'), dPath(this.serverPathName + '/src/main/java/' + props.basePath + '/controller/' + props.entityName + 'Controller.java'), props)
      }
    } else {
      if (props.isCustom) {
        copyTpl(tPath('server/java/package/controller/_EntityCustomController.java'), dPath(this.serverPathName + '/src/main/java/' + props.basePath + '/controller/' + props.entityName + 'Controller.java'), props)
      } else {
        copyTpl(tPath('server/java/package/controller/_EntityController.java'), dPath(this.serverPathName + '/src/main/java/' + props.basePath + '/controller/' + props.entityName + 'Controller.java'), props)
      }
    }
    copyTpl(tPath('server/java/package/converter/_EntityConverter.java'), dPath(this.serverPathName + '/src/main/java/' + props.basePath + '/converter/' + props.entityName + 'Converter.java'), props)
    copyTpl(tPath('server/java/package/model/_EntityEntity.java'), dPath(this.serverPathName + '/src/main/java/' + props.basePath + '/model/' + props.entityName + 'Entity.java'), props)
    copyTpl(tPath('server/java/package/repository/_EntityRepository.java'), dPath(this.serverPathName + '/src/main/java/' + props.basePath + '/repository/' + props.entityName + 'Repository.java'), props)
  }

  install () {
  }
}
