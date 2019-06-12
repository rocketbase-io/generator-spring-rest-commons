# generator spring rest commons

![logo](https://github.com/rocketbase-io/generator-spring-rest-commons/raw/master/assets/generator-commons-rest.svg?sanitize=true)

[![npm](https://nodei.co/npm/generator-spring-rest-commons.png?mini=true)](https://www.npmjs.com/package/generator-spring-rest-commons)
[![Build Status](https://travis-ci.org/rocketbase-io/generator-spring-rest-commons.svg?branch=master)](https://travis-ci.org/rocketbase-io/generator-spring-rest-commons)

Scaffold your spring-boot application and generate services. The generated code is based on [rocketbase-io/commons-rest](https://github.com/rocketbase-io/commons-rest).

## features

- setup project with actual spring-boot and commons-rest
- easy flow to create new service (generated DTOs, Repository, Controller, Converter etc.)
- you can choose between JPA or MongoDB

## technology

* [spring-boot](https://projects.spring.io/spring-boot/) application
* [MongoDB](https://www.mongodb.com/) via spring-data-mongodb or MySQL via spring-data-jpa
* [mapstruct](http://mapstruct.org/) to convert between DTOs and Entity
* [commons-rest](https://github.com/rocketbase-io/commons-rest) to simplify CRUD coding


## usage

```bash
# installation
npm install -g yo
npm install -g rocketbase/generator-spring-rest-commons
# usage
yo spring-rest-commons
```

