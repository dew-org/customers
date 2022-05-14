package com.dew

import com.dew.customers.application.create.CreateCustomerCommand
import com.dew.customers.application.update.UpdateCustomerCommand
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@MicronautTest
@Testcontainers
class CustomerControllerSpec extends Specification implements TestPropertyProvider {

    static MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
            .withExposedPorts(27017)

    @Inject
    CustomerClient client

    def "interact with customers api"() {
        when: 'create a customer'
        var customer = new CreateCustomerCommand("123", "Manolo", "Jesus")
        var status = client.save(customer)

        then: 'the status should be created'
        status == HttpStatus.CREATED

        when: 'get a customer'
        var response = client.findById("123")

        then: 'the customer should be returned'
        response.status == HttpStatus.OK
        response.body.present
        response.body().id == "123"

        when: 'get a customer that does not exist'
        response = client.findById("321")

        then: 'the customer should not be returned'
        response.status == HttpStatus.NOT_FOUND
        !response.body.present

        when: 'create same customer again'
        client.save(customer)

        then: 'the status should be conflict'
        thrown(HttpClientResponseException)

        when: 'update a customer'
        customer = new UpdateCustomerCommand("Manolo", "Jesus", "111111111", "manolos@mail.com")
        response = client.update("123", customer)

        then: 'the status should be ok'
        response.status == HttpStatus.OK

        when: 'update a customer that does not exist'
        customer = new UpdateCustomerCommand("Manolo", "Jesus", "111111111", null)
        response = client.update("321", customer)

        then: 'the status should be not found'
        response.status == HttpStatus.NOT_FOUND
    }

    @Override
    Map<String, String> getProperties() {
        mongo.start()

        return ["mongodb.uri": mongo.replicaSetUrl]
    }
}
