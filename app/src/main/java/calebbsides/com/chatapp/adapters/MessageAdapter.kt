package calebbsides.com.chatapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import calebbsides.com.chatapp.Message
import calebbsides.com.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet



class MessageAdapter(val ctx: Context, val layoutID: Int, val messageList: List<Message>)
    : ArrayAdapter<Message>(ctx, layoutID, messageList) {

    private val mAuth  = FirebaseAuth.getInstance()
    private val user = mAuth.currentUser!!

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val li = LayoutInflater.from(ctx)
        val view = li.inflate(layoutID, null)
        val messageView = view.findViewById<TextView>(R.id.chat_message)
        val message = messageList[position]
        var bias = 0.0f


        val constraintLayout : ConstraintLayout = view.findViewById(R.id.chat_log_message)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        if(message.user == user.email!!) {
            bias = 1.0f
        }

        constraintSet.setHorizontalBias(R.id.chat_message, bias)
        constraintSet.applyTo(constraintLayout)

        messageView.text = message.data
        return view
    }
}