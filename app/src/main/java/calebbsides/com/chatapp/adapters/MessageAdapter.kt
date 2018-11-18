package calebbsides.com.chatapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import calebbsides.com.chatapp.Message
import calebbsides.com.chatapp.R

class MessageAdapter(val ctx: Context, val layoutID: Int, val messageList: List<Message>)
    : ArrayAdapter<Message>(ctx, layoutID, messageList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val li = LayoutInflater.from(ctx)
        val view = li.inflate(layoutID, null)
        val messageView = view.findViewById<TextView>(R.id.myMessage)
        val message = messageList[position]

        messageView.text = message.data
        return view
    }
}