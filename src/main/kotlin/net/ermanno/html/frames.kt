package net.ermanno.html

enum class Scroll { YES, NO, AUTO }

/**
 *
 * @author  ermanno
 */
class FrameSet internal constructor(doc: Doc, val cols: String?, val rows: String?) :
    ContainerTag(doc, "frameset") {

    private val frames = mutableListOf<SingleTag>()

    fun addFrameset(cols: String?, rows: String?): FrameSet {
        val frset = FrameSet(doc, cols, rows)
        frames.add(frset)
        return frset
    }

    fun setNOFrameBorder(): FrameSet {
        addAttribute("frameborder", "no")
        return this
    }


    fun addFrame(nome: String?, src: String, scrolling: Scroll = Scroll.AUTO): Frame {
        val fr = Frame(doc, nome, src, scrolling)
        frames.add(fr)
        return fr
    }

    override fun toString(): String {
        super.clear() //qualsiasi oggetto tag aggiunto involontariamente dal programmatore alla classe madre viene eliminato
        frames.forEach {
            elements.add(it)
        }
        addContainerTag("noframes").addTextBlock(
            "QUESTA PAGINA UTILIZZA I FRAMES, CHE QUESTO BROWSER NON E' IN GRADO DI VISUALIZZARE.",
            true
        )
            .addBR("SI PREGA DI PASSARE AD UNA VERSIONE PIU' AGGIORNATA.")

        return super.toString()
    }


    init {
        if (cols == null && rows == null) throw HtmlException("rows and cols cannot be simultaneously null")
        if (cols != null) addAttribute("cols", cols)
        if (rows != null) addAttribute("rows", rows)
    }
}

/**
 *
 * @author  ermanno
 */
class Frame internal constructor(doc: Doc, name: String?, val src: String, val scrolling: Scroll) :
    SingleTag(doc, "frame", name) {
    fun setNoresize(): Frame {
        addAttribute("noresize", "true")
        return this
    }

    fun setMarginHeight(marginHeight: String): Frame {
        addAttribute("marginheight", marginHeight)
        return this
    }

    fun setMarginWidth(marginWidth: String): Frame {
        addAttribute("marginwidth", marginWidth)
        return this
    }

    fun setBorder(border: String): Frame {
        addAttribute("border", border)
        return this
    }

    fun setNOFrameBorder(): Frame {
        addAttribute("frameborder", "NO")
        return this
    }


    init {
        addAttributes("src" to src, "scrolling" to scrolling.toString())
    }
}
