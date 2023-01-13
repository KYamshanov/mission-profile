package ru.kyamshanov.mission.profile.config

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.reactivestreams.client.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@EnableReactiveMongoRepositories
class MongoReactiveConfig(
    @Value("\${MONGO_INITDB_ROOT_USERNAME}")
    private val mongoUsername: String,
    @Value("\${MONGO_INITDB_DATABASE}")
    private val mongoDatabase: String,
    @Value("\${MONGO_INITDB_ROOT_PASSWORD}")
    private val mongoPassword: CharArray,
    @Value("\${mongodb.host}")
    private val host: String,
    @Value("\${mongodb.port}")
    private val port: Int
) {


    @Bean
    fun mongoClient(): ReactiveMongoClientFactoryBean = ReactiveMongoClientFactoryBean().apply {
        setHost(host)
        setPort(port)
        setMongoClientSettings(
            MongoClientSettings.builder()
                .credential(MongoCredential.createCredential(mongoUsername, mongoDatabase, mongoPassword))
                .build()
        )
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