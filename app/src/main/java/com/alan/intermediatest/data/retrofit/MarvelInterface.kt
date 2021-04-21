package com.alan.intermediatest.data.retrofit

import com.alan.intermediatest.data.models.CharactersResponse
import com.alan.intermediatest.data.models.EventsResponse
import com.alan.intermediatest.utils.Constants
import retrofit2.http.*


interface MarvelInterface {
    @GET("characters?ts=${Constants.TS}&apikey=${Constants.API_KEY}&hash=${Constants.HASH}")
    suspend fun getCharacters(
        @Query("offset") page: Int,
        @Query("limit") pageSize: Int
    ): CharactersResponse

    @GET("characters/{characterId}?ts=${Constants.TS}&apikey=${Constants.API_KEY}&hash=${Constants.HASH}")
    suspend fun getCharacter(@Path("characterId") characterId: Int): CharactersResponse

    @GET("events?ts=${Constants.TS}&apikey=${Constants.API_KEY}&hash=${Constants.HASH}&orderBy=startDate")
    suspend fun getEvents(
        @Query("offset") page: Int,
        @Query("limit") pageSize: Int
    ): EventsResponse
}