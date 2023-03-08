package ru.kyamshanov.mission.profile.processor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.kyamshanov.mission.profile.dto.BackRegisterRqDto
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.AGE
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.NAME
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.service.BackgroundRegistrationService

internal interface BackRegisterProcessor {

    suspend fun registerUser(rq: BackRegisterRqDto)
}

@Component
private class BackRegisterProcessorImpl @Autowired constructor(
    private val backgroundRegistrationService: BackgroundRegistrationService,
) : BackRegisterProcessor {
    override suspend fun registerUser(rq: BackRegisterRqDto) {
        val userProfile = rq.info.toUserProfile()
        backgroundRegistrationService.registerUser(rq.userId, userProfile)
    }

    private fun BackRegisterRqDto.Info.toUserProfile(): UserProfile.Info =
        buildMap<String, Any> {
            age?.let { put(AGE, it) }
            name?.let { put(NAME, it) }
        }.let { UserProfile.Info(it) }

}