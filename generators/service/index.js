'use strict'
const Generator = require('yeoman-generator')
const chalk = require('chalk')
const yosay = require('yosay')
const fs = require('fs')
const _ = require('lodash')

module.exports = class extends Generator {

  constructor (args, opts) {
    super(args, opts)
    this.props = {fields: []}
    this.sayYo = _.has(opts, 'subCall')
  }

  initializing () {
    this.placeholder = {name: ''}
  }

  prompting () {
    if (!this.sayYo) {
      // Have Yeoman greet the user.
      this.log(yosay(
        'Welcome to majestic ' + chalk.red('spring-rest-commons:service') + ' generator!'
      ))
    }

    const prompts = [{
      type: 'input',
      name: 'projectName',
      message: 'Your project name (needs to be same with project-generator!)',
      validate: (input) => {
        if (!(/^([a-z0-9_\-]*)$/.test(input))) {
          return chalk.red('The projectName cannot contain special characters and all lowercase')
        } else if (/^.*\-(api|server)$/.test(input)) {
          return chalk.red('api/server will get added automatically to submodules - please remove it!')
        } else if (input === '') {
          return chalk.red('The projectName name cannot be empty')
        }
        this.placeholder.projectName = input
        return true
      },
      store: true
    }, {
      type: 'input',
      name: 'packageName',
      message: 'Base code package structure of project (needs to be same with project-generator!)',
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
      message: 'Name of Entity',
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
    }]

    return this.prompt(prompts)
      .then((answers) => {
        // To access props later use this.props.someAnswer;
        this.props = _.assign(answers, this.props)

        this.props.mongoDb = answers.springData === 'mongodb'
        this.props.idClass = this.props.mongoDb ? 'String' : 'Long'

        this.props.entityCamelCase = _.lowerFirst(answers.entityName)
        this.props.entityKebabCase = _.kebabCase(answers.entityName)
        this.props.entitySnakeCaseUpper = _.kebabCase(answers.entityName).replace('-','_').toUpperCase()

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
    copyTpl(tPath('api/java/package/dto/entity/_EntityRead.java'), dPath(props.projectName + '-api/src/main/java/' + props.basePath + '/dto/' + props.entityFolder + '/' + props.entityName + 'Read.java'), props)
    copyTpl(tPath('api/java/package/dto/entity/_EntityWrite.java'), dPath(props.projectName + '-api/src/main/java/' + props.basePath + '/dto/' + props.entityFolder + '/' + props.entityName + 'Write.java'), props)
    copyTpl(tPath('api/java/package/resource/_EntityResource.java'), dPath(props.projectName + '-api/src/main/java/' + props.basePath + '/resource/' + props.entityName + 'Resource.java'), props)

    // server
    copyTpl(tPath('server/java/package/controller/_EntityController.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/controller/' + props.entityName + 'Controller.java'), props)
    copyTpl(tPath('server/java/package/converter/_EntityConverter.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/converter/' + props.entityName + 'Converter.java'), props)
    copyTpl(tPath('server/java/package/model/_EntityEntity.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/model/' + props.entityName + 'Entity.java'), props)
    copyTpl(tPath('server/java/package/repository/_EntityRepository.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/repository/' + props.entityName + 'Repository.java'), props)
  }

  install () {
  }
}
