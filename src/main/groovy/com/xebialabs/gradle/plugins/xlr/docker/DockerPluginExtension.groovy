package com.xebialabs.gradle.plugins.xlr.docker

class DockerPluginExtension {
    String version;
    List<Download> downloads
}

class Download {
    Closure closure
    String name
    String user
    String password
    Download(String name, String user=null, String password=null, Closure closure) {
        this.closure = closure
        this.name = name
        this.user = user
        this.password = password
    }
}