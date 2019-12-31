package com.cabify.carpooling

import org.slf4j.LoggerFactory.getLogger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarpoolingApplication

private val log = getLogger(CarpoolingApplication::class.java)

fun main(args: Array<String>) {
    runApplication<CarpoolingApplication>(*args)
}


