package ru.kyamshanov.mission.profile.validtor

import org.springframework.stereotype.Component
import ru.kyamshanov.mission.profile.exceptions.InvalidFieldException
import ru.kyamshanov.mission.profile.model.ProfileInfoKeys
import ru.kyamshanov.mission.profile.model.UserProfile

@Component
@BackgroundRegistrationValidator
internal class UserProfileValidatorImpl : UserProfileValidator {
    override fun validate(profile: UserProfile.Info) {
        val profileMap = profile.value
        profileMap.forEach { (key, _) ->
            if (ValidFields.contains(key).not()) throw InvalidFieldException("Profile`s field $key is invalid ")
        }
        (profileMap[ProfileInfoKeys.FIRSTNAME] as? String)?.let { firstname ->
            validateName(firstname)
        }
        (profileMap[ProfileInfoKeys.LASTNAME] as? String)?.let { lastname ->
            validateName(lastname)
        }
        (profileMap[ProfileInfoKeys.PATRONYMIC] as? String)?.let { patronymic ->
            validateName(patronymic)
        }
        (profileMap[ProfileInfoKeys.GROUP] as? String)?.let { group ->
            validateGroup(group)
        }
    }

    private fun validateName(name: String) {
        if (name.isEmpty()) throw InvalidFieldException("name cannot be empty")
        if (name.length > 15 || name.length == 1) throw InvalidFieldException("name has invalid length")
        if (name.matches(AvailableNameSymbolsRegEx)) throw InvalidFieldException("name has unavailable symbols")
    }

    private fun validateGroup(group: String) {
        if (group.isEmpty()) throw InvalidFieldException("Group cannot be empty")
        if (group.length > 10 || group.length == 1) throw InvalidFieldException("Group has invalid length")
        if (group.matches(AvailableGroupSymbolsRegEx)) throw InvalidFieldException("Group has unavailable symbols")
    }

    private companion object {


        val ValidFields = listOf("firstname", "lastname", "patronymic", "group")

        val AvailableNameSymbolsRegEx = Regex("[a-zA-zа-яА-Яё]")
        val AvailableGroupSymbolsRegEx = Regex("[0-9a-zA-zа-яА-Яё]|-")
    }

}