package br.com.eventos.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 04/08/20
 */

private val locale = Locale("pt", "BR")

fun Long?.formatDate (
    withLabel: Boolean = true,
    labelMessage: String = "Data do evento:",
    nullValueMessage: String = "Data não informada"
) : String {
    if (this == null) {
        return nullValueMessage
    }

    val formatter = SimpleDateFormat("dd/MM/yyyy", locale)
    val dateConverted = Date(this)

    return try {
        val dateFormatted = formatter.format(dateConverted)
        if (withLabel) "$labelMessage $dateFormatted"
        else dateFormatted
    } catch (e: Exception) {
        nullValueMessage
    }
}