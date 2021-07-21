import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    compileSdk = Versions.androidCompileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
                implementation(Deps.Kotlinx.coroutinesCore) {
                    isForce = true
                }

                with(Deps.Ktor) {
                    implementation(clientCore)
                    implementation(clientJson)
                    implementation(clientLogging)
                    implementation(clientSerialization)
                }

                with(Deps.Kotlinx) {
                    implementation(serializationCore)
                }

                with(Deps.SqlDelight) {
                    implementation(runtime)
                    implementation(coroutineExtensions)
                }

                with(Deps.Koin) {
                    api(core)
                    api(test)
                }

                with(Deps.Log) {
                    api(kermit)
                }

                with(Deps.Utils) {
                    api(klock)
                }
            }
        }
        val commonTest by getting

        val androidMain by getting {
            dependencies {
                implementation(Deps.Ktor.clientAndroid)
                implementation(Deps.SqlDelight.androidDriver)
            }
        }

        val androidTest by getting

        val iosMain by getting {
            dependencies {
                implementation(Deps.Ktor.clientIos)
                implementation(Deps.SqlDelight.nativeDriver)
            }
        }
        val iosTest by getting
    }
}

tasks.withType<KotlinCompile> {
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
