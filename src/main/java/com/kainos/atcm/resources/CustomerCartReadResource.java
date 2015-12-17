package com.kainos.atcm.resources;

import com.codahale.metrics.annotation.Timed;
import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;
import java.util.Collection;
import java.util.UUID;

@Path("/cart/read")
public class CustomerCartReadResource {

    private CustomerCartRepository customerCartRepository;

    public CustomerCartReadResource(CustomerCartRepository customerCartRepository){
        this.customerCartRepository = customerCartRepository;
    }

    @GET
    @Timed
    @Path("/{cartId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleCart(@PathParam("cartId") String cartId) {
        Response response = Response.status(Response.Status.NOT_FOUND).build();
        CustomerCart customerCart = customerCartRepository.getCustomerCart(UUID.fromString(cartId));
        if (customerCart != null) {
            response = Response.ok(customerCart).build();
        }
        return response;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<CustomerCart> getAllCarts() {
        return customerCartRepository.getAllCarts();
    }
}
