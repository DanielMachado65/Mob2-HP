package br.com.mob2_hp.utils

import br.com.mob2_hp.api.HpApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://hp-api.onrender.com/api/"

    val service: HpApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HpApiService::class.java)
    }
}