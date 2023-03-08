package ru.kyamshanov.mission.profile.dto

data class BackRegisterRqDto(
    val userId: String,
    val info: Info
) {
    data class Info(
        val name: String?,
        val age: String?
    )
}