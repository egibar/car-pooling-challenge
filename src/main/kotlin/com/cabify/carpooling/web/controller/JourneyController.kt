package com.cabify.carpooling.web.controller

import com.cabify.carpooling.domain.Journey
import com.cabify.carpooling.service.JourneyService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping(value = [""])
class JourneyController(val journeyService: JourneyService) : BaseController() {


    @GetMapping("/journey/{id}")
    fun getJourneyById(@PathVariable id: Long): ResponseEntity<Journey> {
        val journey: Journey? = journeyService.findById(id)
        journey?.let {
            return ResponseEntity(it, HttpStatus.OK)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/journey/findall")
    fun all(): List<Journey> {
        return journeyService.findAll()
    }

    @PostMapping("/journey")
    fun save(@Valid @RequestBody journey: Journey): ResponseEntity<Void> {
        journeyService.save(journey)
        return ResponseEntity.ok().build()
    }

    @PostMapping(value = ["/dropoff"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun dropoff(@RequestParam("ID") id: Long?): ResponseEntity<Void> {
        id?.let {
            return if (journeyService.deleteById(id)) {
                ResponseEntity.ok().build()
            } else {
                ResponseEntity.notFound().build() // no journey with that id found
            }
        } ?: return ResponseEntity.badRequest().build() // no id sent in the request
    }
}
