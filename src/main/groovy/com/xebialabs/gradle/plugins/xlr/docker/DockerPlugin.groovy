package com.xebialabs.gradle.plugins.xlr.docker

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import de.undercouch.gradle.tasks.download.Download

class DockerPlugin implements Plugin<Project> {
    public static final String COMPILE_DOCKER_TASK_NAME = "compileDocker"
    public static final String RUN_DOCKER_TASK_NAME = "runDocker"

    public static final String COPY_DOWNLOADS_TASK_NAME = "copyDownloadResources"
    public static final String CLEAN_DOWNLOAD_CACHE_TASK_NAME = "cleanDownloadCache"
    public static final String DOWNLOAD_RESOURCES_TASK_NAME = "downloadResources"

    @Override
    void apply(Project project) {
        // Apply the base plugin to get cleaning behaviour
        project.apply plugin: "base"
        project.apply plugin: 'de.undercouch.download'

        DockerPluginExtension dockerPluginExtension = project.extensions.create("xlrDocker", DockerPluginExtension)
        Task downloadTasks = defineDownloadTasks(project, dockerPluginExtension)

        project.afterEvaluate {
            Task compileTask = createDockerTask(project, COMPILE_DOCKER_TASK_NAME, ["run", "-v", project.getRootDir().absolutePath + ":/data",  "-v", System.getProperty("user.home") + "/.xlgradle:/root/.gradle", "xebialabs/xlr_dev_compile:"+dockerPluginExtension.version])
            Task runTask = createDockerTask(project, RUN_DOCKER_TASK_NAME, ["run", "-p", "5516:5516", "-v", project.getRootDir().absolutePath + ":/data", "-v", System.getProperty("user.home") + "/xl-licenses:/license",  "xebialabs/xlr_dev_run:"+dockerPluginExtension.version])
            runTask.dependsOn compileTask
            compileTask.dependsOn downloadTasks
        }

        createCleanDownloadCacheTask(project)
    }

    private Task createCleanDownloadCacheTask(Project project) {
        return project.tasks.create(CLEAN_DOWNLOAD_CACHE_TASK_NAME, Delete).configure {
            delete project.fileTree(dir: 'src/downloads/plugins', exclude: '**/*.gitignore')
        }
    }


    private static Task createDockerTask(Project project, String taskName, Iterable<?> taskArgs) {
        return project.tasks.create(taskName, Exec).configure {
            executable "docker"
            args(taskArgs)
            workingDir project.getProjectDir()
        }
    }

    private Task defineDownloadTasks(Project project, DockerPluginExtension dockerPluginExtension) {
        def Task lastDownloadTask
        def Task firstDownloadTask
        dockerPluginExtension.downloads.each() { download ->
            def downloadTask = project.task("${DOWNLOAD_RESOURCES_TASK_NAME}_${download.name}", type: Download).configure {
                overwrite false
                username download.user
                password download.password
            }

            downloadTask.configure(download.closure)
            if (firstDownloadTask == null) {
                firstDownloadTask = downloadTask
            }

            if (lastDownloadTask != null) {
                lastDownloadTask.dependsOn downloadTask
            }
            lastDownloadTask = downloadTask
        }

        if (firstDownloadTask != null) {
            def copyDownloadsTask = project.task("${COPY_DOWNLOADS_TASK_NAME}_${project.name}", type: Copy).configure {
                from('src') {
                    include 'downloads/**/*'
                }
                into "$project.buildDir/downloads"
            }

            copyDownloadsTask.dependsOn firstDownloadTask
            firstDownloadTask = copyDownloadsTask
        }
        firstDownloadTask
    }

}