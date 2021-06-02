package net.ermanno.html

//convenience extension function
fun <T : HtmlElem> MutableList<in T>.addAndReturn(elem: T): T {
    this.add(elem)
    return elem
}

/** Interfaccia che definisce il comportamento di un contenitore di tag.
 * Definisce i metodi Factory per istanziare i tag.
 * @author ermanno
 */
interface HtmlContainer : HtmlElem {

    val elements: MutableList<in HtmlElem>

    fun loadFragment(f: Fragment): Fragment {
        elements.add(f)
        return f
    }

    fun addTextBlock(rawText: String, encode: Boolean): HtmlContainer {
        elements.add(TextTag(doc, rawText, encode))
        return this
    }

    fun addBR(rawText: String? = null): HtmlContainer {
        addStandardTag("br", closingTag = false)
        if (rawText != null) addTextBlock(rawText, true)
        return this
    }

    fun addHR(): HtmlContainer {
        addStandardTag("hr", closingTag = false)
        return this
    }


    fun addImage(src: String, alt: String, id: String? = null, width: String, height: String): HtmlContainer {
        addStandardTag("img", id, null, closingTag = false)
            .addAttributes("src" to src, "alt" to alt, "height" to height, "width" to width)
        return this
    }

    fun div(
        id: String? = null,
        className: String? = null,
        text: (() -> String)? = null
    ): Div {
        val div = Div(doc, id, className)
        if (text != null) div.addTextBlock(text(), true)
        return elements.addAndReturn(div)
    }

    fun span(
        id: String? = null,
        className: String? = null,
        text: (() -> String)? = null
    ): ContainerTag {
        if (doc.doctype == DocType.XHTML_STRICT || doc.doctype == DocType.XHTML1_1) {
            if (this == doc || Fragment::class.isInstance(this)) throw HtmlException(
                "span not allowed at first level when XHTML_STRICT or XHTML1_1"
            )
        }
        val span = ContainerTag(doc, "span", id, className)
        if (text != null) span.addTextBlock(text(), true)
        return elements.addAndReturn(span)
    }

    fun addP(
        id: String? = null,
        className: String? = null,
        text: (() -> String)? = null
    ): ContainerTag {
        val p = P(doc, id, className)
        if (text != null) p.addTextBlock(text(), true)
        return elements.addAndReturn(p)
    }

    fun addStandardTag(
        htmlName: String,
        id: String? = null,
        className: String? = null,
        closingTag: Boolean = true
    ): StandardTag {
        return elements.addAndReturn(StandardTag(doc, htmlName, id, className, closingTag))
    }

    fun addContainerTag(htmlName: String, id: String? = null, className: String? = null): ContainerTag {
        return elements.addAndReturn(ContainerTag(doc, htmlName, id, className))
    }

    fun addContainerTag(tag: ContainerTag): ContainerTag {
        return elements.addAndReturn(tag)
    }

    fun table(id: String? = null, className: String? = null, width: String? = null, border: Int? = null): Table {
        return elements.addAndReturn(Table(doc, id, className, width, border))
    }

    fun addForm(methodType: Method = Method.GET, id: String? = null, className: String? = null): Form {
        return elements.addAndReturn(Form(doc, id, className, methodType))
    }

    /**
     * @deprecated firstValue evaluated only in ordered case
     * @deprecated oltype evaluated only in ordered case
     */
    fun addList(
        id: String? = null,
        className: String? = null,
        ordered: Boolean = false,
/*        deprecated oltype: OLTYPE = OLTYPE.NUMBER,
        firstValue: Int = 0,*/
    ): AbstractList {
        val ct = if (ordered) OrderedList(doc, id, className) else UnorderedList(doc, id, className)
        return elements.addAndReturn(ct)
    }
}
