package ru.kyamshanov.mission.profile.exceptions


internal open class ValidateException : Exception {

    constructor() : super()
    constructor(message: String) : super(message)
}

internal class InvalidFieldException(override val message: String) : ValidateException()