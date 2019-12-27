package com.cabify.carpooling

import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.service.CarService
import com.cabify.carpooling.web.controller.CarController
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@WebMvcTest(CarController::class)
@ExtendWith(MockKExtension::class)
class CarControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var carService: CarService

    @Test
    fun `car controller findById`() {
        val car: Car = Car(1, 6)
        every { carService.findById(1) } returns car

        mockMvc.perform(get("/cars/{1}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.seats").value(6))
    }
}