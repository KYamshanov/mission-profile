package ru.kyamshanov.mission.profile.service

import kotlinx.coroutines.flow.first
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.persistence.ProfileDocument
import ru.kyamshanov.mission.profile.persistence.toUserProfile
import ru.kyamshanov.mission.profile.repository.ProfileCrudRepository
import ru.kyamshanov.mission.profile.repository.ProfileRepository
import java.util.UUID

/**
 * Сервис профилей
 */
internal interface ProfileService {

    suspend fun registerUser(login: String, data: UserProfile.Info): UserProfile

    suspend fun fetchProfileById(userId: String): UserProfile

    suspend fun setUserProfileInfo(userId: String, profileInfo: UserProfile.Info)
}

@Service
internal class ProfileServiceImpl @Autowired constructor(
    private val profileCrudRepository: ProfileCrudRepository,
    private val profileRepository: ProfileRepository
) : ProfileService {
    override suspend fun registerUser(login: String, data: UserProfile.Info): UserProfile =
        profileCrudRepository.save(
            ProfileDocument(
                userId = generateUserId(),
                profile = data.value
            )
        ).toUserProfile()

    override suspend fun fetchProfileById(userId: String): UserProfile =
        (profileCrudRepository.findFirstByUserId(userId) ?: run {
            val sketchUserDocument = ProfileDocument(
                userId = generateUserId(),
                profile = emptyMap()
            )
            profileCrudRepository.save(sketchUserDocument)
        }).let { profileDocument ->
            UserProfile(
                id = profileDocument.userId,
                data = UserProfile.Info(profileDocument.profile)
            )
        }

    override suspend fun setUserProfileInfo(userId: String, profileInfo: UserProfile.Info) {
        profileRepository.mergeProfile(
            userId = userId,
            login = null,
            data = profileInfo.value
        )
    }

    private fun generateUserId() = UUID.randomUUID().toString()
}