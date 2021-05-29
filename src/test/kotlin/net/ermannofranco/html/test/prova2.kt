package net.ermannofranco.html.test

import net.ermanno.html.DocType
import net.ermanno.html.Doc

fun main() {
    print(Prova2().docu)
}

/**
 *
 * @author  ermanno
 */
class Prova2 {

    val docu= Doc("Ciao da HtmlFactory", DocType.XHTML_STRICT)
    init {
        val frset = docu.setFrameset("20%,*", null)
        frset.addFrame("uno", "http://www.unicredit.it").setMarginHeight("20px")
        val frdue = frset.addFrameset(null, "390,*").setNOFrameBorder()
        frdue.addFrame("due", "http://www.subito.it").setMarginWidth("20px")
        frdue.addFrame("duebis", "http://www.francescoantonacci.it")
    }
}
