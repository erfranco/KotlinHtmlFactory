package net.ermanno.html

abstract class AbstractList(
    doc: Doc,
    htmlName: String, id: String? = null,
    className: String? = null,
) : ContainerTag(
    doc,
    htmlName, id,
    className
) {
    fun addItems(vararg s: String): ContainerTag {
        s.forEach {
            val item = ListItem(doc, it)
            elements.add(item)
        }
        return this
    }

    fun addItem(
        id: String? = null,
        className: String? = null
    ): ListItem {
        return elements.addAndReturn(ListItem(doc, id = id, className = className))
    }
}

internal class OrderedList(
    doc: Doc,
    id: String? = null,
    className: String? = null,
    /* deprecated val start: Int = 0,
    val oltype: OLTYPE = OLTYPE.NUMBER*/
) : AbstractList(
    doc, "ol", id, className
) //{
/*    init {
        { start > 0 }.let { addAttribute("start", start.toString()) }
        if (oltype != OLTYPE.NUMBER) addAttribute("type", oltype.value)
    }*/
//}

internal class UnorderedList(
    doc: Doc,
    id: String? = null,
    className: String? = null,
) : AbstractList(
    doc, "ul", id, className
)

class ListItem internal constructor(
    doc: Doc,
    text: String? = null,
    id: String? = null,
    className: String? = null
) : Block(doc, "li", id, className) {
    init {
        text?.let { addTextBlock(text, true) }
    }
}
