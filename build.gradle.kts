// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.detekt)
}

apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
detekt{
    parallel = true
    config.setFrom(rootProject.files("config/detekt/detekt.yml"))
//    config.setFrom(files("${project.rootDir}/config/detekt/detekt.yml"))
}

subprojects {
    apply(plugin = rootProject.libs.plugins.gradle.ktlint.get().pluginId)
    ktlint {
        verbose.set(true)
        android.set(true)
        filter {
            exclude("**/generated/**")
        }
    }
}