package br.com.eventos.model

data class Event(
    val people: List<People>,
    val date: Long,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Float,
    val title: String,
    val id: String,
    val cupons: List<Cupons>
)