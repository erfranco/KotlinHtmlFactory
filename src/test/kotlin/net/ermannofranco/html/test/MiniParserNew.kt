package net.ermannofranco.html.test

import net.ermanno.html.*
import java.util.*

fun main() {
    MiniParserNew()
}
/**
 *
 * @author  ermanno
 */
class MiniParserNew: Doc("Mini HTML Parser 2021 - by ErmannoFranco", DocType.XHTML_STRICT) {

    /** Creates a new instance of MiniParser  */
    init {

        //foglio di stile incorporato; incoraggiata la separazione contenuto-presentazione
        //qui lo carico nella pagina per farlo vedere.
        //Se invece voglio creare un link css, uso addCSSLink(URI);
        loadCSSFile("sito/css/MiniParser2021.css")
        // se ne possono caricare diversi

        addScriptReference("prototype/1.7.3.0/prototype.js", AJAX_GOOGLE_LIBS)
        // script incorporato
        // incoraggiata la separazione contenuto-scripting
        //qui lo carico nella pagina per farlo vedere.
        //Se invece voglio creare un link script, uso addScriptReference(URI);
        try {
            loadScriptFile("sito/script/ScriptMiniParser2021.txt")
            // se ne possono caricare diversi
        } catch (ignored: HtmlException) {
        }
        // uno script solo, non puo' avvenire eccezione, a meno che non scriviate scemenze nel file di script...
        // l'eccezione avviene se i linguaggi dei vari script sono fra loro incompatibili

        //disegno la pagina
        //riferimenti indispensabili
        val form: Form
        val tabellaEsterna: Table
        var riga: Row
        val tabellaInterna: Table
        var tabellaInternariga: Row
        //fine riferimenti indispensabili

        // disegno la struttura della pagina
        //la libreria � costruita in modo da restituire un riferimento all'ultimo oggetto creato
        //in questo modo le istruzioni si possono concatenare.
        //es. un oggetto Select possiede il metodo addOption(valore, testo)
        //addOption restituisce un riferimento all'oggetto Select stesso, per cui
        // diventa facile concatenare tutte le option.
        ///(...).addSelect("spacetab").addOption("2","2").addOption("4","4").addSelectedOption("6","6").addOption("8","8"); e cos� via

        //tabella esterna
        addForm().also { form = it }.table().also { tabellaEsterna = it }.setAlign(Align.CENTER)
        //riga 1, cella 1
        tabellaEsterna.addRow().also { riga = it }.addCell("Scrivere il testo da codificare")
        //cella 2 con tabella nidificata
        riga.addCell().table().also { tabellaInterna = it }.setAlign(Align.CENTER)
        //riga 1 di tabella interna con cella unica
        tabellaInterna.addRow().also { tabellaInternariga = it }.addCell("Spazi per tab:")
        //riga 2 di tabella interna con unica cella con campo SELECT
        tabellaInterna.addRow().also { tabellaInternariga = it }
            .addCell().addSelect("spacetab").addOption("2", "2").addSelectedOption("4", "4").addOption("6", "6")
            .addOption("8", "8")
        //torniamo alla tabella esterna: riga 1, cella 3
        riga.addCell().addButton("PULISCI").event("onclick", "pulisci(new Array(this.form.input,this.form.output))")
        //riga 2 di tabella esterna con unica cella che si estende per 3 colonne e textarea di ingresso
        tabellaEsterna.addRow().addCell(3).addTextarea("input", "100%", "18").addClass("red")
        //riga 3 cella 1 che si estende per 2 colonne con pulsante 1(codifica)
        tabellaEsterna.addRow("middle").also { riga = it }
            .addCell(2).addButton("Codifica HTML:").addClass("buttonred").event(
                "onclick",
                "assegnaValore(codifica(this.form.input.value,this.form.spacetab.value), this.form.output)"
            )
        //riga 3 cella 2 con pulsante 2(escape)
        riga.addCell().addButton("Escape:").addClass("buttonred")
            .event("onclick", "assegnaEscape(this.form.input.value, this.form.output)")
        //riga 4 di tabella esterna con unica cella che si estende per 3 colonne e testo
        tabellaEsterna.addRow().addCell(3).addTextBlock("Risultato:", false)
        //riga 5 di tabella esterna con unica cella che si estende per 3 colonne e textarea di uscita
        tabellaEsterna.addRow().addCell(3).addTextarea("output", "100%", "18", readOnly = true).addClass("green")

        //fine disegno della struttura della pagina

        /*totale: 
     1 istanziazione diretta;
     5 dichiarazioni
     1 riga per lo stile
     3 righe per gli script, compresa gestione eccezioni
    11 righe di codice per la struttura
    
     */

        // salva l'output in un file permanente e stampa a console un messaggio di conferma    
        save("sito/MiniHtmlParserAutoGenNew" + Date().time + ".htm")

    }
}