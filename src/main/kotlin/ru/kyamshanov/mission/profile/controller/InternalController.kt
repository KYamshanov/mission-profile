package ru.kyamshanov.mission.profile.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kyamshanov.mission.profile.dto.*
import ru.kyamshanov.mission.profile.service.SearchingService

/**
 * [!] Эдп поинты для внутренней сети
 */
@RestController
@RequestMapping("/profile/internal/")
internal class InternalController @Autowired constructor(
    private val searchingService: SearchingService
) {

        @PostMapping("/search/map")
    suspend fun mappingUsers(
        @RequestBody(required = true) body: MappingRqDto,
    ): ResponseEntity<MappingRsDto> {
        val userFaces = searchingService.mappingUsers(body)
        val response = userFaces.toMappingDto()
        return ResponseEntity(response, HttpStatus.OK)
    }
}