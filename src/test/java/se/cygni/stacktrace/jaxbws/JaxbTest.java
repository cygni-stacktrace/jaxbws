package se.cygni.stacktrace.jaxbws;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.junit.Test;

import se.cygni.stacktrace.jaxbws.UserAccount;
import se.cygni.stacktrace.jaxbws.UserAccount.Services;
import se.cygni.stacktrace.jaxbws.UserAccounts;

/** Test case for the JAXB-sample (stacktrace.se). */
public class JaxbTest {
    /** Creates a UserAccount-object. */
    private UserAccount createUserAccount(final int id, final String username, final String email,
            final String... serviceStrings) {
        final UserAccount account = new UserAccount();
        account.setId(id);
        account.setUsername(username);
        account.setEmail(email);

        final Services services = new Services();
        services.service = Arrays.asList(serviceStrings);
        account.setServices(services);
        return account;
    }

    /** Test marshal/unmarshal via JAXB. */
    @Test
    public void testMarshalUnmarshal() throws JAXBException {
        // First, setup test data - two user accounts
        final UserAccount account1 = createUserAccount(1, "account1", "account1@localhost", "news", "music");
        final UserAccount account2 = createUserAccount(2, "account2", "account2@localhost", "news", "sports");

        final UserAccounts accounts = new UserAccounts();
        accounts.getUserAccount().add(account1);
        accounts.getUserAccount().add(account2);

        // Marshal the test data to XML
        final StringWriter writer = new StringWriter();
        final JAXBContext context = JAXBContext.newInstance(UserAccount.class.getPackage().getName());
        final Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(accounts, writer); // Store the XML in a writer

        // Unmarshal the XML to classes
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final UserAccounts accountsAfterRoundtrip = (UserAccounts) unmarshaller.unmarshal(new StringReader(writer.toString()));

        // Verify that the newly created objects are equal to the original test data
        Assert.assertNotNull(accountsAfterRoundtrip);
        Assert.assertFalse(accountsAfterRoundtrip.getUserAccount().isEmpty());
        Assert.assertEquals(accounts.getUserAccount().size(), accountsAfterRoundtrip.getUserAccount().size());

        final UserAccount accountAfterRoundtrip1 = accountsAfterRoundtrip.getUserAccount().get(0);
        Assert.assertEquals(account1.getId(), accountAfterRoundtrip1.getId());
        Assert.assertEquals(account1.getEmail(), accountAfterRoundtrip1.getEmail());
        Assert.assertEquals(account1.getServices().getService().size(), accountAfterRoundtrip1.getServices().getService()
                .size());
        Assert.assertTrue(accountAfterRoundtrip1.getServices().getService().contains("music"));
    }
}
