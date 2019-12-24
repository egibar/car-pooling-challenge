package com.cabify.carpooling.web.controller

import com.cabify.carpooling.data.entities.JourneyEntity
import com.cabify.carpooling.data.entities.toJourney
import com.cabify.carpooling.data.entities.toJourneyEntity
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.data.repository.JourneyRepository
import com.cabify.carpooling.domain.Journey
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/journey"])
class JourneyController (val repo: JourneyRepository, val carRepo: CarRepository) {


    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<Journey> {
        val carEntity: JourneyEntity? = repo.findByIdOrNull(id)
        carEntity?.let {
            return ResponseEntity(it.toJourney(), HttpStatus.OK)
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findall")
    fun all(): List<Journey> {
        val journeyList = mutableListOf<Journey>()
        for (journeyEntity in repo.findAll()) {
            journeyList.add(journeyEntity.toJourney())
        }
        return journeyList
    }

    @PostMapping("")
    fun postJourney(@Valid @RequestBody journey: Journey): ResponseEntity<Void> {
        val carEntity = carRepo.findById(1).get()

        var journeyEntity = journey.toJourneyEntity()
        journeyEntity.car = carEntity
        journeyEntity = repo.save(journeyEntity)
        return ResponseEntity.ok().build();
    }

}
