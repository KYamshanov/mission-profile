package ru.kyamshanov.mission.profile.dto

data class AddInfoRqDto(
    val userId: String,
    val info: Map<String, Any>
)