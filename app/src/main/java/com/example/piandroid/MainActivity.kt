package com.example.piandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.piandroid.Fragments.Livros
import com.example.piandroid.Fragments.Principal
import com.example.piandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instanciando os fragmentos
        val fragmentoPrincipal = Principal()
        val fragmentoLivros = Livros()

        //Switch do menu Inferior
        binding.bottomNavigationViewPrincipal.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) { //Pega o id do item do menu
                R.id.btnVoltar -> {
                    Toast.makeText(this, "btnVoltar", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                    true
                }

                R.id.btnLivros -> { //Troca pro fragmento Livros
                    Toast.makeText(this, "btnLivros", Toast.LENGTH_SHORT).show()
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragmentContainerViewPrincipal, fragmentoLivros) // Coloca o fragmento Livros no container
                            addToBackStack(null) // Faz com que o app não feche quando voltar
                            commit() // Aplica alterações
                        }
                    true
                }

                R.id.btnHome ->{  //Troca pro fragmento Home
                    Toast.makeText(this, "btnHome", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainerViewPrincipal, fragmentoPrincipal)
                        addToBackStack(null)
                        commit()
                    }
                    true
                }
                else -> false
            }



        }
    }
}


