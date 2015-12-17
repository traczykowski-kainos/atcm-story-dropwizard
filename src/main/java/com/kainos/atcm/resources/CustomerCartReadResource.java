package com.kainos.atcm.resources;

import com.codahale.metrics.annotation.Timed;
import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public CustomerCart getSingleCart(@PathParam("cartId") String cartId) {
        CustomerCart customerCart = customerCartRepository.getCustomerCart(UUID.fromString(cartId));
        if (customerCart == null) {
            throw new HTTPException(404);
        }
        return customerCart;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<CustomerCart> getAllCarts() {
        return customerCartRepository.getAllCarts();
    }
}
