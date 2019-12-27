package com.cabify.carpooling

import com.cabify.carpooling.domain.Car
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerIntegrationTest(@Autowired var mockMvc: MockMvc, @Autowired var mapper: ObjectMapper) {

    @Test
    fun `carController - save and find list of cars`() {
        val carList: List<Car> = listOf(Car(1, 4), Car(2, 5), Car(3, 6))
        val s = asJsonString(carList)
        mockMvc.perform(put("/cars")
                .content(s)
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
    }

    fun asJsonString(obj: Any?): String {
        return try {
            mapper.writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}