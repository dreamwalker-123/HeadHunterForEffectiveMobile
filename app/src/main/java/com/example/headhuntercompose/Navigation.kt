package com.example.headhuntercompose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.designsystem.MyTypes
import com.example.entry.EntryRoute
import com.example.favorite.FavoriteRoute
import com.example.headhuntercompose.Routes.entryScreen
import com.example.headhuntercompose.Routes.favoriteTab
import com.example.headhuntercompose.Routes.messagesTab
import com.example.headhuntercompose.Routes.profileTab
import com.example.headhuntercompose.Routes.responsesTab
import com.example.headhuntercompose.Routes.searchTab
import com.example.headhuntercompose.Routes.tabBarItems
import com.example.headhuntercompose.Routes.vacancyScreen
import com.example.mainorsearch.SearchRoute
import com.example.model.TabBarItem
import com.example.vacancy.VACANCY
import com.example.vacancy.VacancyRoute

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    windowSizeClass: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController(),
) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    var showTabs by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            if (showTabs) {
                TabView(
                    tabBarItems = tabBarItems,
                    navigateToBarItem = { navController.navigate(it) },
                    selectedTabIndex = selectedTabIndex,
                    changeSelectedTabIndex = { selectedTabIndex = it })
            }
        }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = entryScreen) {
                composable(route = entryScreen) {
                    EntryRoute(
                        onMainScreenClick = {
                            navController.navigate(searchTab.title)
                            showTabs = true
                        }
                    )
                }

                composable(searchTab.title) {
                    SearchRoute(
                        // передача аргумента в CARD_PRODUCT_SCREEN и также
                        // одновременно происходит запись аргумента в Bundle
                        onVacancyClick = { navController.navigate("$vacancyScreen/${it}") }
                    )
                }

                composable("$vacancyScreen/{$VACANCY}",
                    // запись при получении аргумента в Bundle
                    arguments = listOf(navArgument(VACANCY) {
                        type = NavType.StringType
                        defaultValue = "0"
                    }
                    ))
                {
                    VacancyRoute(onUpClicked = navController::navigateUp)
                }

                composable(favoriteTab.title) {
                    FavoriteRoute(onVacancyClick = { navController.navigate("$vacancyScreen/${it}") })
                }

                composable(responsesTab.title) {
                    Text(responsesTab.title)
                }

                composable(messagesTab.title) {
                    Text(messagesTab.title)
                }

                composable(profileTab.title) {
                    Text(profileTab.title)
                }
            }
        }
    }
}

@Composable
fun TabView(
    tabBarItems: List<TabBarItem>,
    navigateToBarItem: (String) -> Unit,
    selectedTabIndex: Int,
    changeSelectedTabIndex: (Int) -> Unit)
{
    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    changeSelectedTabIndex(index)
                    navigateToBarItem(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = painterResource(id = tabBarItem.selectedIcon),
                        unselectedIcon = painterResource(id = tabBarItem.unselectedIcon),
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
                label = { Text(text = tabBarItem.title, style = MyTypes.tab_text) })
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: Painter,
    unselectedIcon: Painter,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            painter = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}

internal const val CATALOG_SCREEN = "CatalogScreen"
internal const val BASKET_SCREEN = "BasketScreen"
internal const val SPLASH_SCREEN = "SplashScreen"