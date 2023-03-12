package ru.kyamshanov.mission.profile.repository

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import ru.kyamshanov.mission.profile.model.FoundUser
import ru.kyamshanov.mission.profile.persistence.ProfileDocument

internal interface ProfileRepository {

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    suspend fun mergeProfile(userId: String, data: Map<String, Any?>? = null)

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    suspend fun searchByNameAndAge(name: String, age: Int?): List<FoundUser>
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

    override suspend fun searchByNameAndAge(name: String, age: Int?): List<FoundUser> =
        mongoOperations.find(
            query(
                where("profile.name").regex("(?i)$name")
                    .run { if (age != null) and("profile.age").`is`(age) else this }
            ),
            FoundUserProfile::class.java
        ).asFlow().map { it.toFoundUser() }.toCollection(mutableListOf())

    @Document("documents")
    private data class FoundUserProfile(
        @Id
        val userId: String,
        val profile: ProfileInfo
    ) {
        data class ProfileInfo(
            val name: String?,
            val age: Int?
        )
    }

    private fun FoundUserProfile.toFoundUser() = FoundUser(
        id = userId,
        name = profile.name,
        age = profile.age
    )
}