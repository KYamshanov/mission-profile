package ru.kyamshanov.mission.profile.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import ru.kyamshanov.mission.profile.model.UserProfile

@Document("documents")
internal data class ProfileDocument(
    @Id
    val userId: String,
    val profile: Map<String, Any>
)

internal fun ProfileDocument.toUserProfile() = UserProfile(
    id = userId,
    data = UserProfile.Info(profile)
)