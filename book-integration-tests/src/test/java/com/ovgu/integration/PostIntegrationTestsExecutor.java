package com.ovgu.integration;

import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.applications.DeleteApplicationRequest;

import static com.ovgu.integration.PreIntegrationTestsExecutor.IT_BOOK_APP;

public class PostIntegrationTestsExecutor {

    public static void main(String... args) {

        TestRunner runner = TestRunner.getInstance();
        CloudFoundryOperations client = runner.getCloudFoundryOperations();
        client.applications().delete(DeleteApplicationRequest.builder().name(IT_BOOK_APP).build()).block();

    }
}
