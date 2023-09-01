package com.example.fecthapplication.common.entities

sealed class Category(var value:Int, var isSelected: Boolean = false) {
    object Uno : Category(1)
    object Dos : Category(2)
    object Tres : Category(3)
    object Cuatro : Category(4)
}
