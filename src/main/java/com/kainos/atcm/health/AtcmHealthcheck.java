package com.kainos.atcm.health;

import com.codahale.metrics.health.HealthCheck;

public class AtcmHealthcheck extends HealthCheck {

    public AtcmHealthcheck() { }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
