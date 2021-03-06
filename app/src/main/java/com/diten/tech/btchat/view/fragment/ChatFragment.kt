package com.diten.tech.btchat.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diten.tech.btchat.R
import com.diten.tech.btchat.adapter.ChatAdapter
import com.diten.tech.btchat.model.Message


class ChatFragment : Fragment(), View.OnClickListener {

    private lateinit var chatInput: EditText
    private lateinit var sendButton: FrameLayout
    private var communicationListener: CommunicationListener? = null
    private var chatAdapter: ChatAdapter? = null
    private lateinit var recyclerviewChat: RecyclerView
    private val messageList = arrayListOf<Message>()

    companion object {
        fun newInstance(): ChatFragment {
            val myFragment = ChatFragment()
            val args = Bundle()
            myFragment.arguments = args
            return myFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView: View  = LayoutInflater.from(activity).inflate(R.layout.fragment_chat, container, false)
        initViews(mView)
        return mView
    }




    @SuppressLint("UseRequireInsteadOfGet")
    private fun initViews(mView: View) {

        chatInput = mView.findViewById(R.id.chatInput)
        val chatIcon: ImageView = mView.findViewById(R.id.sendIcon)
        sendButton = mView.findViewById(R.id.sendButton)
        recyclerviewChat = mView.findViewById(R.id.chatRecyclerView)

        sendButton.isClickable = false
        sendButton.isEnabled = false

        val llm = LinearLayoutManager(activity)
        llm.reverseLayout = true
        recyclerviewChat.layoutManager = llm

        chatInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {

                if (s.isNotEmpty()) {
                    chatIcon.setImageDrawable(activity?.getDrawable(R.drawable.ic_send))
                    sendButton.isClickable = true
                    sendButton.isEnabled = true
                }else {
                    chatIcon.setImageDrawable(activity?.getDrawable(R.drawable.ic_send_depri))
                    sendButton.isClickable = false
                    sendButton.isEnabled = false
                }
            }
        })

        sendButton.setOnClickListener(this)


        chatAdapter = ChatAdapter(messageList.reversed(), activity!!)
        recyclerviewChat.adapter = chatAdapter

    }


    override fun onClick(v: View?) {
        if (chatInput.text.isNotEmpty()){
            communicationListener?.onCommunication(chatInput.text.toString())
            chatInput.setText("")
        }
    }

    fun setCommunicationListener(communicationListener: CommunicationListener){
        this.communicationListener = communicationListener
    }

    interface CommunicationListener{
        fun onCommunication(message: String)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun communicate(message: Message){
        messageList.add(message)
        if(activity != null) {
            chatAdapter = ChatAdapter(messageList.reversed(), activity!!)
            recyclerviewChat.adapter = chatAdapter
            recyclerviewChat.scrollToPosition(0)
        }
    }
}