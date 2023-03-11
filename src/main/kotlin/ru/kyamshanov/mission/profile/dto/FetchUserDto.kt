package ru.kyamshanov.mission.profile.dto

data class FetchUserDtoRs(
    val userId: String,
    val profile: Map<String, Any>
)