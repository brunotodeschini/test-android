package br.com.eventos.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 04/08/20
 */

private val locale = Locale("pt", "BR")

fun Float?.formattedPrice () : String {
    if (this == null) {
        return "Valor não informado"
    }

    val formatter = NumberFormat.getCurrencyInstance(locale)
    if (formatter is DecimalFormat) {
        formatter.isDecimalSeparatorAlwaysShown = true
    }

    return formatter.format(this)
}