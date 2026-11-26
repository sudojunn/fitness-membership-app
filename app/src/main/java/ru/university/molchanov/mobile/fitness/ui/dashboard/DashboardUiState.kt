package ru.university.molchanov.mobile.fitness.ui.dashboard

data class DashboardUiState(
    val centerName: String,
    val activeVisits: Int,
    val capacity: Int,
    val hasValidMembership: Boolean,
    val lastOperation: LastOperation?,
) {
    init {
        require(centerName.isNotBlank())
        require(capacity > 0)
        require(activeVisits in 0..capacity)
    }

    val hasFreeSpot: Boolean
        get() = activeVisits < capacity

    val canStartVisit: Boolean
        get() = hasValidMembership && hasFreeSpot
}

enum class LastOperation {
    VISIT_STARTED,
    MEMBERSHIP_EXPIRED,
    MEMBERSHIP_RENEWED,
}

const val DEFAULT_CAPACITY = 5

fun startVisit(state: DashboardUiState): DashboardUiState {
    if (!state.canStartVisit) return state
    return state.copy(
        activeVisits = state.activeVisits + 1,
        lastOperation = LastOperation.VISIT_STARTED,
    )
}

fun toggleMembership(state: DashboardUiState): DashboardUiState =
    state.copy(
        hasValidMembership = !state.hasValidMembership,
        lastOperation = if (state.hasValidMembership) {
            LastOperation.MEMBERSHIP_EXPIRED
        } else {
            LastOperation.MEMBERSHIP_RENEWED
        },
    )