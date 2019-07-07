package com.ovgu.integration;


import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;

public class TestRunner {


    public static final String HOST_API = "api.run.pivotal.io";
    private static String USERNAME_ENV_KEY = "CF_USERNAME";
    private static String PASSOWORD_ENV_KEY = "CF_PASSWORD";
    private static TestRunner testRunner;
    private String uri;


    private final CloudFoundryOperations cloudFoundryOperations;

    private TestRunner() {

        CloudFoundryClient client = ReactorCloudFoundryClient.builder()
                .tokenProvider(
                        PasswordGrantTokenProvider
                                .builder()
                                .username(System.getenv("CF_IT_USERNAME"))
                                .password(System.getenv("CF_IT_PASSWORD"))
                                .build())
                .connectionContext(DefaultConnectionContext.builder().apiHost(HOST_API).build())
                .build();
        cloudFoundryOperations = DefaultCloudFoundryOperations
                .builder()
                .organization("exchangeagram")
                .space("qa")
                .cloudFoundryClient(client)
                .build();
    }

    public String getNamespace() {
        return "it_" + Runtime.getRuntime();
    }


    public static TestRunner getInstance() {
        if (testRunner == null) {
            testRunner = new TestRunner();
        }

        return testRunner;
    }

    public CloudFoundryOperations getCloudFoundryOperations() {
        return cloudFoundryOperations;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
