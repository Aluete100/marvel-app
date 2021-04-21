package com.alan.intermediatest.data.viewmodel

import androidx.lifecycle.*
import com.alan.intermediatest.data.models.CharactersResponse
import com.alan.intermediatest.data.repository.MarvelRepository
import com.alan.intermediatest.utils.Constants
import com.alan.intermediatest.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CharactersViewModel : ViewModel() {

    private val currentPage = MutableLiveData(1)

    fun getCharacters() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = MarvelRepository.getCharacters(
                        currentPage.value!!,
                        Constants.CHARACTERS_LIMIT
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }

    fun getCharacter(character: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = MarvelRepository.getCharacter(character)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }

    fun loadNextPageData(): LiveData<Resource<CharactersResponse>> {
        currentPage.value = currentPage.value!!.plus(1)
        return getCharacters()
    }

    fun resetPages() {
        currentPage.value = 1
    }
}