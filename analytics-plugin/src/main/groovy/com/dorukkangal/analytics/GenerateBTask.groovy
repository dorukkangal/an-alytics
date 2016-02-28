package com.dorukkangal.analytics

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * @author Doruk Kangal
 * @since 2.0.0
 */
class GenerateBTask extends DefaultTask {

    String rFilePath
    String bDirectoryPath
    String packageName

    @TaskAction
    void generate() {

        File rFile = project.file(rFilePath)
        File bFile = project.file(bDirectoryPath)

        try {
            BindingClassBuilder.brewJava(rFile, bFile, packageName)
        } catch (Exception e) {
            throw new GradleException("Error creating B file.", e)
        }
    }
}
