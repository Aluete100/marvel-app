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
    private var loginResult = MutableLiveData<Boolean>()


    fun loginWithFacebook(token: AccessToken): MutableLiveData<Boolean> {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    loginResult.value = true
                    Log.d(TAG, "signInWithEmailAndPassword:success")
                } else {
                    // If sign in fails, display a message to the user.
                    loginResult.value = false
                    Log.w(TAG, "signInWithEmailAndPassword:failure", task.exception)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "signInWithEmailAndPassword:Failed ${it.localizedMessage}")
            }
        return loginResult
    }

    fun createWithEmailAndPassword(email: String, password: String): MutableLiveData<Boolean> {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    loginResult.value = true
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    loginResult.value = false
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
        return loginResult
    }

    fun currentUser(): FirebaseUser? = auth.currentUser

    fun logout() = auth.signOut()

}