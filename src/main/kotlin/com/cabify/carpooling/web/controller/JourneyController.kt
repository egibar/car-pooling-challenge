package com.cabify.carpooling.web.controller

import com.cabify.carpooling.domain.Journey
import com.cabify.carpooling.service.JourneyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/journey"])
class JourneyController(val journeyService: JourneyService) : BaseController() {


    @GetMapping("/{id}")
    fun getJourneyById(@PathVariable id: Long): ResponseEntity<Journey> {
        val journey: Journey? = journeyService.findById(id)
        journey?.let {
            return ResponseEntity(it, HttpStatus.OK)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/findall")
    fun all(): List<Journey> {
        return journeyService.findAll()
    }

    @PostMapping("")
    fun save(@Valid @RequestBody journey: Journey): ResponseEntity<Void> {
        journeyService.save(journey)
        return ResponseEntity.ok().build()
    }

}
