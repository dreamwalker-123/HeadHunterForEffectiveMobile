package com.example.headhuntercompose

import com.example.model.TabBarItem


object Routes {
    // setting up the individual tabs
    val searchTab = TabBarItem(
        title = "Поиск",
        selectedIcon = com.example.designsystem.R.drawable.search_blue,
        unselectedIcon = com.example.designsystem.R.drawable.search_black
    )
    val favoriteTab = TabBarItem(
        title = "Избранное",
        selectedIcon = com.example.designsystem.R.drawable.favorite_clicked,
        unselectedIcon = com.example.designsystem.R.drawable.favorite_default,
        badgeAmount = 1
    )
    val responsesTab = TabBarItem(
        title = "Отклики",
        selectedIcon = com.example.designsystem.R.drawable.responces_blue,
        unselectedIcon = com.example.designsystem.R.drawable.responces_black
    )
    val messagesTab = TabBarItem(
        title = "Сообщения",
        selectedIcon = com.example.designsystem.R.drawable.messages_black,
        unselectedIcon = com.example.designsystem.R.drawable.messages_black
    )
    val profileTab = TabBarItem(
        title = "Профиль",
        selectedIcon = com.example.designsystem.R.drawable.profile_blue,
        unselectedIcon = com.example.designsystem.R.drawable.profile_black
    )
    val entryScreen = "Entry Screen"
    val vacancyScreen = "Vacancy Screen"

    // creating a list of all the tabs
    val tabBarItems = listOf(searchTab, favoriteTab, responsesTab, messagesTab, profileTab)
}