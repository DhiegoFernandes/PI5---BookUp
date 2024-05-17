package com.example.piandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentYouGlishBinding


class YouGlish : Fragment() {

    private var _binding: FragmentYouGlishBinding? = null
    private val binding get() = _binding!!


    private lateinit var webView: WebView
    private lateinit var searchInput: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout deste fragmento
        _binding = FragmentYouGlishBinding.inflate(inflater, container, false)
        val view = binding.root

        // Encontrar a WebView, EditText e Button
        webView = binding.webViewYouglish

        searchInput = binding.editPesquisa
        searchButton = binding.btnPesquisar

        // Configurar a WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true  // Habilitar JavaScript
        webSettings.domStorageEnabled = true  // Habilitar armazenamento DOM

        webView.webViewClient = WebViewClient()

        // Configurar o botão de pesquisa
        searchButton.setOnClickListener {
            val query = searchInput.text.toString()

            val lingua = when (binding.radioGroupLingua.checkedRadioButtonId) {
                R.id.rdbPt -> "pt"
                R.id.rdbIngles -> "en"
                else -> "pt" // Se nenhum estiver selecionado, defina como Inglês ("en") por padrão
            }
            if (query.isNotEmpty()) {
                loadYouGlishWidget(query, lingua) //Abre widget com query e lingua selecionada
            }
        }

        return view
    }

    private fun loadYouGlishWidget(query: String, lingua: String) {
        // Conteúdo HTML com o widget YouGlish e o código JavaScript
        val htmlContent = """
            <!DOCTYPE html>
            <html lang="pt-Br">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>YouGlish Widget</title>
            </head>
            <body>
                <!-- O widget substituirá esta <div> tag -->
                <div id="widget-1"></div>

                <script>
                    // Carregar o widget API de forma assíncrona
                    var tag = document.createElement('script');
                    tag.src = "https://youglish.com/public/emb/widget.js";
                    var firstScriptTag = document.getElementsByTagName('script')[0];
                    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                    var widget;
                    function onYouglishAPIReady(){
                        widget = new YG.Widget("widget-1", {
                            width: 640,
                            components: 72, // 8 (legenda) + 64 (controle botoes) = 72
                            events: {
                                'onFetchDone': onFetchDone,
                                'onVideoChange': onVideoChange,
                                'onCaptionConsumed': onCaptionConsumed
                            }          
                        });
                        //Inglês ou português
                        if ('$lingua' == 'pt') {
                          widget.fetch("$query", "portuguese");
                        } else {
                         widget.fetch("$query", "english");
                        }
          
                    }

                    var views = 0, curTrack = 0, totalTracks = 0;

                    function onFetchDone(event){
                        if (event.totalResult === 0) alert("No result found");
                        else totalTracks = event.totalResult; 
                    }

                    function onVideoChange(event){
                        curTrack = event.trackNumber;
                        views = 0;
                    }

                    function onCaptionConsumed(event){
                        if (++views < 3)
                            widget.replay();
                        else if (curTrack < totalTracks)
                            widget.next();
                    }
                </script>
            </body>
            </html>
        """.trimIndent()

        // Carregar o conteúdo HTML na WebView
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}