package com.tech.cursor.presentation.common.google

import android.content.Context
import android.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

class GoogleAuthenticator(
    private val context : Context,
    private val credentialManager : CredentialManager
) {


    fun login(){
        val googleIdOption  = GetGoogleIdOption.Builder()
            .setServerClientId("")

    }
}