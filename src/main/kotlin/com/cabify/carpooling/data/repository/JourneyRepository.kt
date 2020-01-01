package com.cabify.carpooling.data.repository

import com.cabify.carpooling.data.entities.JourneyEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface JourneyRepository : CrudRepository<JourneyEntity, Long> {

    @Query(value = """
            SELECT j.*
            FROM journey j LEFT OUTER JOIN car c ON j.car_id = c.id 
            WHERE c.id is null
            ORDER BY create_date
            LIMIT 1
            """, nativeQuery = true)
    fun findFirstUnassigned(): JourneyEntity?
}