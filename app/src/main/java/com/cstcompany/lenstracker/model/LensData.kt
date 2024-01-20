package com.cstcompany.lenstracker.model

import java.time.LocalDate

data class LensData(
    val latestChange: LocalDate = LocalDate.now(),
    val side: EyeSide,
    var daysLeft: Int = 30, // how many days can you wear the lens
) {
    fun changeLens(): LensData {
        return copy(latestChange = LocalDate.now())
    }
}