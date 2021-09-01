package com.kt.learn.services

import com.kt.learn.domain.PassengerRepository
import com.kt.learn.domain.TravelRequest
import com.kt.learn.domain.TravelRequestRepository
import com.kt.learn.interfaces.PassengerAPI
import com.kt.learn.interfaces.TravelRequestInput
import com.kt.learn.interfaces.TravelRequestOutput
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class TravelService(private val travelRequestRepository: TravelRequestRepository) {
    fun saveTravelRequest(travelRequest: TravelRequest) =
        travelRequestRepository.save(travelRequest)
}

@Component
class TravelRequestMapper(
    val passengerRepository: PassengerRepository
) {

    fun map(input: TravelRequestInput): TravelRequest {
        val passenger = passengerRepository.findById(input.passengerId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        return TravelRequest(
            passenger = passenger,
            origin = input.origin,
            destination = input.destination
        )
    }

    fun map(travelRequest: TravelRequest) : TravelRequestOutput {
        return TravelRequestOutput(
            id = travelRequest.id!!,
            origin = travelRequest.origin,
            destination = travelRequest.destination,
            status = travelRequest.status,
            creationDate = travelRequest.creationDate
        ) }

    fun buildOutputModel(travelRequest: TravelRequest, output: TravelRequestOutput):
            EntityModel<TravelRequestOutput> {
        val passengerLink = WebMvcLinkBuilder
            .linkTo(PassengerAPI::class)
            .withRel("passenger")
            .withTitle(travelRequest.passenger.name)
        return EntityModel.of(output, passengerLink)
    }
}

enum class TravelRequestStatus {
    CREATED, ACCEPTED, REFUSED
}