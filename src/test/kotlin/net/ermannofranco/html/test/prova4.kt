package net.ermannofranco.html.test

import net.ermanno.html.Charset
import net.ermanno.html.Doc
import net.ermanno.html.DocType
import net.ermanno.html.ROBOTS

fun main() {
    val txt =
        "L'Operazione Compass (in inglese Operation Compass) è stata un'offensiva sferrata l'8 dicembre 1940 dalle forze armate britanniche della Western Desert Force in Africa Settentrionale durante la seconda guerra mondiale, per ricacciare oltre il confine con la Libia le forze italiane che nel settembre 1940 erano lentamente penetrate in Egitto senza incontrare resistenza. La controffensiva vide contrapposti circa 31 000 soldati britannici, quasi completamente motorizzati e addestrati alla guerra di movimento, all'intera 10ª Armata del maresciallo Rodolfo Graziani forte di oltre 150 000 uomini, disposta tra Sidi Barrani, Bir Sofafi e Bardia.\n" +
                "La campagna, iniziata come un attacco locale della durata prevista di circa cinque giorni, a causa dell'abilità di manovra delle forze britanniche e della inefficace e disordinata difesa italiana si trasformò in un'offensiva generale che, dopo due mesi e quattro battaglie campali (Sidi Barrani, Bardia, Tobruch e Beda Fomm), si concluse con la totale disfatta delle forze del maresciallo Graziani e la vittoria delle moderne unità motocorazzate britanniche, che conquistarono interamente la Cirenaica, annientarono la 10ª Armata e catturarono circa 115 000 soldati italiani.\n" +
                "\t\tLa pesante sconfitta ebbe forti contraccolpi in Italia; Benito Mussolini, già in difficoltà dopo i duri rovesci in Grecia e le gravi perdite navali subite in seguito alla cosiddetta notte di Taranto, fu costretto, per il rischio concreto di perdere anche la Tripolitania, a chiedere aiuto dell'alleato tedesco, decretando così la fine della «guerra parallela» fascista. Per non rischiare di vedere l'Italia prematuramente sconfitta nel teatro del Mediterraneo, Adolf Hitler decise per un rapido invio di reparti corazzati tedeschi in Nordafrica, che consentirono all'Asse di contrattaccare e mantenere aperto il fronte nordafricano fino alla primavera del 1943. "
    val prova4 = Prova4(txt)

}

class Prova4(val text: String) {
    val doc = Doc("Prova4 - vediamo", DocType.XHTML_STRICT, charset = Charset.ISO8859_1)

    init {
        with(doc) {
            setMetaRobots(ROBOTS.FOLLOW)
            addStyleTags(
                "body" to "background-color:lightblue;text-align:justify;",
                "span" to "font-size:12px;color:red;background-color:#ffff00;",
                "div" to "font-family:\"Lucida Console\",\"Courier New\",monospace;font-size:24px;color:red;text-align:center",
                ".uno" to "font-style: oblique"
            )
            span { text }
            addP("unoId", "uno") { "Testo separato" } style "font-size:24px;background-color:#00ff00"
            div("div1") { "Questo e' il testo" }
            div(className = "uno").addTextBlock("Testo di prova & di controllo", encode = true)
            addP()
            save("file4.html")
        }
    }

    override fun toString(): String {
        return doc.toString()
    }
}