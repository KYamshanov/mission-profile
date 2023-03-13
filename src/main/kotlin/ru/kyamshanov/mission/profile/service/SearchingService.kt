package ru.kyamshanov.mission.profile.service

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.kyamshanov.mission.profile.model.UserFace
import ru.kyamshanov.mission.profile.model.UsersFilter
import ru.kyamshanov.mission.profile.persistence.toDomain
import ru.kyamshanov.mission.profile.repository.ProfileFaceCrudRepository
import ru.kyamshanov.mission.profile.repository.ProfileRepository

internal interface SearchingService {

    suspend fun findUsers(filter: UsersFilter): Collection<UserFace>

    suspend fun mappingUsers(userIds: List<String>): Collection<UserFace>
}

@Service
private class SearchingServiceImpl @Autowired constructor(
    private val profileRepository: ProfileRepository,
    private val profileFaceCrudRepository: ProfileFaceCrudRepository
) : SearchingService {
    override suspend fun findUsers(filter: UsersFilter): Collection<UserFace> =
        profileRepository.searchByNameAndAge(filter.name, filter.age)

    override suspend fun mappingUsers(userIds: List<String>): Collection<UserFace> =
        profileFaceCrudRepository.findAllById(userIds)
            .map { it.toDomain() }
            .toCollection(mutableListOf())
}