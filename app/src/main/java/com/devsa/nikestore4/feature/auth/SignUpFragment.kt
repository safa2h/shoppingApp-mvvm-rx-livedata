package com.devsa.nikestore4.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeFragment

class SignUpFragment:NikeFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpBtn=view.findViewById<Button>(R.id.signUPBtn)
        val emailEt=view.findViewById<EditText>(R.id.emailEt_sign_up)
        val passEt=view.findViewById<EditText>(R.id.passwordEt_sign_up)

        val goToSingIn=view.findViewById<Button>(R.id.loginLinkBtn)
        goToSingIn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer,LoginFragment())
            }.commit()
        }

    }

}