package com.example.piandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentDicasDeLeituraBinding
import com.example.piandroid.databinding.FragmentMatematicaBinding

class DicasDeLeitura : Fragment() {

    private var _binding: FragmentDicasDeLeituraBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDicasDeLeituraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaListeners()



        binding.btnLiteratura.setOnClickListener {
            removeCurrentFragment()

            val fragmentLiteratura = Literatura()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentCVDicas, fragmentLiteratura)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.btnMatematica.setOnClickListener {
            // Remove o fragmento atual, se existir
            removeCurrentFragment()

            val fragmentMatematica = matematica()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentCVDicas, fragmentMatematica)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.btnIdiomas.setOnClickListener {
            findNavController().navigate(R.id.action_dicasDeLeitura_to_youGlish)
        }
    }

    private fun removeCurrentFragment() {
        val currentFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentCVDicas)
        if (currentFragment != null) {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(currentFragment)
            transaction.commit()
        }
    }

    private fun iniciaListeners(){
        removeCurrentFragment()

        //Por padrão inicia o container na tela literatura
        val fragmentLiteratura = Literatura()
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentCVDicas, fragmentLiteratura)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}