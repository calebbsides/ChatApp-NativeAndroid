package calebbsides.com.chatapp.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import calebbsides.com.chatapp.R

class ProfileFragment : Fragment() {

    private lateinit var profileView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileView = inflater!!.inflate(R.layout.profile_fragment, container, false)
        return profileView
    }
}