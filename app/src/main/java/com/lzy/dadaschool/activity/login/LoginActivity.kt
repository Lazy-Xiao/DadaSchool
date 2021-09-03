package com.lzy.dadaschool.activity.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.lzy.dadaschool.R
import com.lzy.dadaschool.activity.main.MainActivity
import com.lzy.dadaschool.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    val loginModel: LoginModel by viewModels()
    lateinit var binding:ActivityLoginBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding=setContentView(this, R.layout.activity_login)
        binding.logindata=loginModel
        val sharedPreference = getSharedPreferences("data", Context.MODE_PRIVATE)
        binding.xuehao.text= SpannableStringBuilder(sharedPreference.getString("username",""))
        binding.password.text= SpannableStringBuilder(sharedPreference.getString("password",""))

        binding.loginbt.setOnClickListener {
            binding.loading.show()
            binding.cardbt.visibility=View.GONE
            binding.loading.visibility=View.VISIBLE
            if (binding.xuehao.text!!.isEmpty()||binding.password.text!!.isEmpty()){
                val snackbar= Snackbar.make(
                    binding.cardbt,
                    "账号/密码未填写",
                    Snackbar.LENGTH_LONG
                )
                snackbar.view.setBackgroundColor(R.color.toast)
                snackbar.show()
                binding.cardbt.visibility=View.VISIBLE
                binding.loading.visibility=View.GONE
            }else{
                loginModel.logininit(binding.xuehao.text.toString(),
                    binding.password.text.toString()
                )
            }
            loginModel.data.observe(this, Observer {
                if (it.gan == -1){
                    binding.cardbt.visibility=View.VISIBLE
                    binding.loading.visibility=View.GONE
                   val snackbar= Snackbar.make(
                        binding.cardbt,
                        "密码错误/服务器连接失败",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.view.setBackgroundColor(R.color.toast)
                    snackbar.show()
//                    Toast.makeText(this,"密码错误/服务器连接失败",Toast.LENGTH_SHORT).show()
                }else if(it.gan==1){
                    var editor = sharedPreference.edit()
                    editor.putString("username", binding.xuehao.text.toString())
                    editor.putString("password", binding.password.text.toString())
                    editor.commit();
                    var intent = Intent()
                    intent.setClass(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }
    }
}