package calebbsides.com.chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
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

                    val adapter = MessageAdapter(applicationContext, R.layout.chatlog, messageList)
                    chatLog.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listenForMessages()

        sendMessageBtn.setOnClickListener {
            writeMessage()
        }
    }
}
