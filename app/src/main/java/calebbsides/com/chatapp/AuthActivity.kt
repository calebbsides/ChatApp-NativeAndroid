package calebbsides.com.chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import calebbsides.com.chatapp.fragments.SignInFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Check if user is signed in (non-null) and navigate accordingly.
        val currentUser = mAuth.currentUser
        startApp(currentUser)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.auth_activity, SignInFragment())
        ft.commit()
    }

    private fun startApp(user : FirebaseUser?) {
        if(user !== null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
