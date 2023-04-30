package ru.kyamshanov.mission.profile.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys
import ru.kyamshanov.mission.profile.model.UserInfo
import ru.kyamshanov.mission.profile.model.UserProfile

@Document("documents")
internal data class ProfileDocument(
    @Id
    val userId: String,
    val profile: Map<String, Any?>?
)

internal fun ProfileDocument.toUserInfo() = UserInfo(
    id = userId,
    firstname = profile?.get(ProfileInfoKeys.FIRSTNAME) as? String,
    lastname = profile?.get(ProfileInfoKeys.LASTNAME) as? String,
    patronymic = profile?.get(ProfileInfoKeys.PATRONYMIC) as? String,
    group = profile?.get(ProfileInfoKeys.GROUP) as? String
)