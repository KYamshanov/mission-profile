package ru.kyamshanov.mission.profile.dto

data class PrivateSetProfileDtoRq(
    val userId: String,
    val data: Map<String, Any>
)