package es.ua.eps.raw_filmoteca

import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task


class SignInGoogleActivity : AppCompatActivity() {

    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var mGoogleSignInClient : GoogleSignInClient
=======
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
>>>>>>> ff5a6506e38c99ca365b611f5ab9deb01392f08e

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in_google)

<<<<<<< HEAD
        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener {
            signIn()
        }

        // Configurar opciones de inicio de sesión de Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id_gCloud))
            .requestEmail()
            .build()
=======
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
>>>>>>> ff5a6506e38c99ca365b611f5ab9deb01392f08e

        val getCredRequest = GetCredentialRequest(listOf(getPasswordOption, getPublicKeyCredentialOption))

<<<<<<< HEAD
        // Inicializar el cliente de Google SignIn
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.i("tagg", "account: " + account)
            // Signed in successfully, show authenticated UI.
            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("error", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }

    override fun onStart() {
        super.onStart()
        googleApiClient.connect()
    }

    override fun onStop() {
        super.onStop()
        if (googleApiClient.isConnected) {
            googleApiClient.disconnect()
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
=======
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
>>>>>>> ff5a6506e38c99ca365b611f5ab9deb01392f08e
    }
}