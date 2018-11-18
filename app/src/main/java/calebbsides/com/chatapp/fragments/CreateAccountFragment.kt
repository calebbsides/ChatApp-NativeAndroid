package calebbsides.com.chatapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import calebbsides.com.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.create_account_fragment.*
import com.google.firebase.auth.FirebaseUser
import calebbsides.com.chatapp.MainActivity

class CreateAccountFragment : Fragment() {
    private lateinit var mAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        create_submit.setOnClickListener {
            createUser()
        }

        back_to_login.setOnClickListener {
            backToLogin()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun createUser() {
        val email = create_email_input.text.toString().trim()
        val password = create_password_input.text.toString().trim()
        val passwordConfirm = create_password_confirm_input.text.toString().trim()

        if(email == "" || password == "" || passwordConfirm == "") {
            Toast.makeText(view!!.context, "Please enter a valid email",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if(password != passwordConfirm) {
            Toast.makeText(view!!.context, "Passwords do not match",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    startApp(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        view!!.context, "Please enter a valid email",
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

    private fun backToLogin() {
        val ft = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.create_account_fragment, SignInFragment())
        ft.commit()
    }
}