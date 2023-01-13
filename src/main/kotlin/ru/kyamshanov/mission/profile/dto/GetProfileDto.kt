package ru.kyamshanov.mission.profile.dto

data class GetProfileDtoRq(
    val userId: String
)

data class GetProfileDtoRs(
    val info: Map<String, Any>
)