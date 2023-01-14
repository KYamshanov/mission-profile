package ru.kyamshanov.mission.profile.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("documents")
internal data class ProfileDocument(
    @Id
    val userId: String,
    val profile: Map<String, Any>
)