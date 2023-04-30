package ru.kyamshanov.mission.profile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.FIRSTNAME
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.GROUP
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.LASTNAME
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.PATRONYMIC
import ru.kyamshanov.mission.profile.model.UserInfo
import ru.kyamshanov.mission.profile.persistence.ProfileDocument
import ru.kyamshanov.mission.profile.persistence.ProfileFaceDocument
import ru.kyamshanov.mission.profile.persistence.toDomain

internal interface ProfileRepository {

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    suspend fun mergeProfile(userId: String, data: Map<String, Any?>? = null)

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    fun search(firstname: String, lastname: String, patronymic: String, group: String?): Flow<ProfileFaceDocument>
}

@Repository
private class ProfileRepositoryImpl @Autowired constructor(
    private val mongoOperations: ReactiveMongoOperations
) : ProfileRepository {
    override suspend fun mergeProfile(userId: String, data: Map<String, Any?>?) {
        if (data == null) throw IllegalArgumentException()
        mongoOperations.updateFirst(
            query(where("userId").`is`(userId)),
            Update().apply {
                data.forEach { (key, value) ->
                    if (value == null) unset("profile.$key")
                    else set("profile.$key", value)
                }
            },
            ProfileDocument::class.java
        ).awaitSingle().also { result ->
            if (result.wasAcknowledged().not()) throw IllegalStateException("Update is not acknowledged")
        }
    }

    override fun search(
        firstname: String,
        lastname: String,
        patronymic: String,
        group: String?
    ): Flow<ProfileFaceDocument> =
        mongoOperations.find(
            query(
                Criteria().orOperator(
                    where("profile.$FIRSTNAME").regex("(?i)$firstname"),
                    where("profile.$LASTNAME").regex("(?i)$lastname"),
                    where("profile.$PATRONYMIC").regex("(?i)$patronymic")
                ).run { if (group != null) and("profile.$GROUP").`is`(group) else this }
            ),
            ProfileFaceDocument::class.java
        ).asFlow()
}