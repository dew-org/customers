package com.dew.customers.infrastructure.persistence.mongo

import com.dew.customers.domain.Customer
import com.dew.customers.domain.CustomerRepository
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Singleton
class MongoDbCustomerRepository(
    private val mongoDbConfiguration: MongoDbConfiguration, private val mongoClient: MongoClient
) : CustomerRepository {

    override fun save(customer: Customer): Mono<Boolean> =
        Mono.from(collection.insertOne(customer)).map { true }.onErrorReturn(false)

    override fun findById(id: String): Mono<Customer> {
        return Mono.from(collection.find(Filters.eq("_id", id)))
    }

    private val collection: MongoCollection<Customer>
        get() = mongoClient.getDatabase(mongoDbConfiguration.name)
            .getCollection(mongoDbConfiguration.collection, Customer::class.java)
}