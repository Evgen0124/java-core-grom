package lesson8.adds;

/**
 * Created by user on 04.07.2017.
 */
public class User extends  BaseEntity {
    String userName;
    String location;

    public User(long id, String userName, String location) {
       super(id);
        this.userName = userName;
        this.location = location;
    }
}