# <%= projectName %>

Generated project with [generator-spring-rest-commons](https://github.com/rocketbase-io/generator-spring-rest-commons)

## project-structure

* <%= projectName %>-api
  * containing DTOs and REST-resources
  * could be used in other projects to consume REST endpoints of server
* <%= projectName %>-server
  * containg controller, entity-model, converter, spring-data repositories, and SpringApplication
  * used to provide REST-endpoints via spring boot


## dependencies

* [spring-boot](https://projects.spring.io/spring-boot/):*<%= springBootVersion%>*
* [mapstruct](http://mapstruct.org/):*<%= mapstructVersion%>*
* [commons-rest](https://github.com/rocketbase-io/commons-rest):*<%= commonsRestVersion%>*

## usage

```bash
# install project
mvn install

# run spring-boot app
cd <%= projectName %>-server
mvn spring-boot:run 
```