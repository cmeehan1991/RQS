/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignIn;

import Connections.DBConnection;
import SalesMain.SalesMain;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author cmeehan
 */
public class UserSignIn {

    private final Connection CONN = new DBConnection().connect();
    private String username, password, userID;
    private Stage stage;

    public UserSignIn(String username, String password, Stage stage) {
        this.username = username;
        this.password = password;
        this.stage = stage;
        try {
            userSignIn();
        } catch (SQLException | IOException ex) {
            System.out.println("UserSignIn(): "+ex.getMessage());
        }
    }

    // Check to validate that the user input the proper credentials against what is in the database. 
    private boolean verifyUserCredentials() throws SQLException {
        boolean validUser = false;
        String SQL = "SELECT userID, username, password FROM authorized_users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = CONN.prepareStatement(SQL);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String actUser = rs.getString("username");
                String actPass = rs.getString("password");
                if (actUser.equals(username)) {
                    if (actPass.equals(password)) {
                        validUser = true;
                        this.userID = rs.getString("userID");
                    }
                } else {
                    validUser = false;
                }
            }
        } catch (Exception ex) {
            System.out.println("verifyUserCredentials(): "+ex.getMessage());
            CONN.close();
        }
        return validUser;
    }

    // Check for user access rights
    private String rights() throws SQLException {
        String accessRights = null;
        String SQL = "SELECT rights FROM authorized_users WHERE userID = ?";
        try {
            PreparedStatement ps = CONN.prepareStatement(SQL);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!rs.getString("rights").equals("NONE")) {
                    accessRights = rs.getString("rights");
                } else {
                    JOptionPane.showMessageDialog(null, "ACCESS DENIED: You do not have access to RQS.", "ACCESS DENIED", JOptionPane.WARNING_MESSAGE);
                    stage.close();
                }
            }
        } catch (SQLException | HeadlessException ex) {
            System.out.println("rights(): "+ex.getMessage());
            CONN.close();
        }
        return accessRights;
    }

    // get the User's department
    private String department() throws SQLException {
        String department = null;
        String SQL = "SELECT department FROM authorized_users WHERE userID = ?";
        try {
            PreparedStatement ps = CONN.prepareStatement(SQL);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                department = rs.getString("department");
            }
        } catch (Exception ex) {
            System.out.println("department(): "+ex.getMessage());
            CONN.close();
        }
        return department;
    }

    private void userSignIn() throws SQLException, IOException {
        if (verifyUserCredentials()) {
            if (!rights().equals("NONE")) {
                switch (department()) {
                    case "Customer Service":
                        break;
                    case "Trade Management":
                        break;
                    case "Sales":
                        stage.close();
                        SalesMain salesMain = new SalesMain(userID);
                        break;
                    case "Admin":
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
