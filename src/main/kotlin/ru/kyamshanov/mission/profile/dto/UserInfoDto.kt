package ru.kyamshanov.mission.profile.dto

import ru.kyamshanov.mission.profile.model.UserInfo

data class UserInfoDto(
    val id: String,
    val firstname: String?,
    val lastname: String?,
    val patronymic: String?,
    val group: String?,
)

internal fun UserInfo.toDto() = UserInfoDto(
    id = id,
    firstname = firstname,
    lastname = lastname,
    patronymic = patronymic,
    group = group
)