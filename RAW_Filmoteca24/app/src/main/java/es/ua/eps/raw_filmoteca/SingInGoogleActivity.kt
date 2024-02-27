package es.ua.eps.raw_filmoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingInGoogleActivity : AppCompatActivity() {
    // Use your app or activity context to instantiate a client instance of
// CredentialManager.
    private lateinit var credentialManager: CredentialManager
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in_google)

        credentialManager = CredentialManager.create(this)
        loginButton = findViewById(R.id.loginButton)
        // Assuming you have a button for login, you can initiate the login process here
        // For example:
        loginButton.setOnClickListener {
            initiateLogin()
        }
    }

    private fun initiateLogin() {
        val getPasswordOption = GetPasswordOption()
        val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(requestJson = "")

        val getCredRequest = GetCredentialRequest(listOf(getPasswordOption, getPublicKeyCredentialOption))

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = getCredentialAsync(getCredRequest)
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                handleFailure(e)
            }
        }
    }

    private suspend fun getCredentialAsync(request: GetCredentialRequest): GetCredentialResponse {
        return withContext(Dispatchers.IO) {
            credentialManager.getCredential(context = applicationContext, request = request)
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential

        when (credential) {
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
                // Handle PublicKeyCredential response
            }
            is PasswordCredential -> {
                val username = credential.id
                val password = credential.password
                // Handle PasswordCredential response
            }
            else -> {
                // Handle other credential types if needed
            }
        }
    }

    private fun handleFailure(exception: GetCredentialException) {
        // Handle failure here
    }
}