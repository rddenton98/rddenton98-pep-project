package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::newAccountHandler);
        app.get("/accounts", this::getAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    // User registration
    private void newAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        if (account.getUsername().length() == 0 ||
            account.getPassword().length() < 4
        ) {
            ctx.status(400);
            return;
        }

        Account addedAccount = accountService.addAccount(account);

        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount)).status(200);
        }
        else {
            ctx.status(400);
        }
    }

    // User login
    private void getAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account retrievedAccount = accountService.getAccount(account);

        if (retrievedAccount != null) {
            ctx.json(mapper.writeValueAsString(retrievedAccount)).status(200);
        }
        else {
            ctx.status(401);
        }
    }

    // Create new message


    // Get all messages


    // Get all messages for user


    // Get message by id


    // Update message by id


    // Delete message by id
}