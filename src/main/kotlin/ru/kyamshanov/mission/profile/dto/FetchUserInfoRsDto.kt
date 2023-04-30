package ru.kyamshanov.mission.profile.dto

data class FetchUserInfoRsDto(
    val userInfo: UserInfoDto,
    val requiredField: List<UserInfoFieldDto>
)