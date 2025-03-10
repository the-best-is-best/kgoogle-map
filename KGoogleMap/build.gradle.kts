import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
//    alias(libs.plugins.native.cocoapods)
    id("maven-publish")
    id("signing")
    alias(libs.plugins.maven.publish)
}




apply(plugin = "maven-publish")
apply(plugin = "signing")


tasks.withType<PublishToMavenRepository> {
    val isMac = getCurrentOperatingSystem().isMacOsX
    onlyIf {
        isMac.also {
            if (!isMac) logger.error(
                """
                    Publishing the library requires macOS to be able to generate iOS artifacts.
                    Run the task on a mac or use the project GitHub workflows for publication and release.
                """
            )
        }
    }
}



mavenPublishing {
    coordinates("io.github.the-best-is-best", "kgoogle-map", "1.1.0")

    publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
    signAllPublications()

    pom {
        name.set("KGoogleMap")
        description.set("KGoogleMap provides a unified API that allows developers to implement Google Maps functionalities in both Android and iOS applications with minimal platform-specific code.")
        url.set("https://github.com/the-best-is-best/kgoogle-map")
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://opensource.org/licenses/Apache-2.0")
            }
        }
        issueManagement {
            system.set("GITHUB")
            url.set("https://github.com/the-best-is-best/kgoogle-map/issues")
        }
        scm {
            connection.set("https://github.com/the-best-is-best/kgoogle-map.git")
            url.set("https://github.com/the-best-is-best/kgoogle-map")
        }
        developers {
            developer {
                id.set("MichelleRaouf")
                name.set("Michelle Raouf")
                email.set("eng.michelle.raouf@gmail.com")

            }
        }
    }

}


signing {
    useGpgCmd()
    sign(publishing.publications)
}


kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                    freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_17}")
                }
            }
        }
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)
            dependencies {
                debugImplementation(libs.androidx.testManifest)
                implementation(libs.androidx.junit4)
            }
        }
    }

//    jvm()
//
//    js {
//        browser()
//        binaries.executable()
//    }
//
//    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//        binaries.executable()
//    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "KGoogleMap"
            isStatic = true


            binaryOption("bundleId", "io.github.KGoogleMap")
        }
        it.compilations["main"].cinterops {
            val kgooglemap by creating {
                defFile(project.file("interop/kgooglemap.def"))
                packageName("io.github.native.kgooglemap")
            }

            val googleplaces by creating {
                defFile(project.file("interop/google_places.def"))
                packageName("io.github.native.google_places")
            }
        }

    }


    sourceSets {

        all {
            languageSettings.apply {

                progressiveMode = true
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                if (name.lowercase().contains("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                    optIn("kotlinx.cinterop.BetaInteropApi")
                }
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            api(libs.klocation)

        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activityCompose)
            implementation(libs.google.maps)
            implementation(libs.play.services.location)
            implementation(libs.accompanist.permissions)
            implementation(libs.google.maps.utils)


            implementation(project.dependencies.platform(libs.kotlin.bom))

            implementation(libs.map.places)

        }

//        jvmMain.dependencies {
//            implementation(compose.desktop.currentOs)
//        }

//        jsMain.dependencies {
//            implementation(compose.html.core)
//        }

        iosMain.dependencies {

        }

    }

}

android {
    namespace = "io.github.KGoogleMap"
    compileSdk = 35

    defaultConfig {
        minSdk = 23

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        buildFeatures {
            //enables a Compose tooling support in the AndroidStudio
            compose = true

        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.github.KGoogleMap.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}
