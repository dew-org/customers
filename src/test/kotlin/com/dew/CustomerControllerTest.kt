package com.dew

import com.dew.MongoDbUtils.closeMongoDb
import com.dew.MongoDbUtils.mongoDbUri
import com.dew.MongoDbUtils.startMongoDb
import com.dew.customers.application.create.CreateCustomerCommand
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerTest : TestPropertyProvider {

    @Inject
    lateinit var customerClient: CustomerClient

    @Test
    fun save_customer_should_return_created() {
        val customer = CreateCustomerCommand("123", "Manolo", "Jesus")

        val status = customerClient.save(customer)

        assertEquals(CREATED, status)
    }

    @Test
    fun find_customer_should_return_not_found() {
        val response = customerClient.findById("321")

        assertNotNull(response)
        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertNull(response.body())
    }

    @Test
    fun find_customer_should_return_ok() {
        val customer = CreateCustomerCommand("1234", "Manolo", "Jesus")
        val status = customerClient.save(customer)
        assertEquals(CREATED, status)

        val response = customerClient.findById("1234")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
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