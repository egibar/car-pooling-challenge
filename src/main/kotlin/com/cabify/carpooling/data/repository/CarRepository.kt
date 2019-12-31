package com.cabify.carpooling.data.repository

import com.cabify.carpooling.data.entities.CarEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CarRepository : CrudRepository<CarEntity, Long> {

    @Query(value = """
            SELECT * from car where car.id in (
                select a.id from (
                    SELECT c.id, c.seats - sum(j.people) available
                    FROM car c LEFT JOIN journey j ON c.id = j.car_id
                    GROUP BY c.id
                    HAVING available is null OR available  >= ?1
                    LIMIT 1
                    ) a
                )
            """, nativeQuery = true)
    fun findFirstAvailable(requiredSeats: Int): CarEntity?
}