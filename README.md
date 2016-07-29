# CI status #

[![Build Status][xlr-docker-gradle-travis-image] ][xlr-docker-gradle-travis-url]
[![Codacy Badge][xlr-docker-gradle-codacy-image] ][xlr-docker-gradle-codacy-url]
[![Code Climate][xlr-docker-gradle-code-climate-image] ][xlr-docker-gradle-code-climate-url]

[xlr-docker-gradle-travis-image]: https://travis-ci.org/xebialabs-community/xlr-docker-gradle-plugin.svg?branch=master
[xlr-docker-gradle-travis-url]: https://travis-ci.org/xebialabs-community/xlr-docker-gradle-plugin
[xlr-docker-gradle-codacy-image]: https://api.codacy.com/project/badge/Grade/bd1525ac6d1a4788832d7f9849ddfa73
[xlr-docker-gradle-codacy-url]: https://www.codacy.com/app/joris-dewinne/xlr-docker-gradle-plugin
[xlr-docker-gradle-code-climate-image]: https://codeclimate.com/github/xebialabs-community/xlr-docker-gradle-plugin/badges/gpa.svg
[xlr-docker-gradle-code-climate-url]: https://codeclimate.com/github/xebialabs-community/xlr-docker-gradle-plugin


# Overview #

The xlr-docker-gradle plugin allows to startup a Docker container with XLR installed to compile the plugin.
Also you can use `runDocker` to start an XLR container with your plugin preloaded.

# Requirements #

* **XL Release** 5.x
* **Gradle** 2.12+

# Installation #

Define on top of the `build.gradle` file:

```
plugins {
  id "com.xebialabs.xlr.docker" version "1.0.0"
}
```


For the latest version of the plugin have a look at:
[xlr-docker-gradle-plugin](https://plugins.gradle.org/plugin/com.xebialabs.xlr.docker)

# Usage #

You can make use of the following gradle tasks

* `compileDocker`
    * `version`: specifies which version of the XLR image to use. Default: 5.0.1.1
* `runDocker`
    * `version`: specifies which version of the XLR image to use. Default: 5.0.1.1
    * `initialize_data.sh`: If your project has a file `src/test/resources/docker/initialize/initialize_data.sh`, this will be run after startup. This allows you to create some dummy CI's for testing.
    * The `src/main/resources` folder will be linked into the XLR `ext` folder (so you don't have to restart on script changes)

