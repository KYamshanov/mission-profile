package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.*
import ru.kyamshanov.mission.profile.dto.toDto
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.model.UsersFilter
import ru.kyamshanov.mission.profile.service.ProfileService
import ru.kyamshanov.mission.profile.service.SearchingService

/**
 * Контроллер профиля пользователя для авторизованных юзеров
 * @property profileService Сервис для управления профилем
 */
@RestController
@RequestMapping("/profile/manager/")
internal class ManagerController @Autowired constructor(
    private val profileService: ProfileService,
    private val searchingService: SearchingService
) {

    @PostMapping("/info/add")
    suspend fun addUserInfo(
        @RequestBody(required = true) body: AddInfoRqDto
    ): ResponseEntity<Unit> {
        profileService.setUserProfileInfo(body.userId, UserProfile.Info(body.info))
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/find")
    suspend fun findUsers(
        @RequestHeader(value = USER_ID_HEADER_KEY, required = true) userId: String,
        @RequestBody(required = true) body: FindUsersRqDto
    ): ResponseEntity<FindUsersRsDto> {
        val foundUsers = searchingService.findUsers(body.toFilter())
        val response = foundUsers.toDto()
        return ResponseEntity(response, HttpStatus.OK)
    }

    private companion object {
        const val USER_ID_HEADER_KEY = "user-id"
    }
}