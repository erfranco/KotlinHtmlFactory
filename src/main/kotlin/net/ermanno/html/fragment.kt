package net.ermanno.html

open class Fragment(final override val doc: Doc) : HtmlContainer {

    final override val elements: MutableList<in HtmlElem> = mutableListOf()

    override fun toString(): String {
        val sb = StringBuilder()
        elements.forEach { sb.append(it) }
        return sb.toString()
    }
}

class TextFragment(doc: Doc, val text: String) : Fragment(doc) {
    init {
        elements.add(TextTag(doc, text, true))
    }
}