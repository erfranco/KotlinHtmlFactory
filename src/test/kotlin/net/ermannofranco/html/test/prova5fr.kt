package net.ermannofranco.html.test

import net.ermanno.html.Doc
import net.ermanno.html.Fragment
import net.ermanno.html.MouseEvent

class Prova5(doc: Doc, val text: String) : Fragment(doc) {

    init {
        div().span { text }
        addP()
        div("div1") { "Questo e' il testo" }
        val div = div(className = "uno")
        div.event(MouseEvent.MOUSEOVER, "this.style.backgroundColor='YELLOW'")
        div.addTextBlock("Testo di prova & di controllo", encode = true)

    }
}