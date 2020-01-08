package com.cabify.carpooling.web.integration

import com.cabify.carpooling.data.entities.JourneyEntity
import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.domain.Journey
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.util.AssertionErrors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import javax.transaction.Transactional

@SpringBootTest
@AutoConfigureMockMvc
class JourneyControllerTest(@Autowired var mockMvc: MockMvc, @Autowired var mapper: ObjectMapper) {

    @Transactional
    @Test
    fun `Journey Controller - save journey`() {
        val journey = JourneyEntity(1, 4)
        val journeyAsJson = asJsonString(mapper, journey)
        mockMvc.perform(MockMvcRequestBuilders.post("/journey")
                .content(journeyAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)


        mockMvc.perform(MockMvcRequestBuilders.get("/journey/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.people").value(4))
    }

    @Transactional
    @Test
    fun `Journey Controller - save same journey returns 409 conflict`() {
        val journey = JourneyEntity(1, 4)
        val journeyAsJson = asJsonString(mapper, journey)
        mockMvc.perform(MockMvcRequestBuilders.post("/journey")
                .content(journeyAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.post("/journey")
                .content(journeyAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(409))

    }

    /**
     * Given 1 car and 2 journeys that get linked to that car.
     * When removing 1 of those journeys
     * The car is still there
     */
    @Transactional
    @Test
    fun `journey repository - cars should not be affected by removing a journey`() {

        val car = Car(1, 4)
        val carList: List<Car> = listOf(car)
        val carListAsJson = asJsonString(mapper, carList)
        val journey1AsJson = asJsonString(mapper, Journey(1, 1))
        val journey2AsJson = asJsonString(mapper, Journey(2, 2))
        val carAsJson = asJsonString(mapper, car)


        mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                .content(carListAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)


        mockMvc.perform(MockMvcRequestBuilders.post("/journey")
                .content(journey1AsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.post("/journey")
                .content(journey2AsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.get("/journey/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.people").value(1))

        mockMvc.perform(MockMvcRequestBuilders.post("/dropoff")
                .param("ID", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.seats").value(4))

    }
}