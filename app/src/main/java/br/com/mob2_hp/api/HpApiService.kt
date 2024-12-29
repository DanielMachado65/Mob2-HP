package br.com.mob2_hp.api

import retrofit2.http.GET
import retrofit2.http.Path
import br.com.mob2_hp.model.Character

interface HpApiService {
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): List<Character>

    @GET("characters/staff")
    suspend fun getStaff(): List<Character>

    @GET("characters/house/{house}")
    suspend fun getStudents(@Path("house") house: String): List<Character>

    @GET("characters")
    suspend fun getAllCharacters(): List<Character>
}
