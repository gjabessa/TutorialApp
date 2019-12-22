package com.example.tutorialapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ShareActionProvider
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tutorialapp.data.User
import com.example.tutorialapp.ui.login.LoginActivity
import com.example.tutorialapp.viewmodel.CourseView
import com.example.tutorialapp.viewmodel.UserView
import kotlinx.android.synthetic.main.about_dialog.view.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.join_class.view.*
import kotlinx.android.synthetic.main.nav_header_main2.*

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var userView: UserView
    lateinit var u: TextView
    lateinit var phonen: TextView
    lateinit var loggedInUser: User
    var c: String? = null
    lateinit var courseView: CourseView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.round_menu)

        userView = ViewModelProviders.of(this).get(UserView::class.java)
        courseView = ViewModelProviders.of(this).get(CourseView::class.java)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
        if(findNavController(R.id.nav_host_fragment).currentDestination?.id.toString() == R.id.home2.toString()){
            finish()
        }
        if(findNavController(R.id.nav_host_fragment).currentDestination?.id.toString() == R.id.moreDetail.toString()){
            finish()

        } else if(findNavController(R.id.nav_host_fragment).currentDestination?.id.toString() == "") {

        }
//        else {
//            if(c.isNullOrEmpty()){
//                c = findNavController(R.id.nav_host_fragment).currentDestination?.id.toString()
//                Toast.makeText(this,"Press back again to quit", LENGTH_SHORT).show()
//            } else {
//
//            }
//
//        }
    }
    fun openBrowser(view: View) {

        //Get url from tag
        val url = view.getTag() as String

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        //pass the url to intent data
        intent.data = Uri.parse(url)

        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        u = username
        phonen = phonenumber
        userView.loggedInUser.observe(this, Observer {
                user -> user?.let {
            if(!user.isEmpty()){
                loggedInUser = user[0]
                u.text = user[0].username
                phonen.text = user[0].phone_number
            }
        }
        })

        return true
    }
    private fun setShareIntent() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.beginner->{
                var bundle = Bundle()
                bundle.putString("level","b")
                bundle.putString("category","Salsa")
                loggedInUser.category = "Salsa"
                loggedInUser.level = "b"
                userView.loginUser(loggedInUser)
                courseView.deleteCourses()
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                nav_host_fragment.findNavController().navigate(R.id.detail,bundle, navOptions)
            }
            R.id.intermediate->{
                var bundle = Bundle()
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                bundle.putString("level","b")
                bundle.putString("category","Bachata")
                loggedInUser.level = "b"
                loggedInUser.category = "Bachata"
                userView.loginUser(loggedInUser)
                courseView.deleteCourses()
                nav_host_fragment.findNavController().navigate(R.id.detail,bundle,navOptions)
            }
            R.id.expert->{
                var bundle = Bundle()
                bundle.putString("level","b")
                bundle.putString("category","Kizomba")
                loggedInUser.category = "Kizomba"
                loggedInUser.level = "b"
                userView.loginUser(loggedInUser)
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                courseView.deleteCourses()
                nav_host_fragment.findNavController().navigate(R.id.detail,bundle,navOptions)
            }
            R.id.nav_join ->{
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.join_class, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)

                //show dialog
                val  mAlertDialog = mBuilder.show()
                mDialogView.joinclass.setOnClickListener {
                    val url = it.getTag() as String

                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)

                    //pass the url to intent data
                    intent.data = Uri.parse(url)

                    startActivity(intent)
                }

            }
            R.id.nav_about -> {
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.about_dialog, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)

                //show dialog
                val  mAlertDialog = mBuilder.show()
                mDialogView.close.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
                Toast.makeText(this,"profile", Toast.LENGTH_LONG).show()
            }

            R.id.nav_share ->{
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus"
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }

            R.id.nav_logout->{
                val i = Intent(this,LoginActivity::class.java)
                this?.startActivity(i)
                finish()
                userView.logoutUser(loggedInUser)

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
