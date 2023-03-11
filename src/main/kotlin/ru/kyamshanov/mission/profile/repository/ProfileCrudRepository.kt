package ru.kyamshanov.mission.profile.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import ru.kyamshanov.mission.profile.persistence.ProfileDocument

internal interface ProfileCrudRepository : CoroutineCrudRepository<ProfileDocument, String> {

    suspend fun findFirstByUserId(userId: String): ProfileDocument?
}