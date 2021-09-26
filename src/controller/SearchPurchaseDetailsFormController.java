package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.PurchaseItemDetails;
import view.tm.PurchaseDetailsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-26
 **/
public class SearchPurchaseDetailsFormController {
    public AnchorPane root15;
    public TableView<PurchaseDetailsTM> tblSearchPurchaseDetails;
    public TableColumn colItemCode;
    public TableColumn colQuantity;
    public Label lblPurchaseId;

    public void initialize() {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("supItemCode"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("buyingQty"));

    }

    public void loadAllData(String id) {

        lblPurchaseId.setText(id);

        try {
            ObservableList<PurchaseDetailsTM> tmList = FXCollections.observableArrayList();
            for (PurchaseItemDetails tempOrder : getAllPurchaseProducts(id)
            ) {
                tmList.add(new PurchaseDetailsTM(tempOrder.getSupItemCode(), tempOrder.getBuyingQty()));
            }

            tblSearchPurchaseDetails.setItems(tmList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<PurchaseItemDetails> getAllPurchaseProducts(String buyingId) throws SQLException {

        ArrayList<PurchaseItemDetails> purchaseItemDetails = new ArrayList();

        ResultSet resultSet = DBConnection.getInstance().getConnection().
                prepareStatement("select supItemCode, buyingQty from buyingDetails where buyingId = " +
                        "'" + buyingId + "'").executeQuery();

        while (resultSet.next()) {
            purchaseItemDetails.add(
                    new PurchaseItemDetails(
                            resultSet.getString(1),
                            resultSet.getString(2)
                    )
            );
        }
        return purchaseItemDetails;
    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PurchaseDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root15.getChildren().clear();
        root15.getChildren().add(load);
    }
}
