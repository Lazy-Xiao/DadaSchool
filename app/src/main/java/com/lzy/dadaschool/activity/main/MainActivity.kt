package com.lzy.dadaschool.activity.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.lzy.dadaschool.R
import com.lzy.dadaschool.databinding.ActivityMainBinding
import com.lzy.dadaschool.fragment.home.HomeFragment
import android.view.animation.AnimationSet

import android.view.animation.Animation

import android.view.animation.ScaleAnimation

import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.kaopiz.kprogresshud.KProgressHUD
import com.lzy.dadaschool.activity.login.LoginActivity
import com.wang.avi.AVLoadingIndicatorView
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val mainModel: MainModel by viewModels()
    lateinit var hud: KProgressHUD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val controller = ViewCompat.getWindowInsetsController(binding.root)
        controller?.isAppearanceLightStatusBars = true
        binding.homedata = mainModel

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.noticeFragment, R.id.meFragment
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomnavigation.setupWithNavController(navController)
        hud = KProgressHUD.create(this)
        try {
            initdata()

        } catch (e: Exception) {
            intent.setClass(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initdata() {
        val sharedPreference = getSharedPreferences("data", MODE_PRIVATE)
        mainModel.logininit(
            sharedPreference.getString("username", "").toString(),
            sharedPreference.getString("password", "").toString()
        )
        mainModel.data.observe(this, Observer {
            Toast.makeText(this, it.gan.toString(), Toast.LENGTH_SHORT).show()
            when (it.gan) {
                -1 -> {
                    val intent = Intent()
                    intent.setClass(this, LoginActivity::class.java)
                    startActivity(intent)
                    hud.dismiss()
//                    finish()
                }
                0 -> {
                    val avLoadingIndicatorView = AVLoadingIndicatorView(this)
                    hud.setDetailsLabel("加载数据中")
                        .setCustomView(avLoadingIndicatorView)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show()
                }
                1 -> {
                    hud.dismiss()
                    binding!!.nametitle.text = "你好，" + it.name
                    binding.subtitle.text = "————" + it.banji
                }
            }
        })

    }


}