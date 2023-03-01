package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.FetchUserDtoRs
import ru.kyamshanov.mission.profile.dto.PrivateSetProfileDtoRq
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.service.ProfileService

/**
 * Контроллер ролей
 * @property roleService Сервис для управления ролями
 * @property userProcessor Обработчик пользователя
 */
@RestController
@RequestMapping("/profile/private")
internal class PrivateProfileController @Autowired constructor(
    private val profileService: ProfileService
) {

    @GetMapping("get")
    suspend fun get(
        @RequestParam(required = true, value = "user_id") userId: String
    ): ResponseEntity<FetchUserDtoRs> =
        try {
            TODO("Переписать")
           // val profile =  //profileService.getUserProfile(userId)
           // val response = FetchUserDtoRs(profile.data)
            //ResponseEntity(response, HttpStatus.OK)
        } catch (e: Throwable) {
            e.printStackTrace()
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @PostMapping("set")
    suspend fun set(
        @RequestBody(required = true) body: PrivateSetProfileDtoRq
    ): ResponseEntity<Unit> =
        try {
            TODO("Переписать")//profileService.setUserProfile(body.userId, UserProfile(body.data))
            ResponseEntity(HttpStatus.OK)
        } catch (e: Throwable) {
            e.printStackTrace()
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

}