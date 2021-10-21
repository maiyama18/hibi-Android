package com.muijp.hibi.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muijp.hibi.ui.memoedit.MemoEditScreen
import com.muijp.hibi.ui.memolist.MemoListScreen
import com.muijp.hibi.ui.memolist.MemoListViewModel
import com.muijp.hibi.ui.memosearch.MemoSearchScreen
import com.muijp.hibi.ui.theme.HibiTheme

enum class HibiScreen(val route: String) {
    MemoList("list"),
    MemoCreate("create"),
    MemoEdit("edit/{id}"),
    MemoSearch("search"),
}

@ExperimentalFoundationApi
@Composable
fun HibiApp() {
    val navController = rememberNavController()

    HibiTheme {
        NavHost(navController = navController, startDestination = HibiScreen.MemoList.route) {
            composable(HibiScreen.MemoList.route) {
                val viewModel = hiltViewModel<MemoListViewModel>()
                MemoListScreen(viewModel)
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