object Versions {
    const val androidMinSdk = 21
    const val androidCompileSdk = 30
    const val androidTargetSdk = androidCompileSdk

    const val kotlin = "1.5.10"
    const val kotlinCoroutines = "1.5.0-native-mt"
    const val ktor = "1.6.1"
    const val kotlinxSerialization = "1.2.1"
    const val koin = "3.1.2"
    const val sqlDelight = "1.5.0"

    const val compose = "1.0.0-rc02"
    const val nav_compose = "2.4.0-alpha04"
    const val accompanist = "0.9.0"

    const val material = "1.4.0"
    const val activityCompose = "1.3.0-rc02"
    const val lifecycleKtx = "2.4.0-alpha01"
    const val lifecycleRuntimeKtx = lifecycleKtx
    const val lifecycleViewmodelKtx = lifecycleKtx
    const val osmdroidAndroid = "6.1.10"

    const val junit = "4.13"
    const val testRunner = "1.3.0"

    const val slf4j = "1.7.30"
    const val logback = "1.2.3"
    const val kermit = "0.1.9"
}

object Deps {
    object AndroidSdk {
        const val min = 21
        const val compile = 30
        const val target = compile
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val foundationLayout =
            "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
        const val iconsExtended =
            "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val accompanistCoil =
            "com.google.accompanist:accompanist-coil:${Versions.accompanist}"
        const val accompanistSwipeRefresh =
            "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
        const val accompanistInsets =
            "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
        const val accompanistSystemUIController =
            "com.google.accompanist:accompanist-systemuicontroller:0.14.0"
        const val accompanistPlaceholder =
            "com.google.accompanist:accompanist-placeholder-material:0.12.0"
    }

    object Kotlinx {
        const val serializationCore =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    }

    object Android {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val osmdroidAndroid = "org.osmdroid:osmdroid-android:${Versions.osmdroidAndroid}"
    }

    object AndroidX {
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
        const val lifecycleViewmodelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtx}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val testJUnit4 = "io.insert-koin:koin-test-junit4:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"

        const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
        const val clientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
    }

    object SqlDelight {
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutineExtensions =
            "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
    }

    object Log {
        const val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
        const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    }

    object Utils {
        const val klock = "com.soywiz.korlibs.klock:klock:2.1.2"
    }
}
