package com.blockbasti.cragtrackapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blockbasti.cragtrackapp.MainActivity
import com.blockbasti.cragtrackapp.R
import com.blockbasti.cragtrackapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.loginSignin.setOnClickListener {
            signin()
        }

        binding.loginSigninGoogle.setOnClickListener {
            signinGoogle()
        }

        binding.loginNewAccount.setOnClickListener {
            createNewAccount()
        }

        binding.loginResetPassword.setOnClickListener {
            resetPassword()
        }

        binding.loginSkipAccount.setOnClickListener {
            signinAnonymous()
        }

        mAuth = FirebaseAuth.getInstance()
        mAuth.useAppLanguage()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }

    private fun signinAnonymous() {
        mAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    analyticsSignUp("anonymous")
                    gotoMain()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun resetPassword() {
        if (binding.loginEmail.text.isBlank()) {
            binding.loginEmail.error =
                getString(R.string.error_login_empty_email)
            return
        }
        mAuth.sendPasswordResetEmail(binding.loginEmail.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val bundle = Bundle()
                    bundle.putString("email", binding.loginEmail.text.toString())
                    firebaseAnalytics.logEvent("reset_password", bundle)
                    Toast.makeText(
                        baseContext, getText(R.string.login_sent_reset_mail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun createNewAccount() {
        if (!checkFields()) return
        mAuth.createUserWithEmailAndPassword(
            binding.loginEmail.text.toString(),
            binding.loginPassword.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                analyticsSignUp("email")
                gotoMain()
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    baseContext, task.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        var currentUser = mAuth.currentUser
        if (currentUser != null) {
            gotoMain()
        }
    }

    private fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun signin() {
        if (!checkFields()) return
        mAuth.signInWithEmailAndPassword(
            binding.loginEmail.text.toString(),
            binding.loginPassword.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                analyticsSignIn("email")
                gotoMain()
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    baseContext, task.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun signinGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 0)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 0) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    if (task.result?.additionalUserInfo?.isNewUser!!) {
                        analyticsSignUp("google")
                    } else {
                        analyticsSignIn("google")
                    }
                    gotoMain()

                } else {
                    // If sign in fails, display a message to the user.
                    Snackbar.make(binding.root, "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }

                // ...
            }
    }


    private fun checkFields(): Boolean {
        if (binding.loginEmail.text.isBlank()) binding.loginEmail.error =
            getString(R.string.error_login_empty_email)
        if (binding.loginPassword.text.isBlank()) binding.loginPassword.error =
            getString(R.string.error_login_empty_password)
        if (binding.loginEmail.text.isBlank() || binding.loginPassword.text.isBlank()) return false
        return true
    }

    fun analyticsSignUp(method: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.METHOD, method)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)
    }

    fun analyticsSignIn(method: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.METHOD, method)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
    }

}
