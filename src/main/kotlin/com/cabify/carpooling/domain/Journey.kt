package com.cabify.carpooling.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class Journey(
        @get:NotNull
        var id: Long,
        @get:NotNull
        @get:Min(1, message = "people should not be lower than 1")
        @get:Max(6, message = "people should be not more than 6")
        var people: Int,
        @JsonIgnore var car: Car? = null
)
