package com.devsa.nikestore4.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeFragment
import com.devsa.nikestore4.feature.auth.AuthActivity
import com.devsa.nikestore4.feature.favorites.FavoritActivity
import com.devsa.nikestore4.feature.order.OrderHistoryActivity
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment:NikeFragment() {
    private val viewModel: ProfileViewmodel by viewModel()
    lateinit var favTV: TextView;
    lateinit var orderHistory: TextView
    lateinit var authTv: TextView
    lateinit var userName: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favTV = view.findViewById(R.id.fav_frag)
        orderHistory = view.findViewById(R.id.orderHistory)
        authTv = view.findViewById(R.id.authTv)
        userName = view.findViewById(R.id.userNameTv)
        favTV.setOnClickListener {
        startActivity(Intent(requireContext(),FavoritActivity::class.java))
        }
        orderHistory.setOnClickListener {

            startActivity(Intent(requireContext(),OrderHistoryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (viewModel.isSIginedIn) {
            authTv.text = "خروج از حساب کاربری"
            userName.text=viewModel.userName
            authTv.setOnClickListener {
                viewModel.siginOut()
                checkAuthState()
            }
        } else {
            authTv.text = "ورود به حساب کاربری"
            userName.text="کاربر مهمان"
            authTv.setOnClickListener {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
        }
    }
}