package net.ermanno.html

open class Fragment : HtmlContainer {

    final override val elements: MutableList<in HtmlElem> = mutableListOf()

    override fun toString(): String {
        val sb = StringBuilder()
        elements.forEach { sb.append(it) }
        return sb.toString()
    }
}

class TextFragment(val text: String) : Fragment() {
    init {
        elements.add(TextTag(text, true))
    }
}