package ru.kyamshanov.mission.profile.dto

import ru.kyamshanov.mission.profile.model.UserInfo


typealias MappingRqDto = List<String>

data class MappingRsDto(
    val users: List<UserInfoDto>
)

internal fun Collection<UserInfo>.toMappingDto() = MappingRsDto(
    users = this.map { it.toDto() }
)