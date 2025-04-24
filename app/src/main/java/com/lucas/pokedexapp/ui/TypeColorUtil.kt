package com.lucas.pokedexapp.ui

import android.content.Context
import android.graphics.Color
import android.widget.TextView

fun setTypeColor(textView: TextView, type: String) {
    val context: Context = textView.context
    val color = when (type.lowercase()) {
        "normal" -> "#A8A77A"
        "fire" -> "#EE8130"
        "water" -> "#6390F0"
        "electric" -> "#F7D02C"
        "grass" -> "#7AC74C"
        "ice" -> "#96D9D6"
        "fighting" -> "#C22E28"
        "poison" -> "#A33EA1"
        "ground" -> "#E2BF65"
        "flying" -> "#D3D3D3"
        "psychic" -> "#F95587"
        "bug" -> "#A6B91A"
        "rock" -> "#B6A136"
        "ghost" -> "#735797"
        "dragon" -> "#6F35FC"
        "dark" -> "#705746"
        "steel" -> "#B7B7CE"
        "fairy" -> "#D685AD"
        else -> "#FFA000" // fallback padrão
    }

    textView.setBackgroundColor(Color.parseColor(color))
}
