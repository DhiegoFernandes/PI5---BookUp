package com.example.piandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.piandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    //Objeto singleton estático, variaveis/funções dentro do companion object podem ser utilizada por toda a classe
    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
        private const val REQUEST_CODE_PERMISSIONS = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Deixa barra de status e menu inferior azuis
        window.statusBarColor = ContextCompat.getColor(this, R.color.azul)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.azul)
        //Se maior que Tiramisu (Android 13), pede permissão da forma especifica
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!allPermissionsGranted()) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_PERMISSIONS)
            } else {
                iniciarNavegacao()
            }
        } else {
            iniciarNavegacao()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    // Verificando permissão
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarNavegacao()
                Toast.makeText(this, "Permissão de notificações concedida.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permissão de notificações não foi concedida.", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun iniciarNavegacao(){
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewPrincipal) as? NavHostFragment
        navHost?.let {
            navController = navHost.navController
        } ?: run {
            Toast.makeText(this, "Erro ao iniciar navegação.", Toast.LENGTH_LONG).show()
        }
    }


}


