package ru.kyamshanov.mission.profile.dto

import ru.kyamshanov.mission.profile.model.UserFace


typealias MappingRqDto = List<String>

data class MappingRsDto(
    val users: List<UserInfo>
) {

    data class UserInfo(
        val id: String,
        val name: String?,
        val age: Int?
    )
}

internal fun Collection<UserFace>.toMappingDto() = MappingRsDto(
    users = this.map { user ->
        MappingRsDto.UserInfo(
            id = user.id,
            name = user.name,
            age = user.age
        )
    }
)