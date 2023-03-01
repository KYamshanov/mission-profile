package ru.kyamshanov.mission.profile.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import ru.kyamshanov.mission.profile.model.UserProfile

@Document("documents")
internal data class ProfileDocument(

    @Id
    val userId: String,
    @Indexed(unique = true)
    val login: String,
    val profile: Map<String, Any>
)

internal fun ProfileDocument.toUserProfile() = UserProfile(
    id = userId,
    login = login,
    data = UserProfile.Info(profile)
)