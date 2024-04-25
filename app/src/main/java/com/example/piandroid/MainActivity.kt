package com.example.piandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.piandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniciarNavegacao()

    }

    private fun iniciarNavegacao(){
        val navHost = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerViewPrincipal) as NavHostFragment

        navController = navHost.navController
    }
}


