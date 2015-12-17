package com.kainos.atcm.resources;

import com.codahale.metrics.annotation.Timed;
import com.kainos.atcm.command.AddProductToCustomerCart;
import com.kainos.atcm.command.RemoveProductFromCustomerCart;
import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.event.ProductRemovedFromCustomerCart;
import com.kainos.atcm.handler.ProductAddedToCustomerCartHandler;
import com.kainos.atcm.handler.ProductRemovedFromCustomerCartHandler;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.EventStoreRepository;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;
import java.util.UUID;

@Path("/cart/write")
public class CustomerCartWriteResource {
    private CustomerCartRepository customerCartRepository;
    private EventStoreRepository eventStoreRepository;
    private ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler;
    private ProductRemovedFromCustomerCartHandler productRemovedFromCustomerCartHandler;

    public CustomerCartWriteResource(CustomerCartRepository customerCartRepository, EventStoreRepository eventStoreRepository, ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler, ProductRemovedFromCustomerCartHandler productRemovedFromCustomerCartHandler) {
        this.customerCartRepository = customerCartRepository;
        this.eventStoreRepository = eventStoreRepository;
        this.productAddedToCustomerCartHandler = productAddedToCustomerCartHandler;
        this.productRemovedFromCustomerCartHandler = productRemovedFromCustomerCartHandler;
    }

    @POST
    @Path("/{cartId}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("cartId") UUID cartId, AddProductToCustomerCart addProductToCustomerCart) {
        // Validation could go here
        //

        // Map Command to Event
        //
        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setCorrelationId(addProductToCustomerCart.getCorrelationId());
        productAddedToCustomerCart.setProductId(addProductToCustomerCart.getProductId());

        // Date Time Antics
        //
        productAddedToCustomerCart.setUpdateDateTime(DateTime.parse(addProductToCustomerCart.getUpdateDateTimeUTC()));

        // Store Event
        //
        eventStoreRepository.storeCustomerCartEvent(cartId, productAddedToCustomerCart.getCorrelationId(), productAddedToCustomerCart.toString());

        // Publish Event (Queue goes here)
        //
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        return Response.accepted().build();
    }

    @DELETE
    @Path("/{cartId}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("cartId") UUID cartId, RemoveProductFromCustomerCart removeProductFromCustomerCart) {
        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);
        if (customerCart == null) {
            throw new HTTPException(404);
        }

        // Map Command to Event
        ProductRemovedFromCustomerCart productRemovedFromCustomerCart = new ProductRemovedFromCustomerCart();
        productRemovedFromCustomerCart.setCartId(cartId);
        productRemovedFromCustomerCart.setCorrelationId(removeProductFromCustomerCart.getCorrelationId());
        productRemovedFromCustomerCart.setProductId(removeProductFromCustomerCart.getProductId());

        // Date Time Antics
        productRemovedFromCustomerCart.setUpdateDateTime(DateTime.parse(removeProductFromCustomerCart.getUpdateDateTimeUTC()));

        // Store Event
        eventStoreRepository.storeCustomerCartEvent(cartId, productRemovedFromCustomerCart.getCorrelationId(), productRemovedFromCustomerCart.toString());

        // Publish Event (Queue goes here)
        productRemovedFromCustomerCartHandler.handle(productRemovedFromCustomerCart);

        return Response.accepted().build();
    }
}
