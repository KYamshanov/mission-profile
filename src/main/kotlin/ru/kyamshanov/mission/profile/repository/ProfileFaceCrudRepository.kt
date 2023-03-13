package ru.kyamshanov.mission.profile.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.kyamshanov.mission.profile.persistence.ProfileDocument
import ru.kyamshanov.mission.profile.persistence.ProfileFaceDocument

@Repository
internal interface ProfileFaceCrudRepository : CoroutineCrudRepository<ProfileFaceDocument, String>