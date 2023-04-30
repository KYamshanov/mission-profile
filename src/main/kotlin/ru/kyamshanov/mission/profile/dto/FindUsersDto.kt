package ru.kyamshanov.mission.profile.dto

import ru.kyamshanov.mission.profile.model.UserInfo
import ru.kyamshanov.mission.profile.model.UsersFilter

data class FindUsersRqDto(
    val firstname: String?,
    val lastname: String?,
    val patronymic: String?,
    val group: String?
)

data class FindUsersRsDto(
    val users: List<UserInfoDto>
)

internal fun Collection<UserInfo>.toDto() = FindUsersRsDto(
    users = this.map { it.toDto() }
)

internal fun FindUsersRqDto.toFilter() = UsersFilter(
    firstname = firstname,
    lastname = lastname,
    patronymic = patronymic,
    group = group
)