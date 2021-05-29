package net.ermannofranco.html.test

import net.ermanno.html.Doc
import net.ermanno.html.TextFragment

fun main() {
    ProvaFragment()
}

class ProvaFragment {
    val text = "jhgjygfwal\nkgrlufe\"<><\n><?><_)*(&^^%$!@#$%"
    val fr = TextFragment("aaa <<<< @#$%&^*'''\"\"\"\"\"\"")

    val doc = Doc()

    init {
        doc.div().addA(href = "http://www.unicredit.it") { "ipertesto" }
        doc.loadFragment(fr)
        doc.div { "nuovo Div" }
        doc.loadFragment(Prova5(text))
        println(doc)
    }

}
