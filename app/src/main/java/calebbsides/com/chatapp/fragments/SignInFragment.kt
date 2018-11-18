package calebbsides.com.chatapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import calebbsides.com.chatapp.MainActivity
import calebbsides.com.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.signin_fragment.*

class SignInFragment : Fragment() {
    private lateinit var mAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        login_submit.setOnClickListener {
            signUserIn()
        }

        create_account.setOnClickListener {
            val ft = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.signin_fragment, CreateAccountFragment())
            ft.commit()
        }

        forgot_password.setOnClickListener {
            val ft = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.signin_fragment, ForgotPasswordFragment())
            ft.commit()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun signUserIn() {
        val email = email_input.text.toString().trim()
        val password = password_input.text.toString().trim()

        if(email == "" || password == "") {
            Toast.makeText(view!!.context, "Incorrect email or password",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        //Sign User In
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    startApp(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(view!!.context, "Incorrect email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun startApp(user : FirebaseUser?) {
        if(user !== null) {
            val intent = Intent(view!!.context, MainActivity::class.java)
            startActivity(intent)
        }
    }

}