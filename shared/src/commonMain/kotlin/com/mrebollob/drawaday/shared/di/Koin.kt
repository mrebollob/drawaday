package com.mrebollob.drawaday.shared.di

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.shared.data.DrawADayRepositoryImp
import com.mrebollob.drawaday.shared.data.DrawADayRepositoryNativeImp
import com.mrebollob.drawaday.shared.data.UserRepositoryImp
import com.mrebollob.drawaday.shared.data.network.DrawADayApi
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepositoryNative
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
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
        modules(
            commonModule(enableNetworkLogs = enableNetworkLogs),
            platformModule()
        )
    }

// called by iOS
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), enableNetworkLogs = enableNetworkLogs) }
    single<DrawADayRepository> { DrawADayRepositoryImp() }
    single<DrawADayRepositoryNative> { DrawADayRepositoryNativeImp(get(), get()) }
    single<UserRepository> { UserRepositoryImp(get(), get()) }
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
            level = LogLevel.ALL
        }
    }
}
