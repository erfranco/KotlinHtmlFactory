package net.ermanno.html

import java.io.File
import java.util.*

const val W3CSS = "https://www.w3schools.com/w3css/4/w3.css"

enum class BodyEvent(private val event: String): HtmlEvent {
    ONLOAD("onload"), ONUNLOAD("onunload");
    override fun smallName(): String {
        return event
    }
}

enum class DocType { CLASSIC, CLASSIC_FRAMESET, HTML5, XHTML1_1, XHTML_STRICT, XHTML_TRANSITIONAL, XHTML_FRAMESET }

enum class Charset(val value: String) { UTF8("utf-8"), ISO8859_1("iso-8859-1") }

enum class ROBOTS(val value: String) { INDEX("index"), NOINDEX("noindex"), FOLLOW("follow"), NOFOLLOW("nofollow") }

/** La classe root dell'albero dei tag.
 * Contiene l'istanza del documento, ed i metodi factory pe la generazione dei tag
 * Fornisce in automatico i tag HEAD e BODY.
 * Fornisce i metodi per impostare il DOCTYPE, lo stile, gli script (all'inizio o
 * alla fine),i riferimenti CSS, il tipo di HTML, se gli script sono in JAVASCRIPT
 * o in VBSCRIPT, gli
 * eventi del BODY e altre cose...
 */
open class Doc(
    private val title: String = "null",
    private val doctype: DocType = DocType.CLASSIC,
    private val charset: Charset = Charset.UTF8
) : HtmlContainer {

    final override val elements: MutableList<in HtmlElem> = mutableListOf()
    private var flagFrameset = false
    private val links = mutableListOf<Tag>()
    private val metas = mutableListOf<Tag>()
    private val events = mutableMapOf<BodyEvent, String>()
    private val scriptReferences = mutableListOf<Pair<String, String>>()
    private val stiliFile = mutableListOf<String>()
    private val stili = mutableListOf<Pair<String, String>>()
    private val scripts = mutableListOf<Pair<String, String>>()

    /** restituisce la stringa che indica il tipo di documento
     * @return la stringa che indica il tipo di documento
     */
    private val stringDoctype: String
        get() {
            return when (doctype) {
                DocType.CLASSIC -> "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" " +
                        "\"http://www.w3.org/TR/html4/loose.dtd\">\n<html> "
                DocType.CLASSIC_FRAMESET -> "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" " +
                        "\"http://www.w3.org/TR/html4/frameset.dtd\">\n<html>"
                DocType.HTML5 -> "<!DOCTYPE html>\n<html>"
                DocType.XHTML1_1 -> "<?xml version=\"1.0\"?>\n" +
                        " <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" " +
                        "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                DocType.XHTML_STRICT -> "<?xml version=\"1.0\"?>\n" +
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
                        "\"DTD/xhtml1-strict.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                DocType.XHTML_TRANSITIONAL -> "<?xml version=\"1.0\"?>\n" +
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
                        "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                DocType.XHTML_FRAMESET -> "<?xml version=\"1.0\"?>\n" +
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" " +
                        "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
            }
        }

    /** aggiunge un riferimento ad un file di script esterno;
     * il riferimento viene posizionato nell'head, PRIMA di qualsiasi script caricato;
     * @return il documento
     * @param URI
     */
    fun addScriptReference(URI: String, prefix: String? = null, type: String = "text/javascript"): Doc {
        prefix?.let { scriptReferences.add("$prefix$URI" to type) } ?: scriptReferences.add(URI to type)
        return this
    }

    fun scriptLinks(vararg refs: String): Doc {
        refs.forEach { addScriptReference(it) }
        return this
    }

    /** aggiunge uno script javascript in testa al documento.
     * @param script stringa;attenzione ai ritorni a capo che non vengono forniti
     * @return un riferimento al documento
     */
    fun script(script: String, type: String = "text/javascript"): Doc {
        scripts.add(script to type)
        return this
    }


    /** imposta un tag link ad un foglio di stile esterno
     * @param UrlCSS l'indirizzo url del file di stile
     * @return un riferimento al documento
     */
    fun cssLink(cssUrl: String): Doc {
        links.add(
            StandardTag("link", closingTag = false)
                .addAttributes("rel" to "stylesheet", "type" to "text/css", "href" to cssUrl)
        )
        return this
    }

    fun cssLinks(vararg cssUrls: String): Doc {
        cssUrls.forEach { cssLink(it) }
        return this
    }

    /** imposta l'evento innescato a fine caricamento pagina
     * @param funzione la stringa che richiama il codice evento (es. "faiAzione()")
     * @return un riferimento al documento
     */
    fun event(type: BodyEvent, func: String): Doc {
        events.put(type, func)
        return this
    }

    /** imposta un tag STYLE, con due parametri
     * @param tagClass il tag bersaglio dello stile: può essere un nome comune (es. body),oppure un
     * nome di classe qualificato (input.pippo) o solo di classe (.pippo); segue la
     * sintassi del tag STYLE
     * @param style la stringa di stile, senza parentesi graffe
     * @return un riferimento al documento
     */
    fun addStyleTag(tagClass: String, style: String): Doc {
        stili.add(tagClass to style)
        return this
    }

    fun addStyleTags(vararg styles: Pair<String, String>): Doc {
        styles.forEach {
            stili.add(it)
        }
        return this
    }

    /** metodo factory generico che crea un ContainerTag e lo aggiunge all'oggetto
     * documento
     * @param htmlName
     * @param id
     * @param className
     * @return un ContainerTag
     */
/*    override fun addContainerTag(htmlName: String, id: String?, className: String?): ContainerTag {
        val ct = ContainerTag(htmlName, id, className)
        elements.add(ct)
        return ct
    }*/


    /**
     * @param nomeCompletoFileCSS
     * @return
     */
    fun loadCSSFile(nomeCompletoFileCSS: String): Doc {
        val sb = StringBuilder("\n")
        try {
            val fr = File(nomeCompletoFileCSS)
            sb.append(fr.readText())
        } catch (e: Exception) {
            sb.append("<!-- impossibile leggere file CSS \"$nomeCompletoFileCSS\": $e -->")
        }
        stiliFile.add(sb.toString())
        return this
    }

    /** Carica uno script in testa al documento.
     * Metodo fortemente incoraggiato, fornisce in automatico ritorni a capo e
     * indentazione come nel file di testo passato come parametro
     * @param nomeFileCompleto nome completo del file di script
     * @throws HtmlException di coerenza del linguaggio
     */
    //@Throws(HtmlException::class)
    fun loadScriptFile(nomeFileCompleto: String, type: String = "text/javascript"): Doc {
        val sb = StringBuilder("\n")
        try {
            val file = File(nomeFileCompleto)
            file.bufferedReader().use {
                sb.append(it.readText())
            }
        } catch (e: Exception) {
            sb.append("// WARNING: failed to load \"$nomeFileCompleto\": $e")
        }
        return script(sb.toString(), type)
    }

    /**
     * @param fileName
     * @return
     */
    fun save(fileName: String): Boolean {
        try {
            val f = File(fileName)
            f.writeText(this.toString())
            println("File $fileName salvato.")
            return true
        } catch (e: Exception) {
            println("Warning: $e.")
            return false
        }
    }

    /** stampa tutto
     * @return stampa tutto
     */
    override fun toString(): String {
        //var languageScript: String? = null
        val sb = StringBuilder().append(stringDoctype)
            .append("\n<head>")
            .append("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=${charset.value}\" />")
        metas.forEach {
            sb.append(it)
        }
        sb.append("\n<title>${title}</title>")
        links.forEach {
            sb.append(it)
        }
        stiliFile.forEach {
            val stile = ContainerTag("style")
            stile.addAttribute("type", "text/css")
            stile.addTextBlock(it, false)
            sb.append(stile)
        }
        if (stili.isNotEmpty()) {
            val stile = ContainerTag("style")
            stile.addAttribute("type", "text/css")
            stili.forEach {
                stile.addTextBlock("\n${it.first} {${it.second}}", false)
            }
            sb.append(stile)
        }
        scriptReferences.forEach {
            val ref = StandardTag("script", closingTag = true)
            ref.addAttributes("src" to it.first, "type" to it.second)
            sb.append(ref)
        }

        if (scripts.size > 0) {
            scripts.forEach {
                sb.append("\n<script type=\"${it.second}\">")
                sb.append("\n<!--\n")
                sb.append(it.first)
                sb.append("\n// -->\n</script>")
            }

        }
        sb.append("\n</head>")
        if (!flagFrameset) {
            sb.append("\n<body")
            events.forEach {
                sb.append(" ${it.key.smallName()}=\"${it.value}\"")
            }
            sb.append(">")
        }
        elements.forEach {
            sb.append(it)
        }
        if (!flagFrameset) {
            sb.append("\n</body>")
        }

        sb.append("\n<!-- PAGINA GENERATA AUTOMATICAMENTE DA CODICE KOTLIN che usa la libreria HtmlFactory -->")
        sb.append(
            """
<!-- AUTORE DELLA LIBRERIA HtmlFactory:$AUTHOR ($EMAIL) -->"""
        )
        sb.append(
            """
<!-- Data generazione: ${Date()} -->"""
        )
        sb.append("\n</html>")
        return sb.toString()
    }

    /**
     * @param cols
     * @param rows
     * @return
     */
    fun setFrameset(cols: String, rows: String?): FrameSet {
        flagFrameset = true
        val frset = FrameSet(cols, rows)
        //elimina tutto;
        elements.clear()
        elements.add(frset)
        return frset
    }

    /**
     * @param description
     * @return
     */
    fun addMetaDescription(description: String): Doc {
        metas.add(
            StandardTag("meta", "description", closingTag = false).addAttribute(
                "content",
                description
            )
        )
        return this
    }

    /**
     * @param description
     * @return
     */
    fun addMetaKeywords(description: String): Doc {
        metas.add(
            StandardTag("meta", "keywords", closingTag = false).addAttribute(
                "content",
                description
            )
        )
        return this
    }

    /**
     * @param robot
     * @return
     */
    fun setMetaRobots(robot: ROBOTS): Doc {
        metas.add(StandardTag("meta", "robots", closingTag = false).addAttribute("content", robot.value))
        return this
    }

    /** la data in formato stringa
     * @param expires
     * @return
     */
    fun addMetaExpires(expires: String): Doc {
        metas.add(
            StandardTag("meta", closingTag = false)
                .addAttributes("http-equiv" to "expires", "content" to expires)
        )
        return this
    }

    /**
     * @param seconds
     * @param URLRedirect
     * @return
     */
    fun addMetaRefresh(seconds: Int, URLRedirect: String): Doc {
        metas.add(
            StandardTag("meta", closingTag = false)
                .addAttributes(
                    "http-equiv" to "refresh",
                    "content" to if (URLRedirect.isEmpty()) "$seconds" else "$seconds; url=$URLRedirect"
                )
        )
        return this
    }

    /** il linguaggio della pagina
     * @param language
     * @return
     */
    fun addMetaLanguage(language: String): Doc {
        metas.add(
            StandardTag("meta", "language", closingTag = false)
                .addAttribute("content", language)
        )
        return this
    }

}

/**
 * Questa eccezione formalizza le eccezioni del package.
 * Per ora viene usata solo se si inseriscono nello stesso blocco di script
 * funzioni scritte in linguaggi diversi
 *
 * @author  ermanno
 */
class HtmlException
/** Creates a new instance of HtmlException
 * @param msg il messaggio passato
 */
    (val msg: String?) : Exception(msg)