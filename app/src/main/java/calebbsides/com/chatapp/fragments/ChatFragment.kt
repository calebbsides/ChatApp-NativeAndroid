package calebbsides.com.chatapp.fragments

import calebbsides.com.chatapp.Message
import calebbsides.com.chatapp.R
import calebbsides.com.chatapp.adapters.MessageAdapter
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.chat_fragment.*

class ChatFragment : Fragment() {
    private lateinit var chatView : View
    private val db = FirebaseDatabase.getInstance()
    private val ref = db.getReference("messages")
    private var messageList: MutableList<Message> = mutableListOf()
    private var initializedLog: Boolean = false


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

            val message = Message(id, input)
            ref.child(id).setValue(message)
        }

        messageInput.setText("")
    }

    private fun listenForMessages() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                if(data!!.exists()) {
                    if (!initializedLog) {
                        initializeLog(data)
                    } else {
                        val lastMessage = data.children.last().getValue(Message::class.java)
                        messageList.add(lastMessage!!)
                    }

                    val adapter = MessageAdapter(view!!.context, R.layout.chatlog, messageList)
                    chatLog.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chatView = inflater!!.inflate(R.layout.chat_fragment, container, false)
        return chatView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listenForMessages()

        sendMessageBtn.setOnClickListener {
            writeMessage()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}