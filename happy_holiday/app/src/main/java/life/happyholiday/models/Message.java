package life.happyholiday.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Message object used to store message.
 * Used for stfalcon lib
 *
 * Created by liuyang on 9/30/2017.
 */

public class Message extends RealmObject implements IMessage {
    @PrimaryKey
    private String id;

    private String text;
    private Author user;
    private Date createdAt;

    public Message() {
    }

    public Message(String text, Author user, Date createdAt) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(Author user) {
        this.user = user;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}
