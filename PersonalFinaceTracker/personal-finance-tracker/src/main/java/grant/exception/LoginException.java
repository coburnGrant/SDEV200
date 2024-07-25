package grant.exception;

import java.sql.SQLException;

/** A class for exceptions to be thrown while logging in. */
public class LoginException extends Exception {
    /** Constructor that accepts a message */
    public LoginException(String message) {
        super(message);
    }

    /** Constructor that accepts a SQLException */
    public LoginException(SQLException sqlException) {
        this("SQL Exception: " + sqlException.getMessage());
    }
}
