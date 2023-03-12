package ru.kyamshanov.mission.profile.validtor

import org.springframework.stereotype.Component
import ru.kyamshanov.mission.profile.exceptions.InvalidFieldException
import ru.kyamshanov.mission.profile.model.UserProfile

@Component
@BackgroundRegistrationValidator
internal class UserProfileValidatorImpl : UserProfileValidator {
    override fun validate(profile: UserProfile.Info) {
        profile.value.forEach { (key, _) ->
            if (ValidFields.contains(key).not()) throw InvalidFieldException("Profile`s field $key is invalid ")
        }
    }

    private companion object {


        val ValidFields = listOf("age", "name")
    }

}