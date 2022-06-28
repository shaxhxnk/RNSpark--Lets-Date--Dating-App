package com.shaxhxnk.rnspark.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager



import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.shaxhxnk.rnspark.Chat
import com.shaxhxnk.rnspark.R
import com.shaxhxnk.rnspark.User
import com.shaxhxnk.rnspark.activities.TinderCallback
import com.shaxhxnk.rnspark.adapters.ChatsAdapter
import com.shaxhxnk.rnspark.util.DATA_MATCHES
import kotlinx.android.synthetic.main.fragment_matches.*

class MatchesFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference
    private lateinit var chatDatabase: DatabaseReference
    private var callback: TinderCallback? = null

    private val chatsAdapter = ChatsAdapter(ArrayList())

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase()
        chatDatabase = callback.getChatDatabase()

        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matchesRV.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = chatsAdapter
        }
    }

    fun fetchData() {
        userDatabase.child(userId).child(DATA_MATCHES).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.hasChildren()) {
                    p0.children.forEach { child ->
                        val matchId = child.key
                        val chatId = child.value.toString()
                        if(!matchId.isNullOrEmpty()) {
                            userDatabase.child(matchId).addListenerForSingleValueEvent(object: ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    val user = p0.getValue(User::class.java)
                                    if(user != null) {
                                        val chat = Chat(userId, chatId, user.uid, user.name, user.imageUrl)
                                        chatsAdapter.addElement(chat)
                                    }
                                }

                            })
                        }
                    }
                }
            }

        })
    }

}
