import java.io.ByteArrayOutputStream

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
    kotlin("android")
}

android {
    compileSdk = Versions.androidCompileSdk

    defaultConfig {
        applicationId = "com.mrebollob.drawaday"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk

        versionCode = computeVersionCode()
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("releasetestkey.keystore")
            storePassword = System.getenv("KSTOREPWD")
            keyAlias = "ReleaseTestKey"
            keyPassword = System.getenv("KEYPWD")
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            firebaseAppDistribution {
                artifactType = "AAB"
            }
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
        allWarningsAsErrors = false
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {
    with(Deps.Android) {
        implementation(material)
        implementation(osmdroidAndroid)
    }

    with(Deps.AndroidX) {
        implementation(lifecycleRuntimeKtx)
        implementation(lifecycleViewmodelKtx)
        implementation(activityCompose)
    }

    with(Deps.Compose) {
        implementation(ui)
        implementation(uiGraphics)
        implementation(uiTooling)
        implementation(foundationLayout)
        implementation(material)
        implementation(navigation)

        implementation(iconsExtended)

        implementation(accompanistCoil)
        implementation(accompanistPlaceholder)
        implementation(accompanistSwipeRefresh)
        implementation(accompanistInsets)
        implementation(accompanistSystemUIController)
    }

    with(Deps.Koin) {
        implementation(core)
        implementation(android)
        implementation(compose)
        testImplementation(test)
        testImplementation(testJUnit4)
    }

    implementation("com.google.accompanist:accompanist-pager:${Versions.accompanist}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist}")

    implementation("com.google.firebase:firebase-analytics-ktx:19.0.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.1.0")

    implementation(Deps.Utils.klock)

    implementation(project(":shared"))
}

fun computeVersionCode(): Int {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-list", "--count", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim().toInt()
}
