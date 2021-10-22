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
import com.muijp.hibi.ui.memoedit.MemoEditViewModel
import com.muijp.hibi.ui.memolist.MemoListScreen
import com.muijp.hibi.ui.memolist.MemoListViewModel
import com.muijp.hibi.ui.memosearch.MemoSearchScreen
import com.muijp.hibi.ui.theme.HibiTheme

sealed class HibiScreen(val route: String) {
    class MemoList: HibiScreen("list")
    class MemoCreate: HibiScreen("create")
    class MemoEdit: HibiScreen("edit/{id}") {
        fun fullRoute(id: String) = "edit/$id"
    }
    class MemoSearch: HibiScreen("search")
}

@ExperimentalFoundationApi
@Composable
fun HibiApp() {
    val navController = rememberNavController()

    HibiTheme {
        NavHost(navController = navController, startDestination = HibiScreen.MemoList().route) {
            composable(HibiScreen.MemoList().route) {
                val viewModel = hiltViewModel<MemoListViewModel>()
                MemoListScreen(
                    viewModel,
                    navToMemoCreate = { navController.navigate(HibiScreen.MemoCreate().route) },
                    navToMemoEdit = { id -> navController.navigate(HibiScreen.MemoEdit().fullRoute(id)) },
                    navToMemoSearch = { navController.navigate(HibiScreen.MemoSearch().route) },
                )
            }

            composable(HibiScreen.MemoCreate().route) {
                val viewModel = hiltViewModel<MemoEditViewModel>()
                viewModel.retrieveMemo(null)
                MemoEditScreen(
                    viewModel,
                    navToBack = { navController.popBackStack() },
                )
            }

            composable(
                HibiScreen.MemoEdit().route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val memoId = entry.arguments?.getString("id")
                val viewModel = hiltViewModel<MemoEditViewModel>()
                viewModel.retrieveMemo(memoId)

                MemoEditScreen(
                    viewModel,
                    navToBack = { navController.popBackStack() },
                )
            }

            composable(HibiScreen.MemoSearch().route) {
                MemoSearchScreen()
            }
        }
    }
}