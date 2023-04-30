package ru.kyamshanov.mission.profile.processor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.kyamshanov.mission.profile.dto.BackRegisterRqDto
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.FIRSTNAME
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.GROUP
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.LASTNAME
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys.PATRONYMIC
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
            firstname?.trim()?.takeIf { it.isNotBlank() }?.let { put(FIRSTNAME, it) }
            lastname?.trim()?.takeIf { it.isNotBlank() }?.let { put(LASTNAME, it) }
            patronymic?.trim()?.takeIf { it.isNotBlank() }?.let { put(PATRONYMIC, it) }
            group?.trim()?.takeIf { it.isNotBlank() }?.let { put(GROUP, it) }
        }.let { UserProfile.Info(it) }

}