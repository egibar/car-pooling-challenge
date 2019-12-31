package com.cabify.carpooling.web.unit

import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.domain.Journey
import com.cabify.carpooling.service.JourneyService
import com.cabify.carpooling.web.controller.JourneyController
import com.cabify.carpooling.web.integration.asJsonString
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@WebMvcTest(JourneyController::class)
@ExtendWith(MockKExtension::class)
class JourneyControllerTest(@Autowired val mockMvc: MockMvc, @Autowired var mapper: ObjectMapper) {

    @MockkBean
    private lateinit var journeyService: JourneyService

    @Test
    fun `journey controller findById`() {
        val journey: Journey = Journey(1, 6)
        every { journeyService.findById(1) } returns journey

        mockMvc.perform(get("/journey/{1}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.people").value(6))
    }

    @Test
    fun `journey controller save journey`() {
        val journey: Journey = Journey(1, 6)
        val journeyAsJson = asJsonString(mapper, journey)
        every { journeyService.save(journey) } returns Unit

        mockMvc.perform(post("/journey")
                .content(journeyAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `journey controller dropoff returns ok`() {
        val journeyId = 1L
        every { journeyService.deleteById(journeyId) } returns true

        mockMvc.perform(post("/dropoff")
                .param("ID", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `journey controller dropoff returns not found`() {
        val journeyId = 1L
        every { journeyService.deleteById(journeyId) } returns false

        mockMvc.perform(post("/dropoff")
                .param("ID", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `journey controller dropoff returns bad request`() {
        mockMvc.perform(post("/dropoff")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `journey controller locate returns ok`() {
        val journeyId = 1L
        val journey = Journey(1, 3)
        val car = Car(23,4)
        journey.car = car
        every { journeyService.findById(journeyId) } returns journey

        mockMvc.perform(post("/locate")
                .param("ID", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(23))
                .andExpect(jsonPath("$.seats").value(4))
    }

    @Test
    fun `journey controller locate returns no content`() {
        val journeyId = 1L
        val journey = Journey(1, 3)
        every { journeyService.findById(journeyId) } returns journey

        mockMvc.perform(post("/locate")
                .param("ID", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `journey controller locate returns not found`() {
        val journeyId = 1L
        every { journeyService.findById(journeyId) } returns null

        mockMvc.perform(post("/locate")
                .param("ID", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `journey controller locate returns bad request`() {
        mockMvc.perform(post("/locate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

}