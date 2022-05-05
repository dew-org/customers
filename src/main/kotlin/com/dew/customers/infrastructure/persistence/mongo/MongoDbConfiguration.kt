package com.dew.customers.infrastructure.persistence.mongo

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.naming.Named

@ConfigurationProperties("db")
interface MongoDbConfiguration : Named {

    val collection: String
}