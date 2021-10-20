package com.muijp.hibi.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muijp.hibi.ui.memoedit.MemoEditScreen
import com.muijp.hibi.ui.memolist.MemoListScreen
import com.muijp.hibi.ui.memosearch.MemoSearchScreen
import com.muijp.hibi.ui.theme.HibiTheme

enum class HibiScreen(val route: String) {
    MemoList("list"),
    MemoCreate("create"),
    MemoEdit("edit/{id}"),
    MemoSearch("search"),
}

@Composable
fun HibiApp() {
    val navController = rememberNavController()

    HibiTheme {
        NavHost(navController = navController, startDestination = HibiScreen.MemoList.route) {
            composable(HibiScreen.MemoList.route) {
                MemoListScreen()
            }

            composable(HibiScreen.MemoCreate.route) {
                MemoEditScreen()
            }

            composable(
                HibiScreen.MemoEdit.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val memoId = entry.arguments?.getString("id")
                print(memoId)

                MemoEditScreen()
            }

            composable(HibiScreen.MemoSearch.route) {
                MemoSearchScreen()
            }
        }
    }
}