package ru.kyamshanov.mission.profile.model

internal data class UserInfo(
    val id: String,
    val firstname: String?,
    val lastname: String?,
    val patronymic: String?,
    val group: String?
)