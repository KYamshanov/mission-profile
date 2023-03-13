package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.*
import ru.kyamshanov.mission.profile.dto.toDto
import ru.kyamshanov.mission.profile.model.UsersFilter
import ru.kyamshanov.mission.profile.service.SearchingService

@RestController
@RequestMapping("/profile/manager/search")
internal class SearchingManagerController @Autowired constructor(
    private val searchingService: SearchingService
) {

    @PostMapping("/find")
    suspend fun findUsers(
        @RequestHeader(value = USER_ID_HEADER_KEY, required = true) userId: String,
        @RequestBody(required = true) body: FindUsersRqDto
    ): ResponseEntity<FindUsersRsDto> {
        val foundUsers = searchingService.findUsers(UsersFilter(name = body.name, age = body.age))
        val response = foundUsers.toDto()
        return ResponseEntity(response,HttpStatus.OK)
    }

    private companion object {
        const val USER_ID_HEADER_KEY = "user-id"
    }
}