package de.syntax.androidabschluss.ui.events.data.model

data class Events(
    val title: String = "",
    val description: String = "",
    val date: String="",
    val hour: String="",
    val location: String = "",
    var image: String = "",
    var id : String = ""
)