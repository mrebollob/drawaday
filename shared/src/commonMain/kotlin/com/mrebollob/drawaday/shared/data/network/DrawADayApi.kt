package com.mrebollob.drawaday.shared.data.network

import com.mrebollob.drawaday.shared.data.network.model.DrawImageApiModel
import io.ktor.client.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class DrawADayApi(
    private val client: HttpClient,
    private val baseUrl: String = "https://drawaday-6b6a5-default-rtdb.firebaseio.com",
) : KoinComponent {
    suspend fun fetchDrawImages(startAt: Int, endAt: Int): Map<String, DrawImageApiModel> =
        client.get("$baseUrl/images.json") {
            parameter("orderBy", "\"index\"")
            parameter("startAt", startAt)
            parameter("endAt", endAt)
        }
}
