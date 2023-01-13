package ru.kyamshanov.mission.profile.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.persistence.ProfileDocument
import ru.kyamshanov.mission.profile.repository.ProfileCrudRepository

internal interface ProfileService {

    suspend fun getUserProfile(userId: String): UserProfile

    suspend fun setUserProfile(userId: String, profile: UserProfile)
}

@Service
private class ProfileServiceImpl @Autowired constructor(
    private val profileCrudRepository: ProfileCrudRepository
) : ProfileService {

    override suspend fun getUserProfile(userId: String): UserProfile =
        UserProfile(profileCrudRepository.findFirstByUserId(userId).profile)

    override suspend fun setUserProfile(userId: String, profile: UserProfile) {
        profileCrudRepository.save(ProfileDocument(userId, profile.data))
    }
}