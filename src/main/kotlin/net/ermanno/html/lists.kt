package net.ermanno.html

abstract class AbstractList(
    htmlName: String, id: String? = null,
    className: String? = null,
) : ContainerTag(
    htmlName, id,
    className
) {
    fun addItems(vararg s: String): ContainerTag {
        s.forEach {
            val item = ListItem(it)
            elements.add(item)
        }
        return this
    }

    fun addItem(
        id: String? = null,
        className: String? = null
    ): ListItem {
        return elements.addAndReturn(ListItem(id = id, className = className))
    }
}

internal class OrderedList(
    id: String? = null,
    className: String? = null,
    /* deprecated val start: Int = 0,
    val oltype: OLTYPE = OLTYPE.NUMBER*/
) : AbstractList(
    "ol", id, className
) //{
/*    init {
        { start > 0 }.let { addAttribute("start", start.toString()) }
        if (oltype != OLTYPE.NUMBER) addAttribute("type", oltype.value)
    }*/
//}

internal class UnorderedList(
    id: String? = null,
    className: String? = null,
) : AbstractList(
    "ul", id, className
)

class ListItem internal constructor(
    text: String? = null,
    id: String? = null,
    className: String? = null
) : Block("li", id, className) {
    init {
        text?.let { addTextBlock(text, true) }
    }
}
