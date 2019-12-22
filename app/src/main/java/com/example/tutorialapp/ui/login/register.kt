package com.example.tutorialapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tutorialapp.Main2Activity
import com.example.tutorialapp.R
import com.example.tutorialapp.data.User
import com.example.tutorialapp.viewmodel.UserView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.register
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    lateinit var userView: UserView
    lateinit var signup: Button
    lateinit var loading_: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.username_)
        val password = findViewById<EditText>(R.id.password_)
        val password2 = findViewById<EditText>(R.id.password2)
        val phone = findViewById<EditText>(R.id.Phone)
        loading_ = loadingg
        signup = register

        userView = ViewModelProviders.of(this).get(UserView::class.java)
        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        userView.loggedInUser.observe(this, Observer {
                user -> user?.let {
            if(!user.isEmpty()){
                val i = Intent(this, Main2Activity::class.java)
                this?.startActivity(i)
                finish()
            }
        }
        })

        signup.setOnClickListener {
            if(username.text.length == 0 || phone.length() == 0){
                hint.text = "Fields can not be empty"
            } else if(phone.length() < 5){
                hint.text = "Invalid phone number"
            } else if(password.text.toString() == password2.text.toString() && password.length() > 5) {
                loading_.visibility = View.VISIBLE
                var user = User(username.text.toString(), phone.text.toString(), password.text.toString(), "", "0","Salsa")
                userView.insertUser(user)
                userView.loginUser(user)
                loginViewModel.login(username.text.toString(), password.text.toString())
            } else if(password.length() < 5) {
                hint.text = "Password must have more than 5 characters"
            }else
            {
                hint.text = "Passwords do not match"
            }
        }
    }

    override fun onBackPressed() {
        var i = Intent(this,LoginActivity::class.java)
        this.startActivity(i)
        super.onBackPressed()
    }
}
