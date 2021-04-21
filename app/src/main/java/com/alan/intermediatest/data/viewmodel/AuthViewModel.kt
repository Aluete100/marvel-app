package com.alan.intermediatest.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alan.intermediatest.data.repository.UserRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {

//    val loggedUser by lazy {
//        UserRepository.currentUser()
//    }

    private var loginResult = MutableLiveData<Boolean>()
    var loggedUser = MutableLiveData<FirebaseUser>()

    //Login with facebook
    fun loginWithFacebook(token: AccessToken) { //: LiveData<Boolean>
        loginResult = UserRepository.loginWithFacebook(token)
//        return loginResult
    }

    //function to perform login
    fun createWithEmailAndPassword(email: String, password: String){ //: LiveData<Boolean>
        loginResult = UserRepository.createWithEmailAndPassword(email, password)
        loggedUser.postValue(UserRepository.currentUser())
//        return loginResult
    }

    fun logout() {
        UserRepository.logout()
    }


}