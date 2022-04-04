package com.dew.customers.application

import com.dew.customers.application.create.CreateCustomerCommand
import com.dew.customers.domain.Customer
import com.dew.customers.domain.CustomerRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Singleton
class CustomerService(private val customerRepository: CustomerRepository) {

    fun save(command: CreateCustomerCommand): Mono<Boolean> {
        val customer = Customer(
            command.id,
            command.name,
            command.lastName,
            command.phoneNumber,
            command.email
        )

        return customerRepository.save(customer)
    }
}