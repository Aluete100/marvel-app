package com.alan.intermediatest.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alan.intermediatest.data.models.CharactersResponse
import com.alan.intermediatest.data.models.EventsResponse
import com.alan.intermediatest.data.repository.MarvelRepository
import com.alan.intermediatest.utils.Constants
import com.alan.intermediatest.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class EventsViewModels : ViewModel() {
    private val currentPage = MutableLiveData(1)

    fun getEvents() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = MarvelRepository.getEvents(
                        currentPage.value!!,
                        Constants.EVENTS_LIMIT
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }

    fun loadNextPageData(): LiveData<Resource<EventsResponse>> {
        currentPage.value = currentPage.value!!.plus(1)
        return getEvents()
    }

    fun resetPages() {
        currentPage.value = 1
    }
}