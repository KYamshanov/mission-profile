package ru.kyamshanov.mission.profile.service

internal interface IdentifyingService {

    fun identify(externalUserId: String, accessId: String)
}