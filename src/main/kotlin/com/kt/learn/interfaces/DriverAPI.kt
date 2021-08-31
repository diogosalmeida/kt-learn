package com.kt.learn.interfaces

import com.kt.learn.domain.Driver
import com.kt.learn.domain.DriverRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Service
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class DriverAPI(val driverRepository: DriverRepository) {

    @GetMapping("/drivers")
    fun listDrivers(): List<Driver> = driverRepository.findAll()


    @GetMapping("/drivers/{id}")
    fun findDriver(@PathVariable("id") id: Long): Driver = driverRepository.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND)
    }


    @PostMapping("/drivers")
    fun createDriver(@RequestBody driver: Driver) = driverRepository.save(driver)
}