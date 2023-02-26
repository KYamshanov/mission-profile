package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.FetchUserDtoRs
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.service.ProfileService

/**
 * Контроллер профиля пользователя
 * @property profileService Сервис для управления профилем
 */
@RestController
@RequestMapping("/profile")
internal class ProfileController @Autowired constructor(
    private val profileService: ProfileService
) {

    @GetMapping("get")
    suspend fun get(
        @RequestHeader(required = true, value = USER_ID_HEADER_KEY) userId: String
    ): ResponseEntity<FetchUserDtoRs> = TODO("Переписать")
       /* try {
            val profile = profileService.getUserProfile(userId)
            val response = FetchUserDtoRs(profile.data)
            ResponseEntity(response, HttpStatus.OK)
        } catch (e: Throwable) {
            e.printStackTrace()
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }*/

    @PostMapping("set")
    suspend fun set(
        @RequestHeader(required = true, value = USER_ID_HEADER_KEY) userId: String,
        @RequestBody(required = true) body: Map<String, Any>
    ): ResponseEntity<Unit> =TODO("Переписать")
     /*   try {
            profileService.setUserProfile(userId, UserProfile(body))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Throwable) {
            e.printStackTrace()
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }*/

    private companion object {
        const val USER_ID_HEADER_KEY = "user-id"
    }
}