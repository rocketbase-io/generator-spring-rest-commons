'use strict'
const Generator = require('yeoman-generator')
const chalk = require('chalk')
const yosay = require('yosay')
const _ = require('lodash')

module.exports = class extends Generator {
  initializing () {
    this.props = {}
  }

  prompting () {
    // Have Yeoman greet the user.
    this.log(yosay(
      'Welcome to the majestic ' + chalk.red('spring-rest-commons') + ' generator!'
    ))


    const prompts = [{
      type: 'list',
      name: 'whatToDo',
      message: 'What would you like to do?',
      choices: [
        {
          value: 'project',
          name: 'create a new project'
        },
        {
          value: 'service',
          name: 'create a service'
        }
      ],
      default: 'project'
    }]

    return this.prompt(prompts)
               .then(answers => {
                 this.props = _.assign(answers, this.props)
               })
  }

  writing () {

  }

  install () {
  }

  end () {
    if (this.props.whatToDo === 'project') {
      this.composeWith(require.resolve('../project'), {subCall: true})
    } else {
      this.composeWith(require.resolve('../service'), {subCall: true})
    }
  }
}
