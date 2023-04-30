package ru.kyamshanov.mission.profile.processor

import org.springframework.stereotype.Component
import ru.kyamshanov.mission.profile.dto.FetchUserInfoRsDto
import ru.kyamshanov.mission.profile.dto.UserInfoFieldDto
import ru.kyamshanov.mission.profile.dto.toDto
import ru.kyamshanov.mission.profile.model.UserInfo
import ru.kyamshanov.mission.profile.service.ProfileService

interface FetchUserInfoProcessor {

    suspend fun fetchUserInfo(userId: String): FetchUserInfoRsDto
}

@Component
private class FetchUserInfoProcessorImpl(
    private val profileService: ProfileService,
) : FetchUserInfoProcessor {
    override suspend fun fetchUserInfo(userId: String): FetchUserInfoRsDto {
        val fetchedProfile = profileService.fetchProfileById(userId)
        val userInfo = fetchedProfile.toDto()
        return FetchUserInfoRsDto(
            userInfo = userInfo,
            requiredField = getRequiredFields(fetchedProfile)
        )
    }

    private fun getRequiredFields(info: UserInfo): List<UserInfoFieldDto> = buildList {
        if (info.firstname == null) add(UserInfoFieldDto.FIRSTNAME)
        if (info.lastname == null) add(UserInfoFieldDto.LASTNAME)
        if (info.group == null) add(UserInfoFieldDto.GROUP)
        if (info.patronymic == null && info.firstname == null) add(UserInfoFieldDto.PATRONYMIC)
    }

}