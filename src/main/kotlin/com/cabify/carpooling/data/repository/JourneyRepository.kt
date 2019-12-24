package com.cabify.carpooling.data.repository

import com.cabify.carpooling.data.entities.JourneyEntity
import org.springframework.data.repository.CrudRepository

interface JourneyRepository : CrudRepository<JourneyEntity, Long> {

}