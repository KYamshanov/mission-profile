package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.RegisterRsDto
import ru.kyamshanov.mission.profile.dto.SetProfileDtoRq
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.service.ProfileService

/**
 * Контроллер профиля пользователя
 * @property profileService Сервис для управления профилем
 */
@RestController
@RequestMapping("/internal_profile")
internal class InternalController @Autowired constructor(
    private val profileService: ProfileService
) {

    @GetMapping("/identify")
    suspend fun fetchUserByLogin(
        @RequestParam(required = true, name = "login") login: String
    ): ResponseEntity<RegisterRsDto> {
        val savedEntity = profileService.fetchProfileByLogin(login)
        val response = RegisterRsDto(userId = savedEntity.id)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/insert")
    suspend fun insertUserInfo(//SetProfileDtoRq
        @RequestBody(required = true) body: SetProfileDtoRq
    ): ResponseEntity<Unit> {
        profileService.setUserProfileInfo(userId = body.userId, profileInfo = UserProfile.Info(body.data))
        return ResponseEntity(HttpStatus.OK)
    }
}