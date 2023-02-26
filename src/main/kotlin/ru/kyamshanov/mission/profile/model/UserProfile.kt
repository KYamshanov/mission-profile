package ru.kyamshanov.mission.profile.model

internal data class UserProfile(
    val id: String,
    val login: String,
    val data: Info
) {

    @JvmInline
    value class Info(val value: Map<String, Any>)
}
