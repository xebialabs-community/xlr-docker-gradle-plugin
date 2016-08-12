package com.xebialabs.gradle.plugins.xlr.docker

import org.gradle.api.Project


class DockerPluginExtension {
    String version;
    List<Download> downloads

    DockerPluginExtension(Project project) {
        this.downloads = new ArrayList<Download>()
    }

    Download download(String name, String user, String password, Closure closure) {
        Download download = new Download(name, user, password, closure)
        downloads.add(download)
        return download
    }

    Download download(String name, Closure closure) {
        Download download = new Download(name, closure)
        downloads.add(download)
        return download
    }
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