package ru.kyamshanov.mission.profile.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.kyamshanov.mission.profile.model.UserFace

@Document("documents")
internal data class ProfileFaceDocument(
    @Id
    val userId: String,
    val profile: ProfileInfo
) {
    data class ProfileInfo(
        val name: String?,
        val age: Int?
    )
}

internal fun ProfileFaceDocument.toDomain() = UserFace(
    id = userId,
    name = profile.name,
    age = profile.age
)