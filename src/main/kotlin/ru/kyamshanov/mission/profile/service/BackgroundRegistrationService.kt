package ru.kyamshanov.mission.profile.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.persistence.ProfileDocument
import ru.kyamshanov.mission.profile.persistence.toUserProfile
import ru.kyamshanov.mission.profile.repository.ProfileCrudRepository
import ru.kyamshanov.mission.profile.repository.ProfileRepository
import ru.kyamshanov.mission.profile.validtor.BackgroundRegistrationValidator
import ru.kyamshanov.mission.profile.validtor.UserProfileValidator

/**
 * Сервис профилей
 */
internal interface BackgroundRegistrationService {

    suspend fun registerUser(userId: String, data: UserProfile.Info)
}

@Service
internal class BackgroundRegistrationServiceImpl @Autowired constructor(
    private val profileRepository: ProfileRepository,
    @BackgroundRegistrationValidator private val userProfileValidator: UserProfileValidator
) : BackgroundRegistrationService {
    override suspend fun registerUser(userId: String, data: UserProfile.Info) {
        userProfileValidator.validate(data)
        profileRepository.mergeProfile(
            userId = userId,
            login = null,
            data = data.value
        )
    }
}