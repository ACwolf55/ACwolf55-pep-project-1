package Service;

import Util.ConnectionUtil;
import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    // app.post("localhost:8080/messages",);
    public Message postMessage(Message message) {
        return messageDAO.postMessage(message);
    }

    // app.get("localhost:8080/messages",);
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // app.get("localhost:8080/messages/{message_id}",);
    public Message getSingleMessage(int message_id) {
        return messageDAO.getSingleMessage(message_id);
    }

    // app.delete("localhost:8080/messages/{message_id}",);
    public Message deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    // app.patch("localhost:8080/messages/{message_id}",this::patchMessageHandler);
    public Message patchMessage(String message_text, int message_id) {
        boolean exists = messageDAO.messageExists(message_id);
        if(!exists){
            return null;
        }
        return messageDAO.patchMessage(message_text, message_id);
    }

    // app.get("localhost:8080/accounts/{account_id}/messages",);
    public List<Message> getUserMessages(int account_id) {
        return messageDAO.getUserMessages(account_id);
    }

}