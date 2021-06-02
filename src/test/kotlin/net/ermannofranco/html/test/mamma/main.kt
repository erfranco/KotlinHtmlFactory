package net.ermannofranco.html.test.mamma

import net.ermanno.html.*
import net.ermanno.html.DocType.XHTML_STRICT

fun main() {
    val doc = Doc("Prova per Nonna", doctype = XHTML_STRICT, charset = Charset.ISO8859_1)
    with(doc) {
        event(BodyEvent.ONLOAD, "alert('Pagina caricata!')")
        cssLinks("css/pro_drop_1.css", "css/style.css", W3CSS)
        scriptLinks("script/stuHover.js", "script/ajax_base.js")
        div().addA("top")
        val cells = table(width = "95%", border = 3).addRow(2)
        cells[0].loadFragment(Menu(doc))


        val list = cells[1].addList(id = "list1")
        list.addItems("ciao", "ottjkgo&&&", "....>>>novvvve   <")
        loadFragment(Footer(doc))
    }


    doc.save("sito/file1.htm")
}

class Menu(doc: Doc) : Fragment(doc) {
    init {
        span(className = "preload1")
        span(className = "preload2")
        val ul = addList("nav")
        ul.addItem(className = "top").addA(href = "main.php", className = "top_link")
            .span { "Home" }.event(MouseEvent.MOUSEOVER, "this.style.backgroundColor='red'")
        ul.addItem(className = "top").addA("products", "top_link", "#nogo2").span(className = "down") { "Montagna" }
    }
}

class Footer(doc: Doc) : Fragment(doc) {
    init {
        val list = table(width = "100%").addRow(3)
        list.forEachIndexed { index, cell -> cell.addTextBlock(index.toString(), true) }

    }
}