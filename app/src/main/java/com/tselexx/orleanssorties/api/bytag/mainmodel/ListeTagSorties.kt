package com.tselexx.orleanssorties.api.bytag.mainmodel

import com.tselexx.orleanssorties.api.bytag.generatedjson.CustomEvent

data class ListeTagSorties(
    val readme: String,
    val total: Int,
    val offset: Int,
    val limit: Int,
    val events: List<CustomEvent>
)
