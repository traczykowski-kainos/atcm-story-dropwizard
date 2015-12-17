package com.kainos.atcm.resources;

import com.codahale.metrics.annotation.Timed;
import com.kainos.atcm.repository.EventStoreRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Path("/read/events/")
public class EventsReadResource {
    private EventStoreRepository eventStoreRepository;

    public EventsReadResource(EventStoreRepository eventStoreRepository){
        this.eventStoreRepository = eventStoreRepository;
    }

    @GET
    @Path("customer-cart")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Map.Entry<UUID, String>> customerCartEvents() {
        return eventStoreRepository.getCustomerCartEvents();
    }
}
