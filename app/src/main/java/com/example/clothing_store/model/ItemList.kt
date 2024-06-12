package com.example.clothing_store.model

data class Item(
    val itemId: String,
    val name: String,
    val price: Double,
    val image: String?
)

data class ItemList(
    val items: List<Item>
)