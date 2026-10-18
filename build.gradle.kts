// Top-level build file. Versions live in gradle/libs.versions.toml.
// AGP 9 built-in Kotlin: the org.jetbrains.kotlin.android plugin is intentionally NOT applied.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}
