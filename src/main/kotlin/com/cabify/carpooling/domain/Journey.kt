package com.cabify.carpooling.domain

import com.fasterxml.jackson.annotation.JsonIgnore

data class Journey(var id: Long, var people: Int,
                   @JsonIgnore var car: Car?)
