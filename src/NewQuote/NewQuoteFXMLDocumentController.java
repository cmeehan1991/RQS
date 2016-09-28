/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewQuote;

import Connections.DBConnection;
import QuoteData.InsertQuoteData;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author cmeehan
 */
public class NewQuoteFXMLDocumentController implements Initializable {

    protected String userID;
    
    
    @FXML ScrollPane scrollPane; 
    
    @FXML
    TextField contactName, contactEmail, contactPhone, contactPhoneExtension, pol, pod, tshp, commodityDescription, baseOceanFreight, mafiMinimumCharge, baf, eca, thc, wfg, docFee, bookingNumber;

    @FXML
    ComboBox contactPhoneType, mtdApproval, spaceApproval, overseasApproval, tlmApproval, tradeLane, commodityClass, handlingInstructions, oftUnit, bafUnit, ecaUnit, thcUnit, wfgUnit, docUnit, declinedReason;

    @FXML
    CheckBox accessories, mafiMinimumCheckBox, bafIncluded, bafPerTariff, ecaIncluded, ecaPerTariff, thcIncluded, thcPerTariff, thcFAS, wfgIncluded, wfgPerTariff, wfgFAS, docIncluded;

    @FXML
    RadioButton israelWarRisk, tariffRate, spotRate, contractRate, indicatoryRate, ftfRate, booked, declined;

    @FXML
    Text companyName;

    @FXML
    TextArea internalComments, externalComments;

    @FXML
    Button addRowButton, removeRowButton, calculateCBMButton, uploadPLButton;

    @FXML
    TableView packingList;

    @FXML
    private void submitQuote(ActionEvent evt) {
        String[] customerInformation = {companyName.getText(), contactName.getText(), contactEmail.getText(), contactPhone.getText(), contactPhoneExtension.getText()};

        String[] quoteStatus = {mtdApproval.getAccessibleText(), spaceApproval.getAccessibleText(), overseasApproval.getAccessibleText(), tlmApproval.getAccessibleText()};

        String[] portInformation = {tradeLane.getAccessibleText(), pol.getText(), pod.getText(), tshp.getText()};

        List commodityInformation = new ArrayList<>();
        commodityInformation.add(commodityClass.getAccessibleText());
        commodityInformation.add(handlingInstructions.getAccessibleText());
        commodityInformation.add(accessories.isSelected());
        commodityInformation.add(commodityDescription.getText());
        
        List rateInformation = new ArrayList<>();
        rateInformation.add(baseOceanFreight.getText());
        rateInformation.add(oftUnit.getAccessibleText());
        rateInformation.add(mafiMinimumCheckBox.isSelected());
        rateInformation.add(mafiMinimumCharge.getText());
        rateInformation.add(baf.getText());
        rateInformation.add(bafUnit.getAccessibleText());
        rateInformation.add(bafIncluded.isSelected());
        rateInformation.add(bafPerTariff.isSelected());
        rateInformation.add(eca.getText());
        rateInformation.add(ecaUnit.getAccessibleText());
        rateInformation.add(ecaIncluded.isSelected());
        rateInformation.add(ecaPerTariff.isSelected());
        rateInformation.add(thc.getText());
        rateInformation.add(thcUnit.getAccessibleText());
        rateInformation.add(thcIncluded.isSelected());
        rateInformation.add(thcPerTariff.isSelected());
        rateInformation.add(thcFAS.isSelected());
        rateInformation.add(wfg.getText());
        rateInformation.add(wfgUnit.getAccessibleText());
        rateInformation.add(wfgIncluded.isSelected());
        rateInformation.add(wfgPerTariff.isSelected());
        rateInformation.add(wfgFAS.isSelected());
        rateInformation.add(docFee.getText());
        rateInformation.add(docUnit.getAccessibleText());
        rateInformation.add(docIncluded.isSelected());
        rateInformation.add(israelWarRisk.isArmed());
        
        List rateType = new ArrayList<>();
        rateType.add(tariffRate.isArmed());
        rateType.add(spotRate.isArmed());
        rateType.add(contractRate.isArmed());
        rateType.add(indicatoryRate.isArmed());
        rateType.add(ftfRate.isArmed());
        rateType.add(booked.isArmed());
        rateType.add(bookingNumber.getText());
        rateType.add(declined.isArmed());
        rateType.add(declinedReason.getAccessibleText());
        
        String[] comments = {internalComments.getText(), externalComments.getText()};

        // Insert the User ID and Date Quoted into the allquotes database
        Connection conn = new DBConnection().connect();
        String SQL = "INSERT INTO allquotes(user_ID, DATE_QUOTED) VALUES(?,?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userID);
            ps.setString(2, "13-09-2016 12:19:00");
            ps.execute();
            ResultSet key = ps.getGeneratedKeys();
            int quoteID = 0;
            while (key.next()) {
                quoteID = key.getInt(1);
            }
            conn.close();
            
           InsertQuoteData newQuote = new InsertQuoteData(quoteID, customerInformation, quoteStatus, portInformation, commodityInformation, rateInformation, rateType, packingList, comments, userID);
        } catch (Exception ex) {
            System.out.println("Error Controller: "+ex.getMessage());
        }
    }

    @FXML
    private void mafiMinInput(ActionEvent evt) {
        if (!mafiMinimumCharge.isEditable()) {
            mafiMinimumCharge.setEditable(true);
            mafiMinimumCharge.setVisible(true);
        } else {
            mafiMinimumCharge.setEditable(false);
            mafiMinimumCharge.setVisible(false);
        }
    }

    @FXML
    private void bafInput(ActionEvent evt) {
        if (bafIncluded.isSelected() || bafPerTariff.isSelected()) {
            baf.clear();
            baf.setEditable(false);
            baf.setOpacity(0.5);
            bafUnit.getSelectionModel().select(-1);
            bafUnit.setDisable(false);
        } else {
            baf.setEditable(true);
            baf.setOpacity(1.0);
            bafUnit.setDisable(false);
        }
    }

    @FXML
    private void ecaInput(ActionEvent evt) {
        if (ecaIncluded.isSelected() || ecaPerTariff.isSelected()) {
            baf.clear();
            eca.setEditable(false);
            eca.setOpacity(0.5);
            ecaUnit.getSelectionModel().select(-1);
            ecaUnit.setDisable(false);
        } else {
            eca.setEditable(true);
            eca.setOpacity(1.0);
            ecaUnit.setDisable(false);
        }
    }

    @FXML
    private void thcInput(ActionEvent evt) {
        if (thcIncluded.isSelected() || thcPerTariff.isSelected() || thcFAS.isSelected()) {
            thc.setText("");
            thc.setEditable(false);
            thc.setOpacity(0.5);
            thcUnit.setDisable(true);
            thcUnit.getSelectionModel().select(-1);
        } else {
            thc.setOpacity(1.0);
            thc.setEditable(true);
            thcUnit.setDisable(false);
        }
    }

    @FXML
    private void wfgInput(ActionEvent evt) {
        if (wfgIncluded.isSelected() || wfgPerTariff.isSelected() || wfgFAS.isSelected()) {
            wfg.setText("");
            wfg.setOpacity(0.5);
            wfgUnit.getSelectionModel().select(-1);
            wfg.setEditable(false);
            wfgUnit.setDisable(true);
        } else {
            wfg.setOpacity(1.0);
            wfg.setEditable(true);
            wfgUnit.setDisable(false);
        }
    }

    @FXML
    private void docInput(ActionEvent evt) {
        if (docIncluded.isSelected()) {
            docFee.clear();
            docFee.setOpacity(0.5);
            docFee.setEditable(false);
            docUnit.getSelectionModel().select(-1);
            docUnit.setDisable(true);
        } else {
            docFee.setOpacity(1.0);
            docFee.setEditable(true);
            docFee.setDisable(false);
        }
    }

    @FXML
    protected void setTradeLanes() throws SQLException {
        // Clear the combo box
        tradeLane.getItems().clear();

        // Connect to the database
        Connection conn = new DBConnection().connect();

        // Get all trade lanes from the TRADE_LANE table then populate the combo box with the results. 
        String SQL = "SELECT TRADE_LANE FROM trade_lanes";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tradeLane.getItems().add(rs.getString("TRADE_LANE"));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    @FXML
    protected void setCommodities() throws SQLException {
        // Clear the contents from the combo box
        commodityClass.getItems().clear();

        // Connect to the database
        Connection conn = new DBConnection().connect();

        //Get all the commodities from the commodities table and populate the commodity class combo box with them
        String SQL = "SELECT CLASS FROM commodities";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!rs.getString("CLASS").equals("N/A")) {
                    commodityClass.getItems().add(rs.getString("CLASS"));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.close();
        }

    }

    @FXML
    protected void setHandling() throws SQLException {
        // Clear the contents from the combo box
        handlingInstructions.getItems().clear();
        handlingInstructions.getItems().addAll("Self-Propelled", "Towable", "Static", "Break-Bulk", "MAFI", "Static(MAFI)", "Static(Forkliftable)");

    }

    @FXML
    protected void setDeclineReasons() {
        declinedReason.getItems().clear();
        declinedReason.getItems().addAll("No Service", "Freight", "Response", "Schedule", "Space", "Cargo Size", "Operational Restrictions", "Other (See Comments)");
    }

    @FXML
    protected void setRateUnit() {
        oftUnit.getItems().clear();
        oftUnit.getItems().addAll("W/M", "Unit", "Cubic Meter", "Metric Ton", "Short Ton", "Linear Foot", "Linear Meter", "Lane Meter", "MAFI", "40 Cubic Feet");

        bafUnit.getItems().clear();
        bafUnit.getItems().addAll("%", "W/M", "Unit", "Cubic Meter", "Metric Ton", "Short Ton", "Linear Foot", "Linear Meter", "Lane Meter", "MAFI", "40 Cubic Feet");

        ecaUnit.getItems().clear();
        ecaUnit.getItems().addAll("W/M", "Unit", "Cubic Meter", "Metric Ton", "Short Ton", "Linear Foot", "Linear Meter", "Lane Meter", "MAFI", "40 Cubic Feet");

        wfgUnit.getItems().clear();
        wfgUnit.getItems().addAll("W/M", "Unit", "Cubic Meter", "Metric Ton", "Short Ton", "Linear Foot", "Linear Meter", "Lane Meter", "MAFI", "40 Cubic Feet");

        thcUnit.getItems().clear();
        thcUnit.getItems().addAll("W/M", "Unit", "Cubic Meter", "Metric Ton", "Short Ton", "Linear Foot", "Linear Meter", "Lane Meter", "MAFI", "40 Cubic Feet");

        docUnit.getItems().clear();
        docUnit.getItems().addAll("B/L", "W/M", "Unit", "Cubic Meter", "Metric Ton", "Short Ton", "Linear Foot", "Linear Meter", "Lane Meter", "MAFI", "40 Cubic Feet");

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
