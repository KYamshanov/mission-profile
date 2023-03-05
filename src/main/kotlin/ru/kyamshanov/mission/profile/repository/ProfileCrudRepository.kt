package ru.kyamshanov.mission.profile.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import ru.kyamshanov.mission.profile.persistence.ProfileDocument

internal interface ProfileCrudRepository : CoroutineCrudRepository<ProfileDocument, String> {

    fun findFirstByUserId(userId: String): Flow<ProfileDocument>

    suspend fun findFirstByLogin(login: String): ProfileDocument?
}