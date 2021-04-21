package com.alan.intermediatest.ui.views.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.alan.intermediatest.R
import com.alan.intermediatest.ui.base.BaseFragment
import com.alan.intermediatest.ui.base.MainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_auth.*


class AuthFragment : BaseFragment() {
    companion object {
        private const val TAG = "AuthFragment"
    }

    private lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).supportActionBar!!.hide()

        setupFacebookButton()
        setupButtonListener()
        observeData()
    }

    private fun setupButtonListener() {
        btn_login.setOnClickListener {
            if (edt_email.text.toString().isEmpty() || edt_password.text.toString().isEmpty()) {
                Toasty.success(
                    requireContext(),
                    getString(R.string.no_blank_inputs),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                authViewModel.createWithEmailAndPassword(
                    edt_email.text.toString(),
                    edt_password.text.toString()
                )
            }
        }
    }

    private fun setupFacebookButton() {
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()

        btn_facebook.setReadPermissions("email", "public_profile")
        btn_facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                authViewModel.loginWithFacebook(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
    }

    private fun observeData() {
        authViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                popBackStack()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


}
