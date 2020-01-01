package com.cabify.carpooling.domain

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class Car(
        @get:NotNull
        var id: Long,
        @get:NotNull
        @get:Min(4, message = "seats should not be lower than 4")
        @get:Max(6, message = "seats should be not more than 6")
        var seats: Int
)
