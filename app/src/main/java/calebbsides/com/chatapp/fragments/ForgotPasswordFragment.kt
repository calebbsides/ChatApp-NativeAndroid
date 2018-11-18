package calebbsides.com.chatapp.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import calebbsides.com.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.forgot_password_fragment.*
import android.widget.Toast

class ForgotPasswordFragment : Fragment() {
    private lateinit var mAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.forgot_password_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        reset_password.setOnClickListener {
            resetPassword()
        }

        back_to_login.setOnClickListener {
            backToLogin()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun resetPassword() {
        val email = forgot_email_input.text.toString().trim()

        if(email == "") {
            Toast.makeText(
                view!!.context, "Please enter a valid email",
                Toast.LENGTH_SHORT
            ).show()
        }

        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        view!!.context, "Password Reset",
                        Toast.LENGTH_SHORT
                    ).show()
                    backToLogin()
                } else {
                    Toast.makeText(
                        view!!.context, "Please enter a valid email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun backToLogin() {
        val ft = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.forgot_password_fragment, SignInFragment())
        ft.commit()
    }
}