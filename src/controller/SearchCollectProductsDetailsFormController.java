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
import model.CollectProducts;
import view.tm.CollectProductTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-26
 **/
public class SearchCollectProductsDetailsFormController {


    public AnchorPane root13;
    public TableView tblSearchCollectProductDetails;
    public TableColumn colProductId;
    public TableColumn colQuantity;
    public Label lblCollectID;

    public void initialize() {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }

    public void loadAllData(String id) {

        lblCollectID.setText(id);

        try {
            ObservableList<CollectProductTM> tmList = FXCollections.observableArrayList();
            for (CollectProducts tempOrder : getAllCollectProducts(id)
            ) {
                tmList.add(new CollectProductTM(tempOrder.getProductId(), tempOrder.getQty()));
            }

            tblSearchCollectProductDetails.setItems(tmList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<CollectProducts> getAllCollectProducts(String collectId) throws SQLException {
        ArrayList<CollectProducts> collectProducts = new ArrayList();

        ResultSet resultSet = DBConnection.getInstance().getConnection().
                prepareStatement("select finalProductId, productQty from finalProductDetails where collectId = " +
                        "'" + collectId + "'").executeQuery();

        while (resultSet.next()) {
            collectProducts.add(
                    new CollectProducts(
                            resultSet.getString(1),
                            resultSet.getInt(2)
                    )
            );
        }
        return collectProducts;
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CollectProductsDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root13.getChildren().clear();
        root13.getChildren().add(load);
    }
}
