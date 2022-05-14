package com.dew.customers.application

import com.dew.customers.application.create.CreateCustomerCommand
import com.dew.customers.application.update.UpdateCustomerCommand
import com.dew.customers.domain.Customer
import com.dew.customers.domain.CustomerRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Singleton
class CustomerService(private val customerRepository: CustomerRepository) {

    fun save(command: CreateCustomerCommand): Mono<Boolean> {
        val customer = Customer(
            command.id, command.name, command.lastName, command.phoneNumber, command.email
        )

        return customerRepository.save(customer)
    }

    fun findById(id: String): Mono<CustomerResponse> {
        return customerRepository.findById(id).mapNotNull { customer -> mapResponse(customer) }
    }

    private fun mapResponse(customer: Customer): CustomerResponse = CustomerResponse(
        customer.id,
        customer.name,
        customer.lastName,
        customer.phoneNumber,
        customer.email,
        customer.createdAt,
        customer.updatedAt
    )

    fun update(id: String, command: UpdateCustomerCommand): Mono<Boolean> {
        return customerRepository.findById(id).flatMap {
            val customerToUpdate = it.copy(
                name = command.name,
                lastName = command.lastName,
                phoneNumber = command.phoneNumber,
                email = command.email
            )

            customerRepository.update(customerToUpdate)
        }
    }
}