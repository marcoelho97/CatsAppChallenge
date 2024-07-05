package com.example.catsappchallenge.utils

fun splitLifeSpan(lifeSpan: String?): Pair<Float, Float> {
    if(lifeSpan == null) {
        return 0f to 0f
    }

    // Make sure the number of spaces in between both number won't matter
    val lifeSpanSplit = lifeSpan.split("-").map { it.trim() }

    // Considering only the first and last value, regardless of how many values were provided
    var lifeSpanValues = convertNumbers(
        lower = lifeSpanSplit.first(),
        higher = lifeSpanSplit.last()
    )

    // Make sure the lowerLifeSpan is always lower than the higherLifeSpan
    if(lifeSpanValues.first > lifeSpanValues.second) {
        lifeSpanValues = lifeSpanValues.second to lifeSpanValues.first
    }

    return lifeSpanValues
}

fun convertNumbers(lower: String, higher: String): Pair<Float, Float> {
    val lowerLifeSpan = safeStringToFloat(lower)
    val higherLifeSpan = safeStringToFloat(higher)

    /*
     *    Make sure it always returns a Pair of floats
     *    If neither could be converted to Float, they'll both be 0
     */
    return when {
        lowerLifeSpan == null && higherLifeSpan == null -> 0f to 0f
        lowerLifeSpan == null  -> higherLifeSpan!! to higherLifeSpan
        higherLifeSpan == null -> lowerLifeSpan to lowerLifeSpan
        else -> lowerLifeSpan to higherLifeSpan
    }
}

fun safeStringToFloat(input: String): Float? {
    return input.toFloatOrNull()
}
