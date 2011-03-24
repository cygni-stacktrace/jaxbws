package se.cygni.stacktrace.jaxbws;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Test;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class UserAccountResourceJerseyTestJSON extends JerseyTest {

    @Test
    public void testJSON() throws Exception {
        UserAccounts accounts = resource().path("/accounts")
                .accept("application/json").get(UserAccounts.class);

        UserAccountResourceTest.assertAccounts(accounts);
    }

    @Override
    protected AppDescriptor configure() {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        return new WebAppDescriptor.Builder("se.cygni.stacktrace.jaxbws")
                .clientConfig(config).build();
    }
}
