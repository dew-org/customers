package com.dew

import com.dew.customers.application.create.CreateCustomerCommand
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
        when:
        var customer = new CreateCustomerCommand("123", "Manolo", "Jesus")
        var status = client.save(customer)

        then:
        status == HttpStatus.CREATED

        when:
        var response = client.findById("123")

        then:
        response.status == HttpStatus.OK
        response.body.present
        response.body().id == "123"

        when:
        response = client.findById("321")

        then:
        response.status == HttpStatus.NOT_FOUND
        !response.body.present

        when:
        client.save(customer)

        then:
        thrown(HttpClientResponseException)
    }

    @Override
    Map<String, String> getProperties() {
        mongo.start()

        return ["mongodb.uri": mongo.replicaSetUrl]
    }
}
