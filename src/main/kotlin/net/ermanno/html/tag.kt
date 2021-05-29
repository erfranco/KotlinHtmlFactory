package net.ermanno.html

import java.io.Serializable
import java.util.*

interface HtmlEvent {
    fun smallName(): String
}

enum class KeyEvent(private val event: String) : HtmlEvent {
    KEYDOWN("onkeydown"), KEYUP("onkeyup"), KEYPRESS("onkeypress");

    override fun smallName(): String {
        return event
    }
}

enum class MouseEvent(private val event: String) : HtmlEvent {
    CLICK("onclick"), DBLCLICK("ondblclick"),
    MOUSEOVER("onmouseover"), MOUSEOUT("onmouseout"),
    MOUSEMOVE("onmousemove"), MOUSEDOWN("onmousedown"),
    MOUSEUP("onmouseup");

    override fun smallName(): String {
        return event
    }
}

enum class Ents(val entity: String) {
    BLANK("&nbsp;"), APOS("&apos;")
}

private data class HtmlClass(val className: String) : Serializable {
    override fun toString(): String {
        return className
    }
}

interface HtmlElem : Serializable

interface Tag : HtmlElem {

    val htmlName: String
    val attributes: MutableMap<String, String>
    val events: MutableMap<String, String>

    /**
     * imposta lo stile
     */
    fun style(stylename: String): Tag


    fun addClass(className: String): Tag {
        return addAttribute("class", className)
    }

    /** aggiunge un attributo
     * @param name
     * @param value
     * @return un riferimento a se stesso per il concatenamento dei metodi
     */
    fun addAttribute(name: String, value: String): Tag {
        val n = name.lowercase(Locale.getDefault()).trim()
        if (n.isEmpty()) return this
        //if (n == "value") attributes["value"] = valore.lowercase(Locale.getDefault())
        attributes[n] = value.trim()
        return this
    }

    fun addAttributes(vararg attrs: Pair<String, String>): Tag {
        attrs.forEach {
            addAttribute(it.first, it.second)
        }
        return this
    }

    /** aggiunge un evento
     * @param nome
     * @param gestore
     * @return un riferimento a se stesso per il concatenamento dei metodi
     */
    fun event(nome: String, gestore: String): Tag {
        val n = nome.lowercase(Locale.getDefault()).trim()
        if (n.isEmpty()) return this
        events[n] = gestore
        return this
    }

    fun event(nome: HtmlEvent, gestore: String): Tag {
        val n = nome.smallName()
        events[n] = gestore
        return this
    }

    /** rimuove un Attributo
     * @param nome nome dellattributo
     */
    fun removeAttribute(nome: String) {
        synchronized(attributes) {
            attributes.remove(nome)
        }
    }

    /** rimuove un evento
     * @param nome il nome dell'evento es. "onclick"
     */
    fun removeEvent(nome: String) {
        synchronized(events) {
            events.remove(nome)
        }
    }

}

/** La classe che implementa un tag singolo, senza tag di chiusura.
 * sono presenti i costruttori per impostare subito il nome html, l'id, il nome e
 * la classe, più tutti i metodi di abstract Tag.
 * La classe non è istanziabile direttamente, ma solo tramite i metodi factory
 * definiti in HtmlContainer
 * @author ermanno
 */
open class SingleTag protected constructor(
    htmlName: String,
    val id: String? = null,
    htmlClass: String? = null,
) :
    Tag {

    final override val htmlName: String
    final override val attributes: MutableMap<String, String> = mutableMapOf()
    final override val events: MutableMap<String, String> = mutableMapOf()
    private val htmlClass: HtmlClass?

    init {
        this.htmlName = htmlName.lowercase(Locale.getDefault())
        this.htmlClass = htmlClass?.let { HtmlClass(htmlClass) }
    }

    /** imposta l'attributo style
     * @param stylename la stringa di stile es. "color:#12345;font: bold 16 Antiqua"
     * @return un riferimento a se stesso per il concatenamento dei metodi
     */
    final override infix fun style(stylename: String): Tag {
        val value = stylename.trim()
        if (value.isEmpty()) return this
        addAttribute("style", value)
        return this
    }

    /** stampa tutto
     * @return stampa tutto
     */
    override fun toString(): String {
        val sb = StringBuilder("\n<${htmlName}")
        if (htmlClass != null) {
            sb.append(" class=\"$htmlClass\"")
        }
        if (!id.isNullOrBlank()) {
            sb.append(" id=\"$id\"")
        }
/*        if (style != null) {
            sb.append(" style=\"${style.toString()}\"")
        }*/
        attributes.forEach {
            sb.append(" ${it.key}=\"${it.value}\"")
        }
        events.forEach {
            sb.append(" ${it.key}=\"${it.value}\"")
        }

        return sb.append(">").toString()
    }

}

/** Implementazione di un tag normale, self-closing o con il tag di chiusura.
 * La classe non è istanziabile direttamente, ma solo tramite i metodi factory
 * definiti in HtmlContainer
 */
open class StandardTag internal constructor(
    htmlName: String,
    id: String? = null,
    htmlClass: String? = null,
    val closingTag: Boolean = false,
) :
    SingleTag(htmlName, id, htmlClass) {

    /** protetto, stampa tutto senza il tag di chiusura
     * @return stampa tutto senza il tag di chiusura
     */
    protected fun toOpenTag(): StringBuilder {
        return StringBuilder(super.toString())
    }

    override fun toString(): String {
        val sb = toOpenTag()
        if (closingTag) {
            sb.append("</$htmlName>")
        } else
            sb.insert(sb.length - 1, " /")
        return sb.toString()
    }

}