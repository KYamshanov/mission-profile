package ru.kyamshanov.mission.profile.config

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@EnableReactiveMongoRepositories(basePackages = ["ru.kyamshanov.mission.profile.repository"])
@Configuration
class MongoReactiveConfig(
    @Value("\${MONGO_INITDB_ROOT_USERNAME}")
    private val mongoUsername: String,
    @Value("\${MONGO_INITDB_DATABASE}")
    private val mongoDatabase: String,
    @Value("\${MONGO_INITDB_ROOT_PASSWORD}")
    private val mongoPassword: String,
    @Value("\${MONGO_HOST}")
    private val host: String,
    @Value("\${MONGO_PORT}")
    private val port: Int,
    @Value("\${MONGO_AUTH_DATABASE}")
    private val authDatabase: String
) : AbstractReactiveMongoConfiguration() {
    override fun getDatabaseName(): String = mongoDatabase

    override fun autoIndexCreation(): Boolean = true

    override fun configureClientSettings(builder: MongoClientSettings.Builder) {
        builder
            .credential(MongoCredential.createCredential(mongoUsername, authDatabase, mongoPassword.toCharArray()))
            .applyToClusterSettings { settings -> settings.hosts(listOf(ServerAddress(host, port))) }
    }
}