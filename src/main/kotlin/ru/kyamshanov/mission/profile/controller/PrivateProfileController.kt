package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.BackRegisterRqDto
import ru.kyamshanov.mission.profile.dto.FetchUserDtoRs
import ru.kyamshanov.mission.profile.dto.PrivateSetProfileDtoRq
import ru.kyamshanov.mission.profile.model.UserProfile
import ru.kyamshanov.mission.profile.processor.BackRegisterProcessor
import ru.kyamshanov.mission.profile.service.ProfileService

@RestController
@RequestMapping("/profile/private")
internal class PrivateProfileController @Autowired constructor(
    private val profileService: ProfileService,
    private val backRegisterProcessor: BackRegisterProcessor
) {

    @GetMapping("/fetch")
    suspend fun fetchUserById(
        @RequestHeader(value = USER_ID_HEADER_KEY, required = true) userId: String
    ): ResponseEntity<FetchUserDtoRs> {
        val fetchedProfile = profileService.fetchProfileById(userId)
        val response = FetchUserDtoRs(
            userId = fetchedProfile.id,
            profile = fetchedProfile.data.value
        )
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/back_reg")
    suspend fun backgroundRegistration(
        @RequestHeader(value = USER_ID_HEADER_KEY, required = true) userId: String,
        @RequestBody(required = true) body: BackRegisterRqDto
    ): ResponseEntity<Unit> {
        backRegisterProcessor.registerUser(userId, body)
        return ResponseEntity(HttpStatus.OK)
    }

    private companion object {
        const val USER_ID_HEADER_KEY = "user-id"
    }

}