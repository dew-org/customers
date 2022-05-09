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
        val document = Document("_id", customer.id)
            .append("name", customer.name)
            .append("lastName", customer.lastName)
            .append("phoneNumber", customer.phoneNumber)
            .append("email", customer.email)
            .append("createdAt", customer.createdAt)

        return Mono.from(collection.insertOne(document)).map { true }
            .onErrorReturn(false)
    }

    override fun findById(id: String): Mono<Customer> {
        return Mono.from(collection.find(Filters.eq("_id", id)))
            .mapNotNull { document ->
                val customer = Customer(
                    id = document["_id"].toString(),
                    name = document.getString("name"),
                    lastName = document.getString("lastName"),
                    phoneNumber = document.getString("phoneNumber"),
                    email = document.getString("email"),
                    createdAt = document.getDate("createdAt")
                )

                customer.updatedAt = document.getDate("updatedAt")

                customer
            }
    }

    private val collection: MongoCollection<Document>
        get() = mongoClient.getDatabase(mongoDbConfiguration.name)
            .getCollection(mongoDbConfiguration.collection)
}