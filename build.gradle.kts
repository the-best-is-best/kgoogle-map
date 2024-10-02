plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.native.cocoapods).apply(false)
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.map.secret).apply(false)

    alias(libs.plugins.maven.publish)
}

