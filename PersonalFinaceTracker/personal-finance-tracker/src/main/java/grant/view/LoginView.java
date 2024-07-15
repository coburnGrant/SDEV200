package grant.view;

import grant.UIHelpers;
import grant.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginView extends BorderPane {
    // Fields for the login view
    private LabeledTextField loginUsernameField;
    private PasswordField loginPasswordField;

    private Button loginButton;
    private Button noAccountButton;

    // Fields for the create new account view
    private LabeledTextField createUsernameField;
    private LabeledTextField createPasswordField;
    private LabeledTextField firstNameField;
    private LabeledTextField lastNameField;

    private Button createAccountButton;
    private Button haveAnAccountButton;

    private VBox loginVBox;
    private VBox createNewAccountVBox;

    public LoginView() { 
        initializeUI();
        displayLogin();
    }

    private void initializeUI() {
        setBackground(UIHelpers.PRIMARY_BACKGROUND);
        setPadding(new Insets(20));

        // Initialize login view
        loginVBox = new VBox(10);
        loginVBox.setAlignment(Pos.CENTER);

        loginUsernameField = new LabeledTextField("Username");
        loginPasswordField = new PasswordField(); //LabeledTextField("Password");

        loginButton = UIHelpers.createSimpleButton("Login");
        loginButton.setOnAction(e -> loginRequested());

        HBox noAccountHBox = new HBox(5);
        noAccountHBox.setAlignment(Pos.CENTER);

        Text noAccountText = UIHelpers.primaryText("Don't have an account?");
        
        noAccountButton = UIHelpers.createSimpleButton("Create new account");
        noAccountButton.setOnAction(e -> displayCreateNewAccount());

        noAccountHBox.getChildren().addAll(noAccountText, noAccountButton);

        loginVBox.getChildren().addAll(loginUsernameField, loginPasswordField, loginButton, noAccountHBox);

        // Initialize create new account view
        createNewAccountVBox = new VBox(10);
        createNewAccountVBox.setAlignment(Pos.CENTER);

        firstNameField = new LabeledTextField("First name");
        lastNameField = new LabeledTextField("Last name");
        createUsernameField = new LabeledTextField("Username");
        createPasswordField = new LabeledTextField("Password");

        HBox haveAnAccountHBox = new HBox(10);
        haveAnAccountHBox.setAlignment(Pos.CENTER);
        
        Text haveAnAccountText = UIHelpers.primaryText("Have an account?");

        haveAnAccountButton = UIHelpers.createSimpleButton("Login");
        haveAnAccountButton.setOnAction(e -> displayLogin());

        createAccountButton = UIHelpers.createSimpleButton("Create new Account");
        createAccountButton.setOnAction(e -> createAccountRequested());

        haveAnAccountHBox.getChildren().addAll(haveAnAccountText, haveAnAccountButton);

        createNewAccountVBox.getChildren().addAll(firstNameField, lastNameField, createUsernameField, createPasswordField, haveAnAccountHBox, createAccountButton);
    }

    private void displayLogin() {
        setCenter(loginVBox);
    }

    private void displayCreateNewAccount() {
        setCenter(createNewAccountVBox);
    }

    private void loginRequested() {
        System.out.println("Login requested!");

        String uname = loginUsernameField.getText();
        String pw = loginPasswordField.getText();   
        
        // TODO login user
    }

    private void createAccountRequested() {
        System.out.println("Create new account requested!");

        User newUser = getNewUser();

        // TODO add new user 
    }

    private User getNewUser() {
        // TODO validate these fields perhaps
        String fname = firstNameField.getText();
        String lname = lastNameField.getText();
        String uname = createUsernameField.getText();
        String pw = createPasswordField.getText();

        return new User(uname, pw, fname, lname);
    }
}