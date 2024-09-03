package com.example.model

data class TabBarItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val badgeAmount: Int? = null
)