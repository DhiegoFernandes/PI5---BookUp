package com.example.piandroid.Fragments

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentPrincipalBinding
import com.google.android.material.button.MaterialButton


class Principal : Fragment() {

    private var _binding: FragmentPrincipalBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPrincipalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFragLivros.setOnClickListener{
            findNavController().navigate(R.id.action_principal_to_livros)
        }

        //Bottom Navigation Principal
        binding.bottomNavigationViewPrincipal.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnVoltar ->{
                    findNavController().popBackStack()
                    true
                }
                R.id.btnHome -> {
                    //Não faz nada
                    true
                }
                R.id.btnLivros -> {
                    //Vai pro fragment Livros
                    findNavController().navigate(R.id.action_principal_to_livros)
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}