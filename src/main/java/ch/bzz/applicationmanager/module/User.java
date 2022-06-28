package ch.bzz.applicationmanager.module;

/**
 * model-class for user
 *
 * @author Kevin Stupar
 * @version 1.0
 * @since 23.05.2022
 */
public class User {
    private String userUUID;
    private String userName;
    private String password;
    private String userRole;
    private String userMail;

    /**
     * constructor of the user
     */
    public User() {
        setUserRole("guest");
    }

    /**
     * gets the user uuid.
     *
     * @return uuid
     */
    public String getUserUUID() {
        return userUUID;
    }

    /**
     * sets the user uuid.
     *
     * @param userUUID
     */
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    /**
     * gets the user name.
     *
     * @return name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the user name.
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * gets the user password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the user password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the user role.
     *
     * @return role
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * sets the user role.
     *
     * @param userRole
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * gets the email of the user
     *
     * @return mail
     */
    public String getUserMail() {
        return userMail;
    }

    /**
     * sets the email of the user
     *
     * @param userMail to be set
     */
    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
