package se.cygni.stacktrace.jaxbws;

import org.junit.Test;

import com.sun.jersey.test.framework.JerseyTest;

public class UserAccountResourceJerseyTestXML extends JerseyTest {

    public UserAccountResourceJerseyTestXML() {
        super("se.cygni.stacktrace.jaxbws");
    }

    @Test
    public void testXML() throws Exception {
        UserAccounts accounts = resource().path("/accounts")
                .accept("application/xml").get(UserAccounts.class);

        UserAccountResourceTest.assertAccounts(accounts);
    }
}
