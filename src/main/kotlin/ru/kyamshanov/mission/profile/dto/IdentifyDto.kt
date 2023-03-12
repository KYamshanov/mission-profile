package ru.kyamshanov.mission.profile.dto

data class IdentifyRqDto(
    val externalUserId: String,
    val accessId: String
)

data class IdentifyRSDto(
    val idToken: String
)