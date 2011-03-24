package se.cygni.stacktrace.jaxbws;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import se.cygni.stacktrace.jaxbws.UserAccount.Services;

@Path("/accounts")
public class UserAccountResource {
         
    @GET
    @Produces({"application/xml", "application/json"})
    public UserAccounts getUserAccounts() {
        UserAccount account1 = createUserAccount(1, "account1", "account1@localhost", "news", "music");
        UserAccount account2 = createUserAccount(2, "account2", "account2@localhost", "news", "sports");

        UserAccounts accounts = new UserAccounts();
        accounts.getUserAccount().add(account1);
        accounts.getUserAccount().add(account2);
        return accounts;
    }
    
    private UserAccount createUserAccount(int id, String username, String email, String... serviceStrings) {
        final UserAccount account = new UserAccount();
        account.setId(id);
        account.setUsername(username);
        account.setEmail(email);

        Services services = new Services();
        services.service = Arrays.asList(serviceStrings);
        account.setServices(services);
        return account;
    }
}
