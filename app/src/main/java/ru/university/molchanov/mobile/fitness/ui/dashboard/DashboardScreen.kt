package ru.university.molchanov.mobile.fitness.ui.dashboard

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.university.molchanov.mobile.fitness.R
import ru.university.molchanov.mobile.fitness.ui.theme.FitnessCenterTheme
import androidx.compose.material3.Surface
@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onStartVisitClick: () -> Unit,
    onToggleMembershipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.dashboard_title),
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(stringResource(R.string.center_value, state.centerName))
        Text(stringResource(R.string.active_visits, state.activeVisits, state.capacity))
        Text(
            if (state.hasValidMembership) {
                stringResource(R.string.membership_valid)
            } else {
                stringResource(R.string.membership_invalid)
            }
        )
        Text(lastOperationText(state.lastOperation))

        Button(
            onClick = onStartVisitClick,
            enabled = state.canStartVisit,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.start_visit))
        }

        if (!state.canStartVisit) {
            Text(
                text = if (!state.hasValidMembership) {
                    stringResource(R.string.error_membership_invalid)
                } else {
                    stringResource(R.string.error_capacity_reached)
                },
                color = MaterialTheme.colorScheme.error,
            )
        }

        TextButton(onClick = onToggleMembershipClick) {
            Text(
                if (state.hasValidMembership) {
                    stringResource(R.string.expire_membership)
                } else {
                    stringResource(R.string.renew_membership)
                }
            )
        }
    }
}

@Composable
private fun lastOperationText(operation: LastOperation?): String = when (operation) {
    null -> stringResource(R.string.last_operation_none)
    LastOperation.VISIT_STARTED -> stringResource(R.string.last_operation_visit_started)
    LastOperation.MEMBERSHIP_EXPIRED -> stringResource(R.string.last_operation_membership_expired)
    LastOperation.MEMBERSHIP_RENEWED -> stringResource(R.string.last_operation_membership_renewed)
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DashboardPreview() {
    FitnessCenterTheme {
        Surface {
            DashboardScreen(
                state = DashboardUiState("Фитнес-центр «Старт»", 2, DEFAULT_CAPACITY, true, null),
                onStartVisitClick = {},
                onToggleMembershipClick = {},
            )
        }
    }
}