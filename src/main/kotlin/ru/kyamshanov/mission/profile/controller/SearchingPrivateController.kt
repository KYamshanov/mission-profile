package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.*
import ru.kyamshanov.mission.profile.service.SearchingService

@RestController
@RequestMapping("/profile/private/search")
internal class SearchingPrivateController @Autowired constructor(
    private val searchingService: SearchingService
) {

    @PostMapping("/map")
    suspend fun mappingUsers(
        @RequestBody(required = true) body: MappingRqDto,
        @RequestHeader(value = USER_ID_HEADER_KEY, required = true) userId: String,
    ): ResponseEntity<MappingRsDto> {
        val userFaces = searchingService.mappingUsers(body)
        val response = userFaces.toMappingDto()
        return ResponseEntity(response, HttpStatus.OK)
    }

    private companion object {
        const val USER_ID_HEADER_KEY = "user-id"
    }
}