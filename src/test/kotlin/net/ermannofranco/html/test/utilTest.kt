package net.ermannofranco.html.test

import net.ermanno.html.StringUtils

fun main() {
    val str="""<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><title>Mini HTML Parser 2021 - by ErmannoFranco</title>

<STYLE>body {background-color: #FEDCBA}
table {font: bold 12px Helvetica;text-align:center;border:4px groove}
input {font: 14px Verdana}
.text {color:red;border:3px green}
.red {color:green;border:3px red}
.buttonred {color:red}
.middle {background-color: #0000BA}
</STYLE>
<script language="JAVASCRIPT">
<!--
/* autore Ermanno Franco 2021 */

function codifica(str, spaziPerTab) {
    var spazi="", mappa={}, symbol = '';
    for (j=0;j<spaziPerTab;j++) {
        spazi+="&nbsp;";
    }
    //hashmap che collega il simbolo con la entity html
    mappa= codificaMappa();

    var res = str.replace(/&/g, '&amp;');
         for (symbol in mappa)  {
         res = rimpiazza(res, symbol, mappa[symbol]);
         }
         res = res.replace(/\r/g, '')
         .replace(/\t/g, spazi)
         .replace(/\n/g, '<br />\r\n');
    return res;
}

function rimpiazza(stringa, symbol, replacmnt)   {
    var sRegExInput = new RegExp(symbol, "g");
    var ret = stringa.replace(sRegExInput ,replacmnt);
    return ret;
}

function assegnaValore(stringa,oggTarget)      {      //procedura indipendente dal contesto
      oggTarget.value=stringa;
}

function assegnaEscape(stringa, oggTarget)      {      //procedura indipendente dal contesto
      oggTarget.value=escape(stringa);
}

function pulisci(arrDiOggetti)      {       //procedura indipendente dal contesto
      for (i=0;i<arrDiOggetti.length;i++)      {
            arrDiOggetti[i].value="";
      }
}

function codificaMappa() {
    var entities = {}, hash_map = {}, decimal = 0;
    //entities['38'] = '&amp;';//lo faccio a parte, per evitare doppie sostituzioni
    entities['34'] = '&quot;';
    entities['39'] = '&#39;';
    entities['60'] = '&lt;';
    entities['62'] = '&gt;';
    //entities['160'] = '&nbsp;';
    entities['161'] = '&iexcl;';
    entities['162'] = '&cent;';
    entities['163'] = '&pound;';
    entities['164'] = '&curren;';
    entities['165'] = '&yen;';
    entities['166'] = '&brvbar;';
    entities['167'] = '&sect;';
    entities['168'] = '&uml;';
    entities['169'] = '&copy;';
    entities['170'] = '&ordf;';
    entities['171'] = '&laquo;';
    entities['172'] = '&not;';
    entities['173'] = '&shy;';
    entities['174'] = '&reg;';
    entities['175'] = '&macr;';
    entities['176'] = '&deg;';
    entities['177'] = '&plusmn;';
    entities['178'] = '&sup2;';
    entities['179'] = '&sup3;';
    entities['180'] = '&acute;';
    entities['181'] = '&micro;';
    entities['182'] = '&para;';
    entities['183'] = '&middot;';
    entities['184'] = '&cedil;';
    entities['185'] = '&sup1;';
    entities['186'] = '&ordm;';
    entities['187'] = '&raquo;';
    entities['188'] = '&frac14;';
    entities['189'] = '&frac12;';
    entities['190'] = '&frac34;';
    entities['191'] = '&iquest;';
    entities['192'] = '&Agrave;';
    entities['193'] = '&Aacute;';
    entities['194'] = '&Acirc;';
    entities['195'] = '&Atilde;';
    entities['196'] = '&Auml;';
    entities['197'] = '&Aring;';
    entities['198'] = '&AElig;';
    entities['199'] = '&Ccedil;';
    entities['200'] = '&Egrave;';
    entities['201'] = '&Eacute;';
    entities['202'] = '&Ecirc;';
    entities['203'] = '&Euml;';
    entities['204'] = '&Igrave;';
    entities['205'] = '&Iacute;';
    entities['206'] = '&Icirc;';
    entities['207'] = '&Iuml;';
    entities['208'] = '&ETH;';
    entities['209'] = '&Ntilde;';
    entities['210'] = '&Ograve;';
    entities['211'] = '&Oacute;';
    entities['212'] = '&Ocirc;';
    entities['213'] = '&Otilde;';
    entities['214'] = '&Ouml;';
    entities['215'] = '&times;';
    entities['216'] = '&Oslash;';
    entities['217'] = '&Ugrave;';
    entities['218'] = '&Uacute;';
    entities['219'] = '&Ucirc;';
    entities['220'] = '&Uuml;';
    entities['221'] = '&Yacute;';
    entities['222'] = '&THORN;';
    entities['223'] = '&szlig;';
    entities['224'] = '&agrave;';
    entities['225'] = '&aacute;';
    entities['226'] = '&acirc;';
    entities['227'] = '&atilde;';
    entities['228'] = '&auml;';
    entities['229'] = '&aring;';
    entities['230'] = '&aelig;';
    entities['231'] = '&ccedil;';
    entities['232'] = '&egrave;';
    entities['233'] = '&eacute;';
    entities['234'] = '&ecirc;';
    entities['235'] = '&euml;';
    entities['236'] = '&igrave;';
    entities['237'] = '&iacute;';
    entities['238'] = '&icirc;';
    entities['239'] = '&iuml;';
    entities['240'] = '&eth;';
    entities['241'] = '&ntilde;';
    entities['242'] = '&ograve;';
    entities['243'] = '&oacute;';
    entities['244'] = '&ocirc;';
    entities['245'] = '&otilde;';
    entities['246'] = '&ouml;';
    entities['247'] = '&divide;';
    entities['248'] = '&oslash;';
    entities['249'] = '&ugrave;';
    entities['250'] = '&uacute;';
    entities['251'] = '&ucirc;';
    entities['252'] = '&uuml;';
    entities['253'] = '&yacute;';
    entities['254'] = '&thorn;';
    entities['255'] = '&yuml;';
    // ascii decimals to real symbols
    for (decimal in entities) {
        symbol = String.fromCharCode(decimal);
        hash_map[symbol] = entities[decimal];
    }
    return hash_map;
}

// -->
</script>
</head>
<body>
<FORM method='get'>
<TABLE align='center'>
<TR>
<TD>Scrivere il testo da codificare
</TD>
<TD>
<TABLE align='center'>
<TR>
<TD>Spazi per tab:
</TD>
</TR>
<TR>
<TD>
<SELECT name="spacetab">
<OPTION value='2'>2</OPTION>
<OPTION value='4' selected='true'>4</OPTION>
<OPTION value='6'>6</OPTION>
<OPTION value='8'>8</OPTION>
</SELECT>
</TD>
</TR>
</TABLE>
</TD>
<TD>
<INPUT type='button' value='PULISCI' onclick='pulisci(new Array(this.form.input,this.form.output))'></INPUT>
</TD>
</TR>
<TR>
<TD colspan='3'>
<TEXTAREA name="input" cols='100%' rows='18' class='red'></TEXTAREA>
</TD>
</TR>
<TR class="middle">
<TD colspan='2'>
<INPUT type='button' value='Codifica HTML:' class='buttonred' onclick='assegnaValore(codifica(this.form.input.value,this.form.spacetab.value), this.form.output)'></INPUT>
</TD>
<TD>
<INPUT type='button' value='Escape:' class='buttonred' onclick='assegnaEscape(this.form.input.value, this.form.output)'></INPUT>
</TD>
</TR>
<TR>
<TD colspan='3'>Risultato:
</TD>
</TR>
<TR>
<TD colspan='3'>
<TEXTAREA name="output" cols='100%' rows='18' class='green' readonly='true'></TEXTAREA>
</TD>
</TR>
</TABLE>
</FORM>
</body>
<!-- PAGINA GENERATA AUTOMATICAMENTE DA CODICE JAVA che usa la libreria HtmlFactory -->
<!-- AUTORE DELLA LIBRERIA HtmlFactory: Ermanno Franco (ermanno.franco@gmail.com) -->
<!-- Data generazione: Sun Feb 28 22:47:45 CET 2021 -->
</html>""".trimMargin()
    println(StringUtils.encodeHtml(str))
}