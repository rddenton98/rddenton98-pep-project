package Service;

import java.util.ArrayList;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    // Constructors
    public MessageService() {
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Add a new message
    public Message addMessage(Message message) {
        return this.messageDAO.insertMessage(message);
    }

    // Get all messages from database
    public ArrayList<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    // Get a message by id
    public Message getMessage(int id) {
        return this.messageDAO.getMessage(id);
    }

    // Update message by id
    public Message updateMessage(Message message) {
        // Check if message exists
        if (this.messageDAO.getMessage(message.getMessage_id()) != null) {
            this.messageDAO.updateMessage(message);
            return this.messageDAO.getMessage(message.getMessage_id());
        }

        return null;
    }

    // Delete message by id
    public Message deleteMessage(int id) {
        Message message = this.messageDAO.getMessage(id);
        
        // Check if message exists
        if (message != null) {
            this.messageDAO.deleteMessage(id);
        }

        return message;
    }

    // Get all messages for account_id
    public ArrayList<Message> getAllMessagesByAccountId(int id) {
        return this.messageDAO.getAllMessagesByAccountId(id);
    }
}
