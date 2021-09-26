package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.SearchPurchase;
import view.tm.SearchPurchaseTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-26
 **/
public class PurchaseDetailsFormController {
    public AnchorPane root14;
    public TableView <SearchPurchaseTM> tblPurchaseDetails;
    public TableColumn colSupplierId;
    public TableColumn colSupplierName;
    public TableColumn colPurchaseId;
    public TableColumn colPurchaseDate;
    public TableColumn colTotalCost;

    public void initialize() {

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colPurchaseId.setCellValueFactory(new PropertyValueFactory<>("buyId"));
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("buyingDate"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("buyingCost"));

        try {
            loadAllPurchaseData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblPurchaseDetails.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        loadPurchaseProductDetailsUI(newValue.getBuyId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void loadPurchaseProductDetailsUI(String buyId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SearchPurchaseDetailsForm.fxml"));
        Parent load = fxmlLoader.load();
        SearchPurchaseDetailsFormController controller = fxmlLoader.getController();
        controller.loadAllData(buyId);
        root14.getChildren().clear();
        root14.getChildren().add(load);

    }

    private void loadAllPurchaseData() throws SQLException {
        ObservableList<SearchPurchaseTM> tmList = FXCollections.observableArrayList();
        for (SearchPurchase tempPurchase : getAllPurchase()
        ) {
            tmList.add(
                    new SearchPurchaseTM(
                            tempPurchase.getSupplierId(),
                            tempPurchase.getSupplierName(),
                            tempPurchase.getBuyId(),
                            tempPurchase.getBuyingDate(),
                            tempPurchase.getBuyingCost()
                    )
            );
        }
        tblPurchaseDetails.setItems(tmList);
    }

    public ArrayList<SearchPurchase> getAllPurchase() throws SQLException {
        ArrayList<SearchPurchase> list = new ArrayList();
        ResultSet resultSet = DBConnection.getInstance().getConnection()
                .prepareStatement(
                        "select s.supId, s.supName, b.buyingId, b.buyingDate, b.buyingCost from supplier s join buy b on b.supId = s.supID")
                .executeQuery();

        while (resultSet.next()) {
            list.add(
                    new SearchPurchase(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5)
                    )
            );
        }
        return list;

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root14.getChildren().clear();
        root14.getChildren().add(load);
    }
}
