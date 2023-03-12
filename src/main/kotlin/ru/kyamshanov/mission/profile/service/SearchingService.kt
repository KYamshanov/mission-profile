package ru.kyamshanov.mission.profile.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.kyamshanov.mission.profile.model.FoundUser
import ru.kyamshanov.mission.profile.model.UsersFilter
import ru.kyamshanov.mission.profile.repository.ProfileRepository

internal interface SearchingService {

    suspend fun findUsers(filter: UsersFilter): Collection<FoundUser>
}

@Service
private class SearchingServiceImpl @Autowired constructor(
    private val profileRepository: ProfileRepository
) : SearchingService {
    override suspend fun findUsers(filter: UsersFilter): Collection<FoundUser> =
        profileRepository.searchByNameAndAge(filter.name, filter.age)
}