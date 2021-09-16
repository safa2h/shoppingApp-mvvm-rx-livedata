package com.devsa.nikestore4.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeCompletableObserver
import com.devsa.nikestore4.common.NikeFragment
import com.devsa.nikestore4.data.TokenResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment:NikeFragment() {

    val authViewModel:AuthViewModel by viewModel()
    val compositeDisposable=CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frgmanet_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goToSingUP=view.findViewById<Button>(R.id.signUpLinkBtn)
        val login_btn=view.findViewById<Button>(R.id.loginBtn)

        val userName=view.findViewById<EditText>(R.id.emailEt)
        val password=view.findViewById<EditText>(R.id.passEt)

        login_btn.setOnClickListener { 
            authViewModel.login(userName.text.toString(),password.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                       requireActivity().finish()
                    }
                })
        }


        goToSingUP.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer,SignUpFragment())
            }.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}