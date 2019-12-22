package com.example.tutorialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tutorialapp.ui.login.LoginActivity
import com.example.tutorialapp.ui.login.LoginViewModel
import com.example.tutorialapp.ui.login.LoginViewModelFactory
import com.example.tutorialapp.viewmodel.UserView

class splashscreen : AppCompatActivity() {
    lateinit var userView: UserView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        userView = ViewModelProviders.of(this).get(UserView::class.java)
        userView.loggedInUser.observe(this, Observer {
                user -> user?.let {
            if(!user.isEmpty()){
                val i = Intent(this,Main2Activity::class.java)
                this?.startActivity(i)
                finish()
            } else {
                val i = Intent(this,LoginActivity::class.java)
                this?.startActivity(i)
                finish()
            }
        }
        })
    }
}
