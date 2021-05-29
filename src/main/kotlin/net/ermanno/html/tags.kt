package net.ermanno.html

enum class Align { CENTER, LEFT, RIGHT }
enum class METHOD { GET, POST, PUT }

enum class FormEvent(private val event: String) : HtmlEvent {
    FOCUS("onfocus"), BLUR("onblur"), CHANGE("onchange"), INPUT("oninput"),
    SUBMIT("onsubmit"), RESET("onreset"), SELECT("onselect");

    override fun smallName(): String {
        return event
    }
}

/** La formalizzazione del tag <CODE>Table</CODE>.
 * Contiene i metodi per generare righe, dalle quali si generano colonne.
 * @author ermanno
 */
class Table
/** Creates a new instance of Table only in subclasses and package
 * @param id
 * @param classname
 */
internal constructor(id: String?, classname: String?, val width: String? = null, border: Int? = null) :
    ContainerTag("table", id, classname) {

    private var align = Align.CENTER

    init {
        width?.let { addAttribute("width", width) }
        border?.let { if (border > 0) addAttribute("border", "$border") }
    }

    fun addBorder(border: Int = 1): Table {
        if (border > 0) addAttribute("border", "$border")
        return this
    }

    /** Genera una riga e la aggiunge automaticamente agli oggetti html
     * @param clas la classe CSS della riga.
     * Utile quando si formattano i titoli
     * @return l'oggetto riga
     */
    fun addRow(clas: String? = null): Row {
        return addRow(null, clas)
    }

    fun addRow(cells: Int): List<Cell> {
        val row = addRow(null, null)
        val ret = mutableListOf<Cell>()
        (0 until cells).forEach {
            ret.add(row.addCell())
        }
        return ret
    }

    fun addRow(id: String? = null, classname: String? = null): Row {
        return elements.addAndReturn(Row(id, classname))
    }

    fun setAlign(align: Align) {
        this.align = align
        addAttribute("align", align.toString())
    }

    //disattivato il testo, non si aggiunge testo in una tabella cosí
    override fun addTextBlock(rawText: String, encode: Boolean): ContainerTag {
        return this
    }

}

/** La riga di una tabella
 * @author ermanno
 */
class Row
/** Creates a new instance of Row only in subclasses and package  */
/** protetto.
 * per ora il parametro columns non ha influenza
 * @param id
 * @param classname
 */
internal constructor(id: String?, classname: String?) :
    ContainerTag("tr", id, classname) {
    //crea una cella senza niente
    /** aggiunge un oggetto cella
     * @return l'oggetto cella
     */
    fun addCell(): Cell {
        return addCell(null, null, null)
    }
    //crea una cella volendo con uno spazio non cassato
    /** aggiunge un oggetto cella, con la possibilità di assegnare uno spazio non
     * eliminabile
     * @param blankSpace true se la cella presenta uno spazio vuoto non ignorabile
     * @return l'oggetto cella
     */
    fun addCell(blankSpace: Boolean): Cell {
        return if (blankSpace) addCell(Ents.BLANK.entity, null, null) else addCell(null, null, null)
    }

    /** Crea una cella ed assegna il testo
     * @param text il testo
     * @return l'oggetto cella
     */
    fun addCell(text: String): Cell {
        return addCell(text, null, null)
    }

    /** crea una cella e le assegna id, nome ecc...
     * @param text
     * @param id
     * @param classname
     * @return l'oggetto cella
     */
    fun addCell(text: String?, id: String?, classname: String?): Cell {
        val cell = Cell(text, id, classname)
        return elements.addAndReturn(cell)
    }

    fun addCell(colspan: Int): Cell {
        val cell = Cell(null, null, null)
        cell.addAttribute("colspan", "$colspan")
        return elements.addAndReturn(cell)
    }

    //disattivato il testo, non si aggiunge testo in una riga così
    override fun addTextBlock(rawText: String, encode: Boolean): HtmlContainer {
        return this
    }
}

/** La formalizzazione della cella Html
 * L'oggetto va istanziato dal metodo factory addCell di HtmlRow
 * @author ermanno
 */
class Cell internal constructor(
    rawText: String?,
    id: String?,
    classname: String?,
) :
    Block("td", id, classname) {
    init {
        if (rawText != null) {
            addTextBlock(rawText, true)
        }
    }
}

/** Formalizzazione del form.
 * Fornisce metodi specializzati di costruzione
 * @author ermanno
 */
class Form internal constructor(
    id: String?,
    classname: String?,
    methodType: METHOD = METHOD.GET,
) :
    ContainerTag("form", id, classname) {
    init {
        setMethod(methodType)
    }

    /** imposta il metodo di spedizione. E' più comodo farlo dal costruttore
     * @param methodType valore numerico per il tipo di spedizione.
     * Costante di Form
     * @return un riferimento a se stesso
     */
    fun setMethod(methodType: METHOD): Form {
        addAttribute("method", methodType.toString())
        return this
    }

    /** Imposta il target di spedizione
     * @param url l'indirizzo bersaglio
     * @return un riferimento a se stesso
     */
    fun setAction(url: String): Form {
        addAttribute("action", url)
        return this
    }
}

/** formalizzazione del tag SELECT
 * @author ermanno
 */
class Select
/** Protetto, Creates a new instance of SelectInput
 * @param id
 * @param classname
 */
internal constructor(id: String?, classname: String?) :
    ContainerTag("select", id, classname) {
    /** metodo factory che inserisce un tag OPTION
     * @param value il valore del tag OPTION (non il nome!!!)
     * @return un tag OPTION
     */
    fun addOption(value: String, text: String): Select {
        val option = ContainerTag("option")
        option.addAttribute("value", value)
        option.addTextBlock(text, true)
        elements.add(option)
        return this
    }

    fun addSelectedOption(value: String, text: String): Select {
        val option = ContainerTag("option")
        option.addAttribute("value", value).addAttribute("selected", "selected")
        option.addTextBlock(text, true)
        elements.add(option)
        return this
    }
}

class TextTag(rawText: String, encode: Boolean) : HtmlElem {
    private val text: String


    override fun toString(): String {
        return text
    }

    init {
        text = if (encode) StringUtils.encodeHtml(rawText) else rawText
    }
}

