package com.tselexx.orleanssorties.api.bytag.generatedjson

data class Location(
    val uid: Int,
    val name: String,
    val slug: String,
    val address: String,
    val image: Any,
    val imageCredits: Any,
    val postalCode: String,
    val city: String,
    val district: Any,
    val department: String,
    val region: String,
    val latitude: Double,
    val longitude: Double,
    val description: List<Any>,
    val access: List<Any>,
    val countryCode: String,
    val website: Any,
    val links: List<Any>,
    val insee: String,
    val phone: Any,
    val tags: Any,
    val timezone: String,
    val updatedAt: String,
    val country: Country
)