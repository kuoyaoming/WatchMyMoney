package com.watchmymoney.logic

import java.time.Duration
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Pure logic class for calculating salary earnings.
 */
object SalaryCalculator {

    private const val DAYS_PER_YEAR = 365.25

    data class Result(
        val earnedToday: Double,
        val progress: Float // 0.0 to 1.0
    )

    /**
     * Calculates the earned amount and progress for the current day.
     *
     * @param annualSalary The user's annual salary.
     * @param currentTimeMs Current timestamp in milliseconds.
     * @param resetHour The hour of the day when the counter resets (0-23). Default is 0 (midnight).
     */
    fun calculate(annualSalary: Double, currentTimeMs: Long, resetHour: Int = 0): Result {
        if (annualSalary <= 0.0) {
            return Result(0.0, 0f)
        }

        val dailySalary = annualSalary / DAYS_PER_YEAR

        val now = Instant.ofEpochMilli(currentTimeMs)
        val zoneId = ZoneId.systemDefault()
        val currentZonedTime = now.atZone(zoneId)

        // The time of day when the "salary day" resets
        val resetTime = LocalTime.of(resetHour, 0)

        // Determine the start of the current "salary day"
        val todayReset = currentZonedTime.with(resetTime)
        
        // If currently it is before the reset time, the "day" started yesterday.
        // Note: ZonedDateTime handles DST correctly when subtracting days.
        val startOfSalaryDay = if (currentZonedTime.isBefore(todayReset)) {
            todayReset.minusDays(1)
        } else {
            todayReset
        }

        // The end of the current "salary day" is the reset time of the next day.
        val endOfSalaryDay = startOfSalaryDay.plusDays(1)

        // Calculate total duration of this "salary day" (usually 24h, but can be 23 or 25 due to DST)
        val dayDuration = Duration.between(startOfSalaryDay, endOfSalaryDay).toMillis()

        // Calculate elapsed duration since start of the "salary day"
        val elapsedDuration = Duration.between(startOfSalaryDay, currentZonedTime).toMillis()

        // Safety check to avoid division by zero (should happen extremely rarely if ever)
        if (dayDuration <= 0) {
            return Result(0.0, 0f)
        }

        // Fraction of the day passed
        val fractionOfDay = elapsedDuration.toDouble() / dayDuration.toDouble()
        
        // Clamp fraction to 0.0 - 1.0 to handle any weird edge cases
        val safeFraction = fractionOfDay.coerceIn(0.0, 1.0)

        val earned = dailySalary * safeFraction

        return Result(
            earnedToday = earned,
            progress = safeFraction.toFloat()
        )
    }
}
