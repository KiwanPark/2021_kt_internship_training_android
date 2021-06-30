package com.sideblind.newstest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sideblind.newstest.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var auth:FirebaseAuth
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        settingGoogleLogin()
        settingFacebookLogin()
    }

    private fun settingFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        binding.facebookSignInButton.setReadPermissions("email")

        // Callback registration
        binding.facebookSignInButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                handleFacebookAccessToken(loginResult?.accessToken!!)
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(FACEBOOK_LOGIN_TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(FACEBOOK_LOGIN_TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(FACEBOOK_LOGIN_TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun settingGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun googleLogin() {
        Log.e(GOOGLE_LOGIN_TAG, "googleLogin")
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(GOOGLE_LOGIN_TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(GOOGLE_LOGIN_TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(GOOGLE_LOGIN_TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(GOOGLE_LOGIN_TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser!=null) {
            Log.e(
                "update UI",
                """
                ${firebaseUser.uid}
                ${firebaseUser.email}
                ${firebaseUser.displayName}
                ${firebaseUser.providerData[0]?.providerId}
                ${firebaseUser.providerData[1]?.providerId}
                ${firebaseUser.photoUrl}
                ${firebaseUser.phoneNumber}
            """.trimIndent()
            )

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun onClick(view: View) {
        when(view) {
            binding.googleSignInButton -> {
                Log.e(GOOGLE_LOGIN_TAG, "googleSignInButton")
                googleLogin()
            }
        }
    }

    companion object{
        const val RC_SIGN_IN = 1001
        const val GOOGLE_LOGIN_TAG: String = "Google Login"
        const val FACEBOOK_LOGIN_TAG: String = "Facebook Login"
    }
}