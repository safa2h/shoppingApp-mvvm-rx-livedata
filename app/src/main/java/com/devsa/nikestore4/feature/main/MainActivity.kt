package com.devsa.nikestore4.feature.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeActivitiy
import com.devsa.nikestore4.common.convertDpToPixel
import com.devsa.nikestore4.common.setupWithNavController
import com.devsa.nikestore4.data.CartItemCount
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : NikeActivitiy() {
    private var currentNavController: LiveData<NavController>? = null
    var bottomNavigationView: BottomNavigationView? = null
    val viewModel: MainActivityViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_main_bottom)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onResume() {
        super.onResume()

        viewModel.getCartItemCount()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {


        val navGraphIds = listOf(R.navigation.home, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_container,
            intent = intent
        )


        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount) {
        val badge = bottomNavigationView?.getOrCreateBadge(R.id.cart)
        if (badge != null) {
            badge.badgeGravity = BadgeDrawable.BOTTOM_START
            badge.backgroundColor =
                bottomNavigationView?.let { MaterialColors.getColor(it, R.attr.colorPrimary) }!!
            badge.number = cartItemCount.count
            badge.verticalOffset = convertDpToPixel(10f, this).toInt()
            badge.isVisible = cartItemCount.count > 0

        }

    }

}