package net.ermanno.html


/** Tag che può contenere altri tag.
 * Fornisce metodi factory per istanziare ed aggiungere i tag.
 * Fornisce metodi per recuperare gli i tag contenuti, come una Collection.
 * La classe non è istanziabile direttamente, ma solo tramite i metodi factory
 * definiti in HtmlContainer
 */
open class ContainerTag internal constructor(
    doc: Doc,
    htmlName: String,
    id: String? = null,
    className: String? = null,
) :
    StandardTag(doc, htmlName, id, className, closingTag = true), HtmlContainer {

    final override val elements: MutableList<in HtmlElem> = mutableListOf()

    protected fun clear() {
        synchronized(elements) {
            elements.clear()
        }
    }

    /** permette di sapere se il contenitore non ha altri tag annidati
     * @return se il contenitore non ha altri tag annidati
     */
/*    val isEmpty: Boolean
        get() = elements.isEmpty()*/

    /**
     * @return stampa tutto
     */
    override fun toString(): String {

        val sb = super.toOpenTag()
        elements.forEach {
            sb.append(it)
        }
        sb.append("</$htmlName>")
        return sb.toString()
    }


    /** metodo factory specializzato per un pulsante INPUT SUBMIT
     * @param value
     * @return il riferimento al tag
     */
    fun addSubmit(value: String): StandardTag {
        val submit: StandardTag = addStandardTag("input", closingTag = false)
        submit.addAttribute("type", "submit").addAttribute("value", value)
        return submit
    }

    /** metodo factory specializzato per un pulsante INPUT RESET
     * @param value
     * @return il riferimento al tag
     */
    fun addReset(value: String): StandardTag {
        val reset: StandardTag = addStandardTag("input", closingTag = false)
        reset.addAttribute("type", "reset").addAttribute("value", value)
        return reset
    }

    /**
     * @param id
     * @param value
     * @return il riferimento al tag
     */
    fun addButton(value: String, id: String? = null): StandardTag {
        val button: StandardTag = addStandardTag("input", id = id, closingTag = false)
        button.addAttributes("type" to "button", "value" to value)
        return button
    }

    /** metodo factory specializzato per un campo INPUT TEXT
     * @param id
     * @param value
     * @return il riferimento al tag
     */
    fun addTextInput(id: String?, value: String?): StandardTag {
        val text: StandardTag =
            if (id != null) addStandardTag("input", id, closingTag = false) else addStandardTag(
                "input",
                closingTag = false
            )
        text.addAttribute("type", "text")
        if (value != null) text.addAttribute("value", value)
        return text
    }

    fun addTextarea(
        id: String? = null,
        cols: String? = null,
        rows: String? = null,
        text: String? = null,
        readOnly: Boolean = false,
    ): ContainerTag {
        if (cols == null && rows == null) throw HtmlException("cols and rows cannot be simultaneously null")
        val area = addContainerTag("textarea", id)
        if (cols != null) area.addAttribute("cols", cols)
        if (rows != null) area.addAttribute("rows", rows)
        if (text != null) area.addTextBlock(text, true)
        if (readOnly) area.addAttribute("readonly", "readonly")
        return area
    }

    /** metodo factory specializzato per un campo INPUT PASSWORD
     * @param id
     * @param value
     * @return il riferimento al tag
     */
    fun addPasswordInput(id: String?, value: String?): StandardTag {
        val password: StandardTag =
            if (id != null) addStandardTag("input", id, closingTag = false) else addStandardTag(
                "input",
                closingTag = false
            )
        password.addAttribute("type", "password")
        if (value != null) password.addAttribute("value", value)
        return password
    }

    /** metodo factory specializzato per un campo INPUT HIDDEN
     * @param id
     * @param value
     * @return il riferimento al tag
     */
    fun addHiddenInput(id: String?, value: String?): StandardTag {
        val hidden = if (id != null) addStandardTag("input", id, closingTag = false) else addStandardTag(
            "input",
            closingTag = false
        )
        hidden.addAttribute("type", "hidden")
        if (value != null) hidden.addAttribute("value", value)
        return hidden
    }

    /** metodo factory specializzato per un campo INPUT CHECKBOX
     * @param id
     * @param value
     * @return il riferimento al tag
     */
    fun addCheckboxInput(id: String?, value: String? = null, checked: Boolean = false): StandardTag {
        val checkbox = if (id != null) addStandardTag("input", id, closingTag = false) else addStandardTag(
            "input",
            closingTag = false
        )
        checkbox.addAttribute("type", "checkbox")
        if (value != null) checkbox.addAttribute("value", value)
        if (checked) checkbox.addAttribute("checked", "checked")
        return checkbox
    }

    /** metodo factory specializzato per un campo INPUT RADIO
     * @param id
     * @param value
     * @return il riferimento al tag
     */
    fun addRadioInput(id: String?, value: String): StandardTag {
        val radio = if (id != null) addStandardTag("input", id, closingTag = false) else addStandardTag(
            "input",
            closingTag = false
        )
        radio.addAttributes("type" to "radio", "value" to value)
        return radio
    }

    /** metodo factory specializzato per un campo INPUT SELECT
     * @return il riferimento al tag
     * @param id
     * @param className
     */
    /** metodo factory specializzato per un campo INPUT SELECT
     * @return il riferimento al tag
     */
    fun addSelect(id: String? = null, className: String? = null): Select {
        return elements.addAndReturn(Select(doc, id, className))
    }

}

abstract class Block internal constructor(doc: Doc, htmlName: String, id: String? = null, className: String? = null) :
    ContainerTag(doc, htmlName, id, className) {
    fun addA(
        id: String? = null,
        className: String? = null,
        href: String? = null,
        text: (() -> String)? = null
    ): ContainerTag {
        val ct = ContainerTag(doc,"a", id, className)
        if (href != null) {
            ct.addAttribute("href", href)
        }
        if (text != null) ct.addTextBlock(text(), true)
        return elements.addAndReturn(ct)
    }
}

class Div internal constructor(doc: Doc, id: String? = null, className: String? = null) :
    Block(doc, "div", id, className)

class P internal constructor(doc: Doc, id: String? = null, className: String? = null) : Block(doc, "p", id, className)