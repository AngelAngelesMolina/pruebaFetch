package com.example.fecthapplication.common.utils

sealed class Category(var isSelected: Boolean = false) {
    object Uno : Category()
    object Dos : Category()
    object Tres : Category()
    object Cuatro : Category()
}
