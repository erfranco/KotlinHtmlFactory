package net.ermannofranco.html.test

import net.ermanno.html.*
import kotlin.math.roundToInt

fun main() {
    Prova()
}

/**
 *
 * @author  ermanno
 */
class Prova {

    /** Creates a new instance of Prova  */
    init {
        val docu = Doc("Ciao da HtmlFactory", doctype = DocType.CLASSIC)
        //docu.addScriptReference("../script/Pippo.js")
        docu.metaDescription("pagina personale")
            .metaKeywords("pippo,pluto")
            .addMetaLanguage("it,en")
        //.addMetaRefresh(10, "www.pippo.com")
        try {
            //docu.loadScriptFile("D:/ProgettiSoft/HtmlFactory/script.txt");
            docu.script(
                "function pippo(){" +
                        "\nif (form.check.checked) document.bgColor='green';" +
                        "\nelse document.bgColor='red';}\nfunction orazio() {" +
                        "\nif (due.innerText==\"Ciao a Tutti\") {" +
                        "\ndue.innerText=\"Forza Java!!\";" +
                        "\nform.check.checked=true" +
                        "\ndocument.bgColor='lime';" +
                        "\nuno.innerText=\"SEL\";" +
                        "\ndue.style.color='red';" +
                        "\n}     else {" +
                        "\ndocument.bgColor='aqua';" +
                        "\nform.check.checked=false" +
                        "\nuno.innerText=\"NON SEL\";" +
                        "\ndue.innerText=\"Ciao a Tutti\";" +
                        "\ndue.style.color='white';}" +
                        "\n}\nfunction minnie() {" +
                        "\nvar form = document.forms[0];" +
                        "\nvar uno = document.getElementbyId('uno');" +
                        "\nvar due = document.getElementbyId('due');" +
                        //"\nwindow.setTimeout('pippo()',4000)") +
                        "}"
            )
            /*.setEndingScript(
                ("sub pluto()" +
                        "\ndim a,b" +
                        "\na=navigator.platform" +
                        "\nb=navigator.userAgent" +
                        "\nuno.innerText=a&\": \"&b" +
                        "\nend sub"), Document.SCRIPT.VBSCRIPT_EXPLICIT
            )*/
        } catch (e: HtmlException) {

        }
        docu.addStyleTag("span", "color:blue;font-size:24px")
        docu.addStyleTag("input.pippo", "color:aqua;font-size:24px")
        //docu.addCSSLink("Pippo.css").setOnLoadEvent("minnie()")
        val form = docu.addForm(Method.POST, id = "form").setAction("http://www.jolie.it/csea")
        form.addButton("CLICCA!")//.addClass("pippo").addEvent("onClick", "pluto()")
        form.addCheckboxInput("check", checked = true).event(MouseEvent.CLICK, "orazio()")
        val spanUno = form.span("uno") { "selezionato" }
        spanUno.addBR()
        val div = form.div("due")
        div.style("color:red;background-color:green;text-align:center;font:italic bold 32 'Times New Roman'")
        div.addTextBlock("Ciao a Tutti", encode = true)
        val details =
            div.addContainerTag("details").addTextBlock("Contenuto: questo  <> e' il contteennuuttooooo", true)
        details.addContainerTag("summary").addTextBlock("Titolo", true)

        div.addA(href = "www.pluto.it").addImage("www.kkkkk.it", "immagine caricata", height = "23", width = "33")
        docu.addHR()
        val tbl = docu.table("tabella")
        tbl.style("font-family:Arial")
            .addAttribute("border", "1")
            .addAttribute("align", "center")
        var row: Row
        //HtmlCell col;
        val numRighe = (Math.random() * 25).roundToInt()
        val numCols = (Math.random() * 20).roundToInt()
        for (i in 0 until numRighe) {
            row = tbl.addRow()
            row.style("font-size:${i * 7 / 5 + 8}px")
            for (j in 0 until numCols) {
                val rNum = (Math.random() * 255).roundToInt()
                val gNum = (Math.random() * 255).roundToInt()
                val bNum = (Math.random() * 255).roundToInt()
                val rRec = 255 - rNum
                val gRec = 255 - gNum
                val bRec = 255 - bNum
                var r = Integer.toHexString(rNum)
                if (r.length == 1) r = "0$r"
                var g = Integer.toHexString(gNum)
                if (g.length == 1) g = "0$g"
                var b = Integer.toHexString(bNum)
                if (b.length == 1) b = "0$b"
                var rRecStr = Integer.toHexString(rRec)
                if (rRecStr.length == 1) rRecStr = "0$rRecStr"
                var gRecStr = Integer.toHexString(gRec)
                if (gRecStr.length == 1) gRecStr = "0$gRecStr"
                var bRecStr = Integer.toHexString(bRec)
                if (bRecStr.length == 1) bRecStr = "0$bRecStr"
                row.addCell("${i + 1}-${j + 1}")
                    .style("color:#$r$g$b;Background-color:#$rRecStr$gRecStr$bRecStr")
            }
        }
        docu.save("prove/prova1.htm")
    }
}
