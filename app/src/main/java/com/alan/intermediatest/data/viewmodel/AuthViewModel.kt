package com.alan.intermediatest.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alan.intermediatest.data.repository.UserRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {

    private var currentUser = MutableLiveData<FirebaseUser>()

    init {
        currentUser = UserRepository.getUser()
    }

    //Login with facebook
    fun loginWithFacebook(token: AccessToken) {
        UserRepository.loginWithFacebook(token)
    }

    //function to perform login
    fun createWithEmailAndPassword(email: String, password: String) {
        UserRepository.createWithEmailAndPassword(email, password)
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return currentUser
    }


}