package net.ermannofranco.html.test

import net.ermanno.html.DocType
import net.ermanno.html.Doc

fun main() {
    Prova3()
}

class Prova3 {
    /**
     * Creates a new instance of Prova3
     */
    init {
        val doc = Doc("Mini HTML Pippo", DocType.XHTML_STRICT)
        doc.addStyleTag("BODY", "background-color:#ABCDEF")
            .addStyleTag("TR", "text-align:center")
            doc.script(
                """function assegnaValore(stringa,oggTarget)	{	//procedura indipendente dal contesto
	oggTarget.value=stringa;
}function assegnaEscape(stringa, oggTarget)	{	//procedura indipendente dal contesto
oggTarget.value=escape(stringa);
}

function codificaHTML(stringa,spaziPerTab)	{ //procedura indipendente dal contesto
	var i,j,c,ret;
	ret="";
	for (i=0;i<stringa.length;i++)	{
		c=stringa.charAt(i);
		if (c=="\n")	{
			ret+="<BR>\r\n";
		}
		else if (c=="\r")	{//nulla, ci pensa \n
			}
		else if (c=="\t")	{
		for (j=0;j<spaziPerTab;j++) {
				ret+="&nbsp;";
			}
			}
		else if (c=="<")	{
			ret+="&lt;";
			}
		else if (c==">")	{
			ret+="&gt;";
			}
		else if (c==" ")	{
			ret+="&nbsp;";
			}
		else if (c=="\"")	{
			ret+="&quot;";
			}
		else if (c=="à")	{
			ret+="&agrave;";
			}
		else if (c=="è")	{
			ret+="&egrave;";
			}
		else if (c=="é")	{
			ret+="&eacute;";
			}
		else if (c=="ì")	{
			ret+="&igrave;";
			}
		else if (c=="ò")	{
			ret+="&ograve;";
			}
		else if (c=="ù")	{
			ret+="&oacute;";
			}
		else if (c=="&")	{
			ret+="&amp;";//permette la ricorsività
			}
		else {
			ret+=c;
			}
	}
	return ret;
}

function pulisci(arrDiOggetti)	{ 	//procedura indipendente dal contesto
	for (i=0;i<arrDiOggetti.length;i++)	{
		arrDiOggetti[i].value="";
	}
}    
""")
        doc.table().addRow().addCell().addA("pippo","www.pippo.httm.com" )
            .addTextBlock(" &aaaaaaaa <div>rrrr", true).div(null, null, null).addTextBlock("ddiiiiiiiiiiiiiivvvvvvvvvv", false)

        ////////tooooooooodooooooooooo
        println(doc)
    }
}