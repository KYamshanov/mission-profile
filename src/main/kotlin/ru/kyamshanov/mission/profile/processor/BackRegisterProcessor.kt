package ru.kyamshanov.mission.profile.processor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.kyamshanov.mission.profile.dto.BackRegisterRqDto
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.AGE
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.NAME
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.service.BackgroundRegistrationService

internal interface BackRegisterProcessor {

    suspend fun registerUser(userId: String, rq: BackRegisterRqDto)
}

@Component
private class BackRegisterProcessorImpl @Autowired constructor(
    private val backgroundRegistrationService: BackgroundRegistrationService,
) : BackRegisterProcessor {
    override suspend fun registerUser(userId: String, rq: BackRegisterRqDto) {
        val userProfile = rq.toUserProfile()
        backgroundRegistrationService.registerUser(userId, userProfile)
    }

    private fun BackRegisterRqDto.toUserProfile(): UserProfile.Info =
        buildMap<String, Any> {
            age?.let { put(AGE, it) }
            name?.let { put(NAME, it) }
        }.let { UserProfile.Info(it) }

}