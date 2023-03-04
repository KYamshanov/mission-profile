package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.AddInfoRqDto
import ru.kyamshanov.mission.profile.dto.FetchUserDtoRs
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.service.ProfileService

/**
 * Контроллер профиля пользователя для авторизованных юзеров
 * @property profileService Сервис для управления профилем
 */
@RestController
@RequestMapping("/manager/profile")
internal class ManagerController @Autowired constructor(
    private val profileService: ProfileService
) {

    @PostMapping("/info/add")
    suspend fun fetchUserById(
        @RequestBody(required = true) body: AddInfoRqDto
    ): ResponseEntity<Unit> {
        profileService.setUserProfileInfo(body.userId, UserProfile.Info(body.info))
        return ResponseEntity(HttpStatus.OK)
    }
}