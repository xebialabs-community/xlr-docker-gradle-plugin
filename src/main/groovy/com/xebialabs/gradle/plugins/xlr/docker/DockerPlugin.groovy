package com.xebialabs.gradle.plugins.xlr.docker

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Exec

class DockerPlugin implements Plugin<Project> {
    public static final String COMPILE_DOCKER_TASK_NAME = "compileDocker"
    public static final String RUN_DOCKER_TASK_NAME = "runDocker"

    @Override
    void apply(Project project) {
        // Apply the base plugin to get cleaning behaviour
        project.apply plugin: "base"

        DockerPluginExtension dockerPluginExtension = project.extensions.create("xlrDocker", DockerPluginExtension)

        project.afterEvaluate {
            Task compileTask = createDockerTask(project, COMPILE_DOCKER_TASK_NAME, ["run", "-v", project.getRootDir().absolutePath + ":/data",  "-v", System.getProperty("user.home") + "/.gradle:/.gradle", "xebialabs/xlr_dev_compile:"+dockerPluginExtension.version])
            Task runTask = createDockerTask(project, RUN_DOCKER_TASK_NAME, ["run", "-p", "5516:5516", "-v", project.getRootDir().absolutePath + ":/data", "-v", System.getProperty("user.home") + "/xl-licenses:/license",  "-v", System.getProperty("user.home") + "/.gradle:/.gradle", "xebialabs/xlr_dev_run:"+dockerPluginExtension.version])
            runTask.dependsOn compileTask
        }
    }


    private static Task createDockerTask(Project project, String taskName, Iterable<?> taskArgs) {
        return project.tasks.create(taskName, Exec).configure {
            executable "docker"
            args(taskArgs)
            workingDir project.getProjectDir()
        }
    }
}