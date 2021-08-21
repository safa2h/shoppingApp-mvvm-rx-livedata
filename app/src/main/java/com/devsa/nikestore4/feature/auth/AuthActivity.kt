package com.devsa.nikestore4.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devsa.nikestore4.R
import org.greenrobot.eventbus.util.ErrorDialogManager

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer,LoginFragment())
        }.commit()
    }
}