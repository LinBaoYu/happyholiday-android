package life.happyholiday.models;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Author used for stfalcon lib
 *
 * Created by liuyang on 9/30/2017.
 */

public class Author implements IUser {
    private String id;
    private String name;
    private String avatar;

    public Author(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
