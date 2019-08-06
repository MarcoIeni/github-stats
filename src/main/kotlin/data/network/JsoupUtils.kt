package data.network

import org.jsoup.select.Elements

fun getIntValuesFromJsoupElementList(elementList: Elements): List<Int> =
    elementList.mapNotNull { it.text().replace(",", "").toIntOrNull() }
