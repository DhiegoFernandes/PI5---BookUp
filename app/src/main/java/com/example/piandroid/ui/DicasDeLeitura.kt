package com.example.piandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentDicasDeLeituraBinding

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
    }


    private fun iniciaListeners(){
        binding.btnYouGlish.setOnClickListener {
            findNavController().navigate(R.id.action_dicasDeLeitura_to_youGlish)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}