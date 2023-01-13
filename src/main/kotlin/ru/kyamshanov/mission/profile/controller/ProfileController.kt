package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kyamshanov.mission.profile.dto.GetProfileDtoRq
import ru.kyamshanov.mission.profile.dto.GetProfileDtoRs
import ru.kyamshanov.mission.profile.dto.SetProfileDtoRq
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.service.ProfileService

/**
 * Контроллер ролей
 * @property roleService Сервис для управления ролями
 * @property userProcessor Обработчик пользователя
 */
@RestController
@RequestMapping("/profile")
internal class ProfileController @Autowired constructor(
    private val profileService: ProfileService
) {

    @GetMapping("get")
    suspend fun get(
        @RequestBody(required = false) body: GetProfileDtoRq
    ): ResponseEntity<GetProfileDtoRs> =
        try {
            val profile = profileService.getUserProfile(body.userId)
            val response = GetProfileDtoRs(profile.data)
            ResponseEntity(response, HttpStatus.OK)
        } catch (e: Throwable) {
            e.printStackTrace()
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @PostMapping("set")
    suspend fun set(
        @RequestBody(required = false) body: SetProfileDtoRq
    ): ResponseEntity<Unit> =
        try {
            profileService.setUserProfile(body.userId, UserProfile(body.data))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Throwable) {
            e.printStackTrace()
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

}