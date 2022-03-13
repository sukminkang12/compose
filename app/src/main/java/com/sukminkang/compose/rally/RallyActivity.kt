/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sukminkang.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.rally.data.UserData
import com.sukminkang.compose.rally.ui.accounts.AccountsBody
import com.sukminkang.compose.rally.ui.accounts.SingleAccountBody
import com.sukminkang.compose.rally.ui.bills.BillsBody
import com.sukminkang.compose.rally.ui.components.RallyTabRow
import com.sukminkang.compose.rally.ui.overview.OverviewBody
import com.sukminkang.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */

val accountsName = RallyScreen.Accounts.name

class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        val allScreens = RallyScreen.values().toList()
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = RallyScreen.fromRoute(
            backstackEntry.value?.destination?.route
        )

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen -> navController.navigate(screen.name) },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(navController = navController,
            startDestination = RallyScreen.Overview.name,
            modifier = Modifier.padding(innerPadding)) {
                composable(RallyScreen.Overview.name) {
                    OverviewBody(
                        onClickSeeAllAccounts = {
                            navController.navigate(RallyScreen.Accounts.name)
                        },
                        onClickSeeAllBills = {
                            navController.navigate(RallyScreen.Bills.name)
                        },
                        onAccountClick = { name ->
                            navigateToSingleAccount(navController, name)
                        },
                    )
                }
                composable(RallyScreen.Accounts.name) {
                    AccountsBody(accounts = UserData.accounts) { name ->
                        navigateToSingleAccount(
                            navController = navController,
                            accountName = name
                        )
                    }
                }
                composable(RallyScreen.Bills.name) {
                    BillsBody(bills = UserData.bills)
                }
                composable(
                    "$accountsName/{name}",
                    arguments = listOf(
                        navArgument("name") {
                            // Make argument type safe
                            type = NavType.StringType
                        }
                    )
                ) { entry -> // Look up "name" in NavBackStackEntry's arguments
                    val accountName = entry.arguments?.getString("name")
                    // Find first name match in UserData
                    val account = UserData.getAccount(accountName)
                    // Pass account to SingleAccountBody
                    SingleAccountBody(account = account)
                }
            }
        }
    }
}

private fun navigateToSingleAccount(
    navController: NavHostController,
    accountName: String
) {
    navController.navigate("${RallyScreen.Accounts.name}/$accountName")
}