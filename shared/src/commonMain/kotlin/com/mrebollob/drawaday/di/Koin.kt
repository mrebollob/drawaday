package com.mrebollob.drawaday.di

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.remote.DrawADayApi
import com.mrebollob.drawaday.repository.DrawADayRepository
import com.mrebollob.drawaday.repository.platformModule
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), enableNetworkLogs = enableNetworkLogs) }
    single { DrawADayRepository() }
    single { DrawADayApi(get()) }
    single { Kermit(logger = get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}
