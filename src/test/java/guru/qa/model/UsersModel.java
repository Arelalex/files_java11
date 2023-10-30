package guru.qa.model;

import java.util.List;

public class UsersModel {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private List<String> language;

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }

    public List<String> getLanguage() {
        return this.language;
    }
}
