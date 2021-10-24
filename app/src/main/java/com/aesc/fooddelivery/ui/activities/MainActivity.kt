package com.aesc.fooddelivery.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.databinding.ActivityMainBinding
import com.aesc.fooddelivery.providers.database.viewmodel.ViewModelOrders
import com.aesc.fooddelivery.ui.interfaces.IOrderNotification
import com.aesc.fooddelivery.utils.Utils
import com.google.android.material.navigation.NavigationView
import com.nex3z.notificationbadge.NotificationBadge
import kotlinx.android.synthetic.main.action_bar_notification_icon.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener, IOrderNotification {
    private var counterOrders = 0
    lateinit var viewModalOrder: ViewModelOrders
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var badge: NotificationBadge? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        /*   binding.appBarMain.fab.setOnClickListener { view ->
               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                   .setAction("Action", null).show()
           }*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_categories, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getAllOrders()
    }

    private fun getAllOrders() {
        viewModalOrder = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get()

        viewModalOrder.allorders.observe(this, { orders ->
            orders.let {
                counterOrders = it.size
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        val view = menu.findItem(R.id.cart_menu).actionView
        badge = view.findViewById(R.id.badge)
        view.cart_icon.setOnClickListener {
            val intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }
        updateCartCount()
        return true
    }

    private fun updateCartCount() {
        if (badge == null) return
        runOnUiThread {
            if (counterOrders == 0) badge!!.visibility =
                View.INVISIBLE else {
                badge!!.visibility = View.VISIBLE
                badge!!.setText(counterOrders.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateCartCount()
    }

    override fun onStart() {
        super.onStart()
        updateCartCount()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onClick(p0: View?) {
        Utils.logsUtils("Test llego")
    }

    override fun addOtherOrder() {
        counterOrders += 1
        updateCartCount()
    }
}