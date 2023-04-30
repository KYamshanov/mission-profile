package ru.kyamshanov.mission.profile.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.kyamshanov.mission.profile.model.UserInfo
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.persistence.ProfileDocument
import ru.kyamshanov.mission.profile.persistence.toUserInfo
import ru.kyamshanov.mission.profile.repository.ProfileCrudRepository
import ru.kyamshanov.mission.profile.repository.ProfileRepository
import java.util.UUID

/**
 * Сервис профилей
 */
internal interface ProfileService {

    suspend fun fetchProfileById(userId: String): UserInfo

    suspend fun setUserProfileInfo(userId: String, profileInfo: UserProfile.Info)
}

@Service
internal class ProfileServiceImpl @Autowired constructor(
    private val profileCrudRepository: ProfileCrudRepository,
    private val profileRepository: ProfileRepository
) : ProfileService {

    override suspend fun fetchProfileById(userId: String): UserInfo =
        (profileCrudRepository.findFirstByUserId(userId)
            ?: run {
                val sketchUserDocument = ProfileDocument(
                    userId = userId,
                    profile = emptyMap()
                )
                profileCrudRepository.save(sketchUserDocument)
            })
            .toUserInfo()

    override suspend fun setUserProfileInfo(userId: String, profileInfo: UserProfile.Info) {
        profileRepository.mergeProfile(
            userId = userId,
            data = profileInfo.value
        )
    }

}