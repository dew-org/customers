package com.dew.customers.infrastructure.persistence.mongo

import com.dew.customers.domain.Customer
import com.dew.customers.domain.CustomerRepository
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import jakarta.inject.Singleton
import org.bson.Document
import reactor.core.publisher.Mono

@Singleton
class MongoDbCustomerRepository(
    private val mongoDbConfiguration: MongoDbConfiguration,
    private val mongoClient: MongoClient
) : CustomerRepository {

    override fun save(customer: Customer): Mono<Boolean> {
        return Mono.from(collection.insertOne(customer.toDocument()))
            .map { true }
            .onErrorReturn(false)
    }

    override fun findById(id: String): Mono<Customer> {
        return Mono.from(collection.find(Filters.eq("_id", id)))
            .mapNotNull { it.toCustomer() }
    }

    private val collection: MongoCollection<Document>
        get() = mongoClient.getDatabase(mongoDbConfiguration.name)
            .getCollection(mongoDbConfiguration.collection)

    private fun Customer.toDocument(): Document =
        Document("_id", id)
            .append("name", name)
            .append("lastName", lastName)
            .append("phoneNumber", phoneNumber)
            .append("email", email)
            .append("createdAt", createdAt)

    private fun Document.toCustomer(): Customer {
        val customer = Customer(
            id = getString("_id"),
            name = getString("name"),
            lastName = getString("lastName"),
            phoneNumber = getString("phoneNumber"),
            email = getString("email"),
            createdAt = getDate("createdAt")
        )

        customer.updatedAt = getDate("updatedAt")

        return customer
    }
}