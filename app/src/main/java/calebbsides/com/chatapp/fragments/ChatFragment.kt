package calebbsides.com.chatapp.fragments

import calebbsides.com.chatapp.Message
import calebbsides.com.chatapp.R
import calebbsides.com.chatapp.adapters.MessageAdapter
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.chat_fragment.*

class ChatFragment : Fragment() {
    private val db = FirebaseDatabase.getInstance()
    private val ref = db.getReference("messages")
    private var messageList : MutableList<Message> = mutableListOf()
    private var initializedLog = false
    private lateinit var mAuth : FirebaseAuth
    private  lateinit var user : FirebaseUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth  = FirebaseAuth.getInstance()
        user = mAuth.currentUser!!

        listenForMessages()

        sendMessageBtn.setOnClickListener {
            writeMessage()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeLog(data : DataSnapshot) {
        for (x in data.children) {
            val message = x.getValue(Message::class.java)
            messageList.add(message!!)
        }

        initializedLog = true;
    }

    private fun writeMessage() {
        val input = messageInput.text.toString().trim()

        if(input != "") {
            val id = ref.push().key.toString()
            val message = Message(id, input, user.email!!)
            ref.child(id).setValue(message)
        }

        messageInput.setText("")
    }

    private fun listenForMessages() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                if(data.exists()) {
                    if (!initializedLog) {
                        initializeLog(data)
                    } else {
                        val lastMessage = data.children.last().getValue(Message::class.java)
                        messageList.add(lastMessage!!)
                    }

                    val adapter = MessageAdapter(view!!.context, R.layout.chat_log_message, messageList)
                    chatLog.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}