package com.kt.learn.services

import com.kt.learn.domain.TravelRequest
import com.kt.learn.domain.TravelRequestRepository
import org.springframework.stereotype.Component


@Component
class TravelService(
    val travelRequestRepository: TravelRequestRepository
) {

    fun saveTravelRequest(travelRequest: TravelRequest) =
        travelRequestRepository.save(travelRequest)
}