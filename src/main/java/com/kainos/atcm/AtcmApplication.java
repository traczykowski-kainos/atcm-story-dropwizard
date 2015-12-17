package com.kainos.atcm;

import com.kainos.atcm.config.AtcmServiceConfiguration;
import com.kainos.atcm.handler.ProductAddedToCustomerCartHandler;
import com.kainos.atcm.handler.ProductRemovedFromCustomerCartHandler;
import com.kainos.atcm.health.AtcmHealthcheck;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.CustomerRepository;
import com.kainos.atcm.repository.EventStoreRepository;
import com.kainos.atcm.repository.ProductRepository;
import com.kainos.atcm.resources.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class AtcmApplication extends Application<AtcmServiceConfiguration> {

    private static final String ATCM_SERVICE = "atcm";

    public static void main(String[] args) throws Exception {
        new AtcmApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AtcmServiceConfiguration> bootstrap) {
    }

    @Override
    public void run(AtcmServiceConfiguration configuration, Environment environment) throws Exception {
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        ProductRepository productRepository = new ProductRepository();
        EventStoreRepository eventStoreRepository = new EventStoreRepository();
        CustomerRepository customerRepository = new CustomerRepository();

        // Register Resources
        environment.jersey().register(new StatusResource());
        environment.jersey().register(new CustomerCartReadResource(customerCartRepository));
        environment.jersey().register(new CustomerCartWriteResource(customerCartRepository, eventStoreRepository, new ProductAddedToCustomerCartHandler(customerCartRepository, productRepository), new ProductRemovedFromCustomerCartHandler(customerCartRepository)));
        environment.jersey().register(new EventsReadResource(eventStoreRepository));
        environment.jersey().register(new ProductReadResource(productRepository));
        environment.jersey().register(new UniqueIdResource());
        environment.jersey().register(new CustomerReadResource(customerRepository));

        // Register healthcheck
        environment.healthChecks().register(getName(), new AtcmHealthcheck());
    }

    @Override
    public String getName() {
        return ATCM_SERVICE;
    }
}