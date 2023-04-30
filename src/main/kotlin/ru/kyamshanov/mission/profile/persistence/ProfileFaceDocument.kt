package ru.kyamshanov.mission.profile.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.kyamshanov.mission.profile.model.UserInfo

@Document("documents")
internal data class ProfileFaceDocument(
    @Id
    val userId: String,
    val profile: ProfileInfo
) {
    data class ProfileInfo(
        val firstname: String?,
        val lastname: String?,
        val patronymic: String?,
        val group: String?
    )
}

internal fun ProfileFaceDocument.toDomain() = UserInfo(
    id = userId,
    firstname = profile.firstname,
    lastname = profile.lastname,
    patronymic = profile.patronymic,
    group = profile.group
)