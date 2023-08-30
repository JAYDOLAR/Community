package DataBaseHandling;

public class FetchDataModal {
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userEmailPassword;

    public FetchDataModal(String firstName, String lastName, String userEmail, String userEmailPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userEmailPassword = userEmailPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmailPassword() {
        return userEmailPassword;
    }

    public void setUserEmailPassword(String userEmailPassword) {
        this.userEmailPassword = userEmailPassword;
    }
}
