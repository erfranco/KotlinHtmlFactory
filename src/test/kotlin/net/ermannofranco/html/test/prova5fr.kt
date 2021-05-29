package net.ermannofranco.html.test

import net.ermanno.html.Fragment

class Prova5(val text: String) : Fragment() {

    init {
        span { text }
        addP()
        div("div1") { "Questo e' il testo" }
        div(className = "uno").addTextBlock("Testo di prova & di controllo", encode = true)
        addP()

    }
}