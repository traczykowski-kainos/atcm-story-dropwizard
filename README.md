[![Build Status](https://travis-ci.org/KainosSoftwareLtd/atcm-story-dropwizard.svg?branch=master)](https://travis-ci.org/KainosSoftwareLtd/atcm-story-dropwizard)

```


        _______/\                    /\______
      /       \   \________________/   /      \
    /           \ __________________ /          \
  /                                               \
 /                                                   \
 \        /\                              /\        /
   \    /   |                            |   \    /
     \/     |                            |     \/
            |                            |
            |           _                |
            |          /  \              |
            |         /|oo \             |
            |        (_|  /_)            |
            |          `@/  \    _       |
            |          |     \   \\      |
            |           \||   \   ))     |
            |           |||\ /  \//      |
            |         _//|| _\   /       |
            |        (_/(_|(____/        |
            |                            |
             ============================
```

## ATCM Practical Demo

```
./gradlew clean build test
```

```
./gradlew run
```

### Available Endpoints (Port 9420)

    GET     /uniqueId (com.kainos.atcm.resources.UniqueIdResource)
    DELETE  /cart/write/{cartId} (com.kainos.atcm.resources.CustomerCartWriteResource)
    POST    /cart/write/{cartId} (com.kainos.atcm.resources.CustomerCartWriteResource)
    GET     /product/read (com.kainos.atcm.resources.ProductReadResource)
    GET     /status (com.kainos.atcm.resources.StatusResource)
    GET     /cart/read (com.kainos.atcm.resources.CustomerCartReadResource)
    GET     /cart/read/{cartId} (com.kainos.atcm.resources.CustomerCartReadResource)
    GET     /customer/read/{customerId} (com.kainos.atcm.resources.CustomerReadResource)
    GET     /read/events/customer-cart (com.kainos.atcm.resources.EventsReadResource)