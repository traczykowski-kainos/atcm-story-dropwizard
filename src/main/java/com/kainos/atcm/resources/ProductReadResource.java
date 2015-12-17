package com.kainos.atcm.resources;

import com.codahale.metrics.annotation.Timed;
import com.kainos.atcm.domain.product.Product;
import com.kainos.atcm.repository.ProductRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/product/read")
public class ProductReadResource {
    private ProductRepository productRepository;

    public ProductReadResource(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Product> product() {
        return productRepository.getProducts();
    }
}
