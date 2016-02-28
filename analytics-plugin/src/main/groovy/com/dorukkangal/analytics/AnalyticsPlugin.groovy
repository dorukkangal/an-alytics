package com.dorukkangal.analytics

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

/**
 * @author Doruk Kangal
 * @since 1.0.0
 */
class AnalyticsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("com.android.application or com.android.library plugin required.")
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants

            project.afterEvaluate {

                String packageName = findPackageName(project)

                // The Android variants are only available at this point.
                addGenerateTasks(project, packageName)
            }
        }

        variants.all { variant ->
            JavaCompile javaCompile = variant.javaCompile
            variant.javaCompile.doLast {
                String[] args = [
                        "-showWeaveInfo",
                        "-1.5",
                        "-inpath", javaCompile.destinationDir.toString(),
                        "-aspectpath", javaCompile.classpath.asPath,
                        "-d", javaCompile.destinationDir.toString(),
                        "-classpath", javaCompile.classpath.asPath,
                        "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)
                ]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true);
                new Main().run(args, handler);
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break;
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break;
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break;
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break;
                    }
                }
            }
        }
    }

    /**
     * Adds generateB tasks to the project.
     */
    private addGenerateTasks(Project project, String packageName) {
        getNonTestVariants(project).each { variant ->
            addTaskForVariant(project, variant, packageName)
        }
    }

    /**
     * Creates generateB task for a variant in an Android project.
     */
    private addTaskForVariant(Project project, Object variant, String packageName) {

        String taskName = 'generate' + variant.name.capitalize() + 'B'
        String rFilePath = 'build/generated/source/r/' + variant.dirName + '/' +
                packageName.replace('.', '/') + '/R.java'

        String bDirectoryPath = 'build/generated/source/b/' + variant.dirName

        GenerateBTask task = project.tasks.create(taskName, GenerateBTask)
        task.rFilePath = rFilePath
        task.bDirectoryPath = bDirectoryPath
        task.packageName = packageName

        variant.outputs.each { output ->
            if (output.name == variant.name) {
                task.dependsOn(output.processResources)
            }
        }

        variant.javaCompile.options.compilerArgs << "-Arespackagename=" + packageName

        variant.javaCompile.dependsOn(task)
        variant.registerJavaGeneratingTask(task, project.file(bDirectoryPath))
    }

    private Object getNonTestVariants(Project project) {
        return project.android.hasProperty('libraryVariants') ?
                project.android.libraryVariants : project.android.applicationVariants
    }

    /**
     * Helper method that parses the manifest file and returns package name
     *
     * @return package name defined in manifest file
     */
    private String findPackageName(Project project) {
        File manifestFile = project.android.sourceSets.main.manifest.srcFile
        return (new XmlParser()).parse(manifestFile).@package
    }
}
