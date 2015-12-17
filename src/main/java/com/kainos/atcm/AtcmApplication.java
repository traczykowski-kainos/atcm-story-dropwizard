package com.kainos.atcm;

import com.kainos.atcm.config.AtcmServiceConfiguration;
import com.kainos.atcm.health.atcmHealthcheck;
import com.kainos.atcm.resources.MergeRequestTrackerResource;
import com.kainos.atcm.resources.ProjectsResource;
import com.kainos.atcm.resources.StatusResource;
import com.kainos.atcm.services.Checker;
import com.kainos.atcm.services.CheckerImpl;
import com.kainos.atcm.services.MergeRequestSummariserImpl;
import com.kainos.atcm.services.ProjectSummariserImpl;
import com.kainos.atcm.services.checks.Check;
import com.kainos.atcm.services.checks.Checks;
import com.kainos.atcm.services.checks.ReadmeCheck;
import com.kainos.atcm.services.outputformatter.CsvOutputFormatter;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.gitlab.api.GitlabAPI;

import java.util.HashMap;
import java.util.Map;


public class AtcmApplication extends Application<atcmServiceConfiguration> {

    private static final String ATCM_SERVICE = "atcm";

    public static void main(String[] args) throws Exception {
        new AtcmApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AtcmServiceConfiguration> bootstrap) {
    }

    @Override
    public void run(AtcmServiceConfiguration configuration, Environment environment) throws Exception {
        Checker checker = new CheckerImpl(checks);
        environment.jersey().register(new StatusResource());
        environment.healthChecks().register(getName(), new AtcmHealthcheck());
    }

    @Override
    public String getName() {
        return ATCM_SERVICE;
    }
}