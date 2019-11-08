'use strict'
const Generator = require('yeoman-generator')
const chalk = require('chalk')
const yosay = require('yosay')
const _ = require('lodash')

module.exports = class extends Generator {

  constructor (args, opts) {
    super(args, opts)
    this.props = {}
    this.sayYo = _.has(opts, 'subCall')
  }

  initializing () {
    this.placeholder = {projectName: '', packageName: '', springData: ''}
    this.xmlJson = null
  }

  prompting () {
    if (!this.sayYo) {
      // Have Yeoman greet the user.
      this.log(yosay(
        'Welcome to majestic ' + chalk.red('spring-rest-commons:project') + ' generator!'
      ))
    }

    var defaultName = this.appname

    const prompts = [{
      type: 'input',
      name: 'projectName',
      message: 'Your project name (normally same with your foldername you perform yeoman:project generator)',
      default: defaultName.replace(/[\-\ ].*/gi, ''),
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
      message: 'Base code package structure of project',
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
      type: 'input',
      name: 'pomGroupId',
      message: 'groupId of maven modules',
      default: () => {
        return this.placeholder.packageName
      },
      validate: (input) => {
        if (!(/^([a-z0-9_\.]*)$/.test(input))) {
          return chalk.red('The pomPackage cannot contain special characters and all lowercase')
        } else if (input === '') {
          return chalk.red('The pomPackage name cannot be empty')
        }
        return true
      }
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
      type: 'confirm',
      name: 'auth',
      message: 'With commons-auth (spring-security via JWT)',
      default: true
    }]

    return this.prompt(prompts)
      .then(answers => {
        this.props = _.assign(answers, this.props)
        this.props.projectName = answers.projectName.toLowerCase()
          .replace(/\ /g, '-')
        this.props.packageName = answers.packageName.toLowerCase()
        // some transformations
        this.props.basePath = this.props.packageName.replace(/\./g, '/')
        this.props.mongoDb = answers.springData === 'mongodb'

        if (answers.auth) {
          this.props.auth = true
          this.props.jwtSecret = Buffer.from(String.fromCodePoint(...Array.from({length: 64}, () => Math.floor(Math.random() * 57) + 65))).toString('base64')
        } else {
          this.props.auth = false
        }
      })
  }

  writing () {
    var props = _.assign({
      springBootVersion: '2.2.0.RELEASE',
      mapstructVersion: '1.3.1.Final',
      commonsRestVersion: '1.10.1',
      commonsAuthVersion: '2.6.1'
    }, this.props)
    var copy = this.fs.copy.bind(this.fs)
    var copyTpl = this.fs.copyTpl.bind(this.fs)
    var tPath = this.templatePath.bind(this)
    var dPath = this.destinationPath.bind(this)

    copy(tPath('_.gitignore'), dPath('.gitignore'))
    copyTpl(tPath('_pom.xml'), dPath('pom.xml'), props)
    copyTpl(tPath('_README.md'), dPath('README.md'), props)

    // api
    copyTpl(tPath('api/_pom.xml'), dPath(props.projectName + '-api/pom.xml'), props)

    // model
    copyTpl(tPath('model/_pom.xml'), dPath(props.projectName + '-model/pom.xml'), props)
    copyTpl(tPath('model/java/package/converter/_CentralConfig.java'), dPath(props.projectName + '-model/src/main/java/' + props.basePath + '/converter/CentralConfig.java'), props)

    // server
    copyTpl(tPath('server/_pom.xml'), dPath(props.projectName + '-server/pom.xml'), props)
    copyTpl(tPath('server/java/package/_Application.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/Application.java'), props)
    if (this.props.auth) {
      copyTpl(tPath('server/java/package/config/_AuditDbConfig.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/config/AuditDbConfig.java'), props)
      copyTpl(tPath('server/java/package/config/_SecurityConfig.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/config/SecurityConfig.java'), props)
      copyTpl(tPath('server/java/package/initializer/_UserInitializer.java'), dPath(props.projectName + '-server/src/main/java/' + props.basePath + '/initializer/UserInitializer.java'), props)
    }
    copyTpl(tPath('server/resources/_application.yml'), dPath(props.projectName + '-server/src/main/resources/application.yml'), props)
    copyTpl(tPath('server/resources/_application-pro.yml'), dPath(props.projectName + '-server/src/main/resources/application-pro.yml'), props)

  }

  install () {
  }
}
