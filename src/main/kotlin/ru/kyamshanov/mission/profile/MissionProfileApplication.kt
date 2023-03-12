package ru.kyamshanov.mission.profile

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [MongoReactiveAutoConfiguration::class, MongoAutoConfiguration::class])
class MissionProfileApplication

fun main(args: Array<String>) {
    runApplication<MissionProfileApplication>(*args)
}
