package ru.kyamshanov.mission.profile.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
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
) {


    @Bean
    fun reactiveMongoClient(): MongoClient {
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString("mongodb://$host:$port/$mongoDatabase"))
            .credential(MongoCredential.createCredential(mongoUsername, authDatabase, mongoPassword.toCharArray()))
            .build()
        return MongoClients.create(settings)
    }

    @Bean
    fun reactiveMongoDatabaseFactory(client: MongoClient): ReactiveMongoDatabaseFactory {
        return SimpleReactiveMongoDatabaseFactory(client, mongoDatabase)
    }

    @Bean
    fun reactiveMongoTemplate(factory: ReactiveMongoDatabaseFactory): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(factory)
    }
}