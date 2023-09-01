package com.example.fecthapplication.common.entities

class ItemEntity(
    var id: Int,
    var listId: Int,
    var name: String,
) {
    override fun toString(): String {
        return "ItemEntity(id=$id, listId=$listId, name='$name')"
    }
}