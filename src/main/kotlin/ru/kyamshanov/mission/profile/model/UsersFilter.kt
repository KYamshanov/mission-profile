package ru.kyamshanov.mission.profile.model

internal data class UsersFilter(
    val firstname: String?,
    val lastname: String?,
    val patronymic: String?,
    val group: String?
)