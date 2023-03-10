package ru.kyamshanov.mission.profile.repository

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import ru.kyamshanov.mission.profile.model.UserFace
import ru.kyamshanov.mission.profile.persistence.ProfileDocument
import ru.kyamshanov.mission.profile.persistence.ProfileFaceDocument
import ru.kyamshanov.mission.profile.persistence.toDomain

internal interface ProfileRepository {

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    suspend fun mergeProfile(userId: String, data: Map<String, Any?>? = null)

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    suspend fun searchByNameAndAge(name: String, age: Int?): List<UserFace>
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

    override suspend fun searchByNameAndAge(name: String, age: Int?): List<UserFace> =
        mongoOperations.find(
            query(
                where("profile.name").regex("(?i)$name")
                    .run { if (age != null) and("profile.age").`is`(age) else this }
            ),
            ProfileFaceDocument::class.java
        ).asFlow().map { it.toDomain() }.toCollection(mutableListOf())
}