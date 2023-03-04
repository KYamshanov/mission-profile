package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.FetchUserDtoRs
import ru.kyamshanov.mission.profile.service.ProfileService

/**
 * Контроллер профиля пользователя для авторизованных юзеров
 * @property profileService Сервис для управления профилем
 */
@RestController
@RequestMapping("/private/profile")
internal class UserController @Autowired constructor(
    private val profileService: ProfileService
) {

    @GetMapping("/fetch")
    suspend fun fetchUserById(
        @RequestParam(value = "id", required = true) externalUserId: String
    ): ResponseEntity<FetchUserDtoRs> {
        val fetchedProfile = profileService.fetchProfileByLogin(externalUserId)
        val response = FetchUserDtoRs(
            userId = fetchedProfile.id,
            login = fetchedProfile.login,
            profile = fetchedProfile.data.value
        )
        return ResponseEntity(response, HttpStatus.OK)
    }
}