import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = AndroidSdk.compile
    defaultConfig {
        applicationId = "com.mrebollob.drawaday"
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        allWarningsAsErrors = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlin.RequiresOptIn", "-Xopt-in=kotlin.Experimental"
        )
    }
}

dependencies {
    add(
        PLUGIN_CLASSPATH_CONFIGURATION_NAME,
        "androidx.compose.compiler:compiler:${Versions.compose}"
    )
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")

    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha01")
    implementation("androidx.activity:activity-compose:1.3.0-alpha08")

    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiTooling)
    implementation(Compose.foundationLayout)
    implementation(Compose.material)
    implementation(Compose.navigation)
    implementation(Compose.accompanist)
    implementation(Compose.accompanistSwiperefresh)
    implementation(Compose.accompanistInsets)
    implementation(Compose.accompanistSystemuicontroller)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.compose)

    implementation("org.osmdroid:osmdroid-android:6.1.10")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.3.0")
    testImplementation("org.robolectric:robolectric:4.4")
    androidTestImplementation("androidx.test:runner:1.3.0")

    implementation(project(":shared"))
}
