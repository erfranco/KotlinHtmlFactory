package net.ermannofranco.html.test

import net.ermanno.html.Doc
import net.ermanno.html.DocType
import net.ermanno.html.TextFragment

fun main() {
    ProvaFragment().doc.save("prove/ProvaFragment.html")
}

class ProvaFragment {
    internal val doc = Doc(doctype = DocType.XHTML_STRICT)
    val text = "jhgjygfwal\nkgrlufe\"<><\n><?><_)*(&^^%$!@#$%"
    val fr = TextFragment(doc, "aaa <<<< @#$%&^*'''\"\"\"\"\"\"")

    init {
        doc.div().addA(href = "http://www.unicredit.it") { "ipertesto" }
        doc.loadFragment(fr)
        doc.div { "nuovo Div" }
        doc.loadFragment(Prova5(doc, text))
        //println(doc)
    }

}
