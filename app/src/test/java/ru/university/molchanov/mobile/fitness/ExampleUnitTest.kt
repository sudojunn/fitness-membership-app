package ru.university.molchanov.mobile.fitness.ui.dashboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class DashboardUiStateTest {
    private val initial = DashboardUiState(
        centerName = "Фитнес-центр «Старт»",
        activeVisits = 2,
        capacity = DEFAULT_CAPACITY,
        hasValidMembership = true,
        lastOperation = null,
    )

    @Test
    fun startVisit_incrementsCountAndRecordsOperation() {
        val actual = startVisit(initial)
        assertEquals(3, actual.activeVisits)
        assertEquals(LastOperation.VISIT_STARTED, actual.lastOperation)
    }

    @Test
    fun startVisit_atCapacityDoesNotChangeState() {
        val full = initial.copy(activeVisits = DEFAULT_CAPACITY)
        assertSame(full, startVisit(full))
    }

    @Test
    fun startVisit_withInvalidMembershipDoesNotChangeState() {
        val expired = initial.copy(hasValidMembership = false)
        assertSame(expired, startVisit(expired))
    }

    @Test
    fun toggleMembership_switchesValidityAndRecordsOperation() {
        val expired = toggleMembership(initial)
        assertFalse(expired.hasValidMembership)
        assertEquals(LastOperation.MEMBERSHIP_EXPIRED, expired.lastOperation)

        val renewed = toggleMembership(expired)
        assertTrue(renewed.hasValidMembership)
        assertEquals(LastOperation.MEMBERSHIP_RENEWED, renewed.lastOperation)
    }

    @Test
    fun negativeVisitsAreRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            initial.copy(activeVisits = -1)
        }
    }

    @Test
    fun visitsAboveCapacityAreRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            initial.copy(activeVisits = DEFAULT_CAPACITY + 1)
        }
    }
}