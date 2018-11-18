package calebbsides.com.chatapp.fragments

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import calebbsides.com.chatapp.AuthActivity
import calebbsides.com.chatapp.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {
    private lateinit var mAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        user_name.text = user!!.email

        sign_out.setOnClickListener {
            signUserOut(user)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun signUserOut(user: FirebaseUser) {
        AuthUI.getInstance()
            .signOut(view!!.context)
            .addOnCompleteListener {
                val intent = Intent(view!!.context, AuthActivity::class.java)
                startActivity(intent)
            }
    }
}