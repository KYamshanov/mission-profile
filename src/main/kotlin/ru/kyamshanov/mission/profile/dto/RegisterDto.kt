package ru.kyamshanov.mission.profile.dto

data class RegisterRqDto(
    val login: String,
    val data: Map<String, Any>? = null
)

data class RegisterRsDto(
    val userId: String
)