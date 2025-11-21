package com.watchmymoney.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.watchmymoney.data.SalaryRepository
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val repository = SalaryRepository(context)
    val userConfig by repository.userConfig.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    if (userConfig == null) {
        // Loading state, maybe show a spinner?
        return
    }

    val config = userConfig!!

    if (config.annualSalary <= 0) {
        OnboardingScreen(
            onSalarySet = { newSalary ->
                scope.launch {
                    repository.updateAnnualSalary(newSalary)
                }
            }
        )
    } else {
        TickerScreen(
            annualSalary = config.annualSalary,
            currencySymbol = config.currencySymbol,
            resetHour = config.resetHour,
            onEditClick = {
                scope.launch {
                    // Resetting salary to 0 will trigger the OnboardingScreen
                    repository.updateAnnualSalary(0.0)
                }
            }
        )
    }
}
