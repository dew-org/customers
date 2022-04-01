package com.dew

import com.dew.MongoDbUtils.closeMongoDb
import com.dew.MongoDbUtils.mongoDbUri
import com.dew.MongoDbUtils.startMongoDb
import com.dew.customers.domain.Customer
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest : TestPropertyProvider {

    @Test
    fun save_customer_should_return_created(customerClient: CustomerClient) {
        val customer = Customer("123", "Manolo", "Jesus")

        val status = customerClient.save(customer)

        assertEquals(CREATED, status)
    }

    override fun getProperties(): Map<String, String> {
        startMongoDb()
        return mapOf("mongodb.uri" to mongoDbUri)
    }

    @AfterAll
    fun cleanup() {
        closeMongoDb()
    }
}