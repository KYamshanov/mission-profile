package ru.kyamshanov.mission.profile.repository

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import ru.kyamshanov.mission.profile.persistence.ProfileDocument

internal interface ProfileRepository {

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    suspend fun mergeProfile(userId: String, login: String? = null, data: Map<String, Any?>? = null)
}

@Repository
private class ProfileRepositoryImpl @Autowired constructor(
    private val mongoOperations: ReactiveMongoOperations
) : ProfileRepository {
    override suspend fun mergeProfile(userId: String, login: String?, data: Map<String, Any?>?) {
        if (login == null && data == null) throw IllegalArgumentException()
        mongoOperations.updateFirst(
            query(where("userId").`is`(userId)),
            Update().apply {
                if (login != null) set("login", login)
                data?.forEach { (key, value) ->
                    if (value == null) unset("profile.$key")
                    else set("profile.$key", value)
                }
            },
            ProfileDocument::class.java
        ).awaitSingle().also { result ->
            if (result.wasAcknowledged().not()) throw IllegalStateException("Update is not acknowledged")
        }
    }
}