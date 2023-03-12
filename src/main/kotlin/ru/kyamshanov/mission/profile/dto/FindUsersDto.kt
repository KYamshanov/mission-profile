package ru.kyamshanov.mission.profile.dto

import ru.kyamshanov.mission.profile.model.FoundUser

data class FindUsersRqDto(
    val name: String,
    val age: Int?
)

data class FindUsersRsDto(
    val users: List<FoundUserInfo>
) {

    data class FoundUserInfo(
        val id: String,
        val name: String?,
        val age: Int?
    )
}

internal fun Collection<FoundUser>.toDto() = FindUsersRsDto(
    users = this.map { user ->
        FindUsersRsDto.FoundUserInfo(
            id = user.id,
            name = user.name,
            age = user.age
        )
    }
)