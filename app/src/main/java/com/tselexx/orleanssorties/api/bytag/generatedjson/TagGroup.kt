package com.tselexx.orleanssorties.api.bytag.generatedjson

data class TagGroup(
    val name: String,
    val access: String,
    val slug: String,
    val tags: List<Tag>
)