package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

        public SocialMediaController() {
            this.accountService = new AccountService();
            this.messageService = new MessageService();
        }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/login", this::loginHandler);
        app.post("/register", this::registerHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getSingleMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

     // app.post("localhost:8080/login",);
     private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        AccountService accountService = new AccountService();

        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());

        if (loggedInAccount != null) {
            ctx.json(mapper.writeValueAsString(loggedInAccount));
        } else {
            ctx.status(401);
        }
    }

    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        AccountService accountService = new AccountService();

        Account addedAccount = accountService.register(account.getUsername(), account.getPassword());
        if (addedAccount != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    // Endpoint: POST /messages
    private void postMessagesHandler(Context ctx) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(ctx.body(), Message.class);
            MessageService messageService = new MessageService();
            Message postedMessage = messageService.postMessage(message);
            if (postedMessage != null) {
                ctx.json(mapper.writeValueAsString(postedMessage));
            } else {
                ctx.status(400);
            }
        } catch (JsonProcessingException e) {
            ctx.status(500);
        }
    }

    // Endpoint: GET /messages
    private void getAllMessagesHandler(Context ctx) {
        MessageService messageService = new MessageService();
        List<Message> allMessages = messageService.getAllMessages();
        ctx.json(allMessages);
    }

    // Endpoint: GET /messages/{message_id}
    private void getSingleMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        MessageService messageService = new MessageService();
        Message singleMessage = messageService.getSingleMessage(messageId);
        ctx.status(200);
        if (singleMessage != null) {
            ctx.json(singleMessage);
        }
    }

    // Endpoint: DELETE /messages/{message_id}
    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        MessageService messageService = new MessageService();
        Message deletedMessage = messageService.deleteMessage(messageId);
        ctx.status(200);
        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        }
    }

    // Endpoint: PATCH /messages/{message_id}
    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = mapper.readValue(ctx.body(), Message.class);
            String messageText = message.message_text;
            MessageService messageService = new MessageService();
            Message patchedMessage = messageService.patchMessage(messageText, messageId);
            if (patchedMessage != null) {
                ctx.json(patchedMessage);
            } else {
                ctx.status(400);
            }
        }

    // Endpoint: GET /accounts/{account_id}/messages
    private void getAccountMessagesHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        MessageService messageService = new MessageService();
        List<Message> userMessages = messageService.getUserMessages(accountId);
        ctx.status(200);
        ctx.json(userMessages);

    }


}