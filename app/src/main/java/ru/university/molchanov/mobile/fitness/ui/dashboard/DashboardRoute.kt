package ru.university.molchanov.mobile.fitness.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun DashboardRoute(modifier: Modifier = Modifier) {
    var state by rememberSaveable(stateSaver = DashboardUiStateSaver) {
        mutableStateOf(
            DashboardUiState(
                centerName = "Фитнес-центр «Старт»",
                activeVisits = 2,
                capacity = DEFAULT_CAPACITY,
                hasValidMembership = true,
                lastOperation = null,
            )
        )
    }

    DashboardScreen(
        state = state,
        onStartVisitClick = { state = startVisit(state) },
        onToggleMembershipClick = { state = toggleMembership(state) },
        modifier = modifier,
    )
}

val DashboardUiStateSaver = listSaver<DashboardUiState, Any>(
    save = {
        listOf(
            it.centerName,
            it.activeVisits,
            it.capacity,
            it.hasValidMembership,
            it.lastOperation?.name.orEmpty(),
        )
    },
    restore = { values ->
        DashboardUiState(
            centerName = values[0] as String,
            activeVisits = values[1] as Int,
            capacity = values[2] as Int,
            hasValidMembership = values[3] as Boolean,
            lastOperation = (values[4] as String)
                .takeIf(String::isNotEmpty)
                ?.let(LastOperation::valueOf),
        )
    },
)