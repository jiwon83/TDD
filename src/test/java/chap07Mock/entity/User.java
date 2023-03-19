package chap07Mock.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String id, password, email;

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public User(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
