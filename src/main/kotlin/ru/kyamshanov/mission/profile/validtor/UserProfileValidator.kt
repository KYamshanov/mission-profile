package ru.kyamshanov.mission.profile.validtor

import ru.kyamshanov.mission.profile.exceptions.ValidateException
import ru.kyamshanov.mission.profile.model.UserProfile
import kotlin.jvm.Throws

internal interface UserProfileValidator {

    @Throws(ValidateException::class)
    fun validate(profile: UserProfile.Info)
}