package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

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
        app.post("/login", this::getAccountHandler);
        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);

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

        if (account.getUsername().isBlank() ||
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
    private void newMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        if (message.getMessage_text().isBlank() ||
            message.getMessage_text().length() >= 255 ||
            accountService.getAccountById(message.getPosted_by()) == null
        ) {
            ctx.status(400);
            return;
        }

        Message addedMessage = messageService.addMessage(message);

        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage)).status(200);
        }
        else {
            ctx.status(400);
        }
    }

    // Get all messages
    private void getAllMessagesHandler(Context ctx) {
        ArrayList<Message> messages = messageService.getAllMessages();
        ctx.json(messages).status(200);
    }

    // Get all messages for user
    private void getAllMessagesByUserHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        ArrayList<Message> messages = messageService.getAllMessagesByAccountId(id);
        ctx.json(messages).status(200);
    }

    // Get message by id
    private void getMessageHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessage(id);
        
        if (message != null) {
            ctx.json(message).status(200);
        }
        else {
            ctx.status(200).result("");
        }
    }

    // Update message by id
    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        message.setMessage_id(id);

        if (message.getMessage_text().isBlank() ||
            message.getMessage_text().length() >= 255
        ) {
            ctx.status(400);
            return;
        }

        Message updatedMessage = messageService.updateMessage(message);

        if (updatedMessage != null) {
            ctx.json(mapper.writeValueAsString(updatedMessage)).status(200);
        }
        else {
            ctx.status(400);
        }
    }

    // Delete message by id
    private void deleteMessageHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(id);
        
        if (message != null) {
            ctx.json(message).status(200);
        }
        else {
            ctx.status(200).result("");
        }
    }
}