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
import model.SearchCollect;
import view.tm.SearchCollectTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-23
 **/
public class CollectProductsDetailsFormController {

    public AnchorPane root12;
    public TableView<SearchCollectTM> tblCollectDetails;
    public TableColumn colGardenID;
    public TableColumn colGardenLocation;
    public TableColumn colGardenType;
    public TableColumn colCollectID;
    public TableColumn colCollectDate;

    public void initialize() {

        colGardenID.setCellValueFactory(new PropertyValueFactory<>("gardenId"));
        colGardenLocation.setCellValueFactory(new PropertyValueFactory<>("gardenLocation"));
        colGardenType.setCellValueFactory(new PropertyValueFactory<>("gardenType"));
        colCollectID.setCellValueFactory(new PropertyValueFactory<>("collectId"));
        colCollectDate.setCellValueFactory(new PropertyValueFactory<>("collectDate"));

        try {
            loadAllCollectData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblCollectDetails.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        loadCollectProductDetailsUI(newValue.getCollectId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void loadCollectProductDetailsUI(String collectId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SearchCollectProductsDetailsForm.fxml"));
        Parent load = fxmlLoader.load();
        SearchCollectProductsDetailsFormController controller = fxmlLoader.getController();
        controller.loadAllData(collectId);
        root12.getChildren().clear();
        root12.getChildren().add(load);

    }

    private void loadAllCollectData() throws SQLException {
        ObservableList<SearchCollectTM> tmList = FXCollections.observableArrayList();
        for (SearchCollect tempCollect : getCollects()
        ) {
            tmList.add(
                    new SearchCollectTM(
                            tempCollect.getGardenId(),
                            tempCollect.getGardenLocation(),
                            tempCollect.getGardenType(),
                            tempCollect.getCollectId(),
                            tempCollect.getCollectDate()
                    )
            );
        }
        tblCollectDetails.setItems(tmList);
    }

    public ArrayList<SearchCollect> getCollects() throws SQLException {
        ArrayList<SearchCollect> list = new ArrayList();
        ResultSet resultSet = DBConnection.getInstance().getConnection()
                .prepareStatement(
                        "select g.gardenId, g.gardenLocation, g.gardenType, c.collectId, c.collectDate from garden g join collect c on c.gardenId = g.gardenID")
                .executeQuery();

        while (resultSet.next()) {
            list.add(
                    new SearchCollect(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return list;
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root12.getChildren().clear();
        root12.getChildren().add(load);
    }
}
