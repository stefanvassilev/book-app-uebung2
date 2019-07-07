package com.ovgu.integration;


import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.applications.*;

import java.io.File;

public class PreIntegrationTestsExecutor {


    public static final String IT_BOOK_APP = "it-book-app";

    public static void main(String... args) {

        System.out.println(System.getProperty("user.dir"));
        TestRunner runner = TestRunner.getInstance();
        CloudFoundryOperations client = runner.getCloudFoundryOperations();
        client.applications().pushManifest(
                PushApplicationManifestRequest.builder().manifest(ApplicationManifest.builder()
                .name(IT_BOOK_APP)
                .host(IT_BOOK_APP)
                .path(new File("book-server/target/book-server-0.0.1-SNAPSHOT.jar").toPath())
                .environmentVariable("SPRING_PROFILES_ACTIVE", "cloud")
                .memory(768).build())
                .build()).block();

        ApplicationDetail detail = client.applications().get(GetApplicationRequest.builder().name(IT_BOOK_APP).build()).block();
        runner.setUri(detail.getUrls().get(0));
        System.out.println(detail);

    }
}
