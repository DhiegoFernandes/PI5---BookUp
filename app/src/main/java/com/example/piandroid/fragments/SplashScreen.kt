package com.example.piandroid.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            navegaParaProximaTela()
        }, 2000) // tempo de espera em milissegundos
    }
    private fun navegaParaProximaTela() {
        findNavController().navigate(R.id.action_splashScreen_to_principal)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}