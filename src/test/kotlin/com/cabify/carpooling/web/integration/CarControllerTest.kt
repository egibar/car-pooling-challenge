package com.cabify.carpooling.web.integration

import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.domain.Journey
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerIntegrationTest(@Autowired var mockMvc: MockMvc, @Autowired var mapper: ObjectMapper) {

    @DirtiesContext
    @Test
    fun `carController - save and find list of cars`() {
        val carList: List<Car> = listOf(Car(1, 4), Car(2, 5), Car(3, 6))
        val carListAsJson = asJsonString(mapper, carList)
        mockMvc.perform(put("/cars")
                .content(carListAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)


        mockMvc.perform(get("/cars/findall")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.[0].id").value(1))
                .andExpect(jsonPath("\$.[0].seats").value(4))
                .andExpect(jsonPath("\$.[1].id").value(2))
                .andExpect(jsonPath("\$.[1].seats").value(5))
    }

    @DirtiesContext
    @Test
    fun `carController - reset database`() {
        val carList: List<Car> = listOf(Car(1, 4), Car(2, 5), Car(3, 6))
        val carListAsJson = asJsonString(mapper, carList)
        mockMvc.perform(put("/cars")
                .content(carListAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        val journeyAsJson = asJsonString(mapper, Journey(1, 4))
        mockMvc.perform(post("/journey")
                .content(journeyAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        // save cars and journey again to verify db is empty
        mockMvc.perform(put("/cars")
                .content(carListAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        mockMvc.perform(post("/journey")
                .content(journeyAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

}


fun asJsonString(mapper: ObjectMapper, obj: Any?): String {
    return try {
        mapper.writeValueAsString(obj)
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}