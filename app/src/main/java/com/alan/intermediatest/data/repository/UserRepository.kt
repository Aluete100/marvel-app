package com.alan.intermediatest.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alan.intermediatest.data.firebase.FirebaseSource
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

object UserRepository {
    private const val TAG = "UserRepository"

    private var auth: FirebaseAuth = Firebase.auth
    private var currentUser = MutableLiveData<FirebaseUser>()

    init {
        currentUser.value = auth.currentUser
    }

    fun loginWithFacebook(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d(TAG, "handleFacebookAccessToken:success")
                    currentUser.value = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "handleFacebookAccessToken:failure", task.exception)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "handleFacebookAccessToken:Failed ${it.localizedMessage}")
            }
    }

    fun createWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d(TAG, "signInWithCredential:success")
                    currentUser.value = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    fun getUser(): MutableLiveData<FirebaseUser> {
        return currentUser
    }

}