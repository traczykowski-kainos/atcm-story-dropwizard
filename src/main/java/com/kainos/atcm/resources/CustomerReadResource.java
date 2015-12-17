package com.kainos.atcm.resources;

import com.codahale.metrics.annotation.Timed;
import com.kainos.atcm.domain.customer.Customer;
import com.kainos.atcm.repository.CustomerRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

@Path("/customer/read")
public class CustomerReadResource {
    private CustomerRepository customerRepository;

    public CustomerReadResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GET
    @Path("/{customerId}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response cart(@PathParam("customerId") UUID customerId) {
        Response response = Response.status(Response.Status.NOT_FOUND).build();

        Optional<Customer> customer = customerRepository.getCustomer(customerId);
        if (customer.isPresent()) {
            response = Response.ok(customer.get()).build();
        }

        return response;
    }

}
