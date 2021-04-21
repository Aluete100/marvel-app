package com.alan.intermediatest.data.repository

import androidx.lifecycle.MutableLiveData
import com.alan.intermediatest.data.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object MarvelRepository {

    suspend fun getCharacters(currentPage: Int, pageSize: Int) = RetrofitClient.apiInterface.getCharacters(currentPage, pageSize)

    suspend fun getCharacter(characterId: Int) = RetrofitClient.apiInterface.getCharacter(characterId)

    suspend fun getEvents(currentPage: Int, pageSize: Int) = RetrofitClient.apiInterface.getEvents(currentPage, pageSize)

}