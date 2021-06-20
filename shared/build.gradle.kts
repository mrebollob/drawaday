import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.squareup.sqldelight")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

// CocoaPods requires the podspec to have a version.
version = "1.0"

android {
    compileSdk = AndroidSdk.compile
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
    }

    // Workaround from https://youtrack.jetbrains.com/issue/KT-43944
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    val sdkName: String? = System.getenv("SDK_NAME")

    val isiOSDevice = sdkName.orEmpty().startsWith("iphoneos")
    if (isiOSDevice) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    android()

    cocoapods {
        summary = "Draw a day"
        homepage = "https://github.com/mrebollob/drawaday-android"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}") {
                    isForce = true
                }

                implementation(Ktor.clientCore)
                implementation(Ktor.clientJson)
                implementation(Ktor.clientLogging)
                implementation(Ktor.clientSerialization)

                implementation(Serialization.core)

                implementation(SqlDelight.runtime)
                implementation(SqlDelight.coroutineExtensions)

                api(Koin.core)
                api(Koin.test)

                implementation(Deps.klock)
                api(Deps.kermit)
            }
        }
        val commonTest by getting

        val androidMain by getting {
            dependencies {
                implementation(Ktor.clientAndroid)
                implementation(Ktor.slf4j)
                implementation(SqlDelight.androidDriver)
            }
        }

        val androidTest by getting

        val iosMain by getting {
            dependencies {
                implementation(Ktor.clientIos)
                implementation(SqlDelight.nativeDriver)
            }
        }
        val iosTest by getting
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

sqldelight {
    database("DrawADayDatabase") {
        packageName = "com.mrebollob.drawaday.db"
        sourceFolders = listOf("sqldelight")
    }
}

multiplatformSwiftPackage {
    packageName("DrawADay")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }
}
