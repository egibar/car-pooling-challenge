package com.cabify.carpooling.web.unit

import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.service.CarService
import com.cabify.carpooling.service.JourneyService
import com.cabify.carpooling.service.ResetService
import com.cabify.carpooling.web.controller.CarController
import com.cabify.carpooling.web.integration.asJsonString
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@WebMvcTest(controllers = [CarController::class])
@ExtendWith(MockKExtension::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CarControllerTest(@Autowired val mockMvc: MockMvc, @Autowired var mapper: ObjectMapper) {

    @MockkBean
    private lateinit var carService: CarService
    @MockkBean
    private lateinit var journeyService: JourneyService
    @MockkBean
    private lateinit var resetService: ResetService

    @Test
    fun `car controller findById`() {
        val car: Car = Car(1, 6)
        every { carService.findById(1) } returns car

        mockMvc.perform(get("/cars/{1}", 1)
                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.seats").value(6))
    }

    @Test
    fun `car controller put cars`() {
        val carList: List<Car> = listOf(Car(1, 4), Car(2, 5), Car(3, 6))
        val carListAsJson = asJsonString(mapper, carList)
        every { carService.saveAll(carList) } returns Unit
        every { resetService.resetDb() } returns Unit

        mockMvc.perform(put("/cars/")
                .content(carListAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }


    @Test
    fun `car controller put invalid cars`() {
        val carList: List<Car> = listOf(Car(3, 7)) // invalid number of seats
        val carListAsJson = asJsonString(mapper, carList)
        every { carService.saveAll(carList) } returns Unit
        every { resetService.resetDb() } returns Unit

        mockMvc.perform(put("/cars/")
                .content(carListAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

}