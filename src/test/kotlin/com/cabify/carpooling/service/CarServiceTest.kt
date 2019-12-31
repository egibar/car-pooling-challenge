package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.CarEntity
import com.cabify.carpooling.data.entities.toCar
import com.cabify.carpooling.data.repository.CarRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.AssertionErrors
import java.util.*

@SpringBootTest
class CarServiceTest {

    @Autowired
    private lateinit var carService: CarService
    @MockkBean
    private lateinit var carRepository: CarRepository

    @Test
    fun `car service findById`() {
        val carEntity = CarEntity(1, 6)
        every { carRepository.findById(1) } returns Optional.of(carEntity)

        val car = carService.findById(1)

        AssertionErrors.assertEquals("asserting right id", carEntity.toCar(), car)
    }
}