package ru.kyamshanov.mission.profile.dto

data class SetProfileDtoRq(
    val userId: String,
    val data: Map<String, Any>
)