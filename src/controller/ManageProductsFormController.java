package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.Products;
import org.controlsfx.control.Notifications;
import util.ValidationUtil;
import view.tm.ProductsTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-08
 **/
public class ManageProductsFormController {
    public JFXButton btnSaveProducts;
    public TableView <ProductsTM> tblProductsDetails;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colProductType;
    public TableColumn colQtyOnHand;
    public TextField txtProductId;
    public TableColumn colUnitPrice;
    public TextField txtProductUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtProductsName;
    public TextField txtProductsType;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^[A-z ]{5,}$");
    Pattern productTypePattern = Pattern.compile("^[A-z]{5,7}$");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9 ]+$");
    Pattern unitPricePattern = Pattern.compile("^[0-9 ]{2,}[.][0-9]{2}$");

    ObservableList<ProductsTM> observableList = FXCollections.observableArrayList();

    public void initialize(){

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            setItemValuesToTable(new ProductsController().selectAllProducts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        storeValidations();

    }

    public void txtGetProductIdOnAction(ActionEvent actionEvent) throws SQLException {
        String productId = txtProductId.getText();

        Products products = new ProductsController().searchProducts(productId);
        if (productId == null) {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Empty Results Set , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        } else {
            setData(products);
        }
    }

    void setData(Products products) {
        txtProductId.setText(products.getProductId());
        txtProductsName.setText(products.getProductName());
        txtProductsType.setText(products.getProductType());
        txtQtyOnHand.setText(String.valueOf(products.getQtyOnHand()));
        txtProductUnitPrice.setText(String.valueOf(products.getUnitPrice()));
    }

    private void setItemValuesToTable(ArrayList<Products> products) {
        products.forEach(e -> {
                    observableList.add(new ProductsTM(e.getProductId(), e.getProductName(), e.getProductType(), e.getQtyOnHand(), e.getUnitPrice()));
                }
        );
        tblProductsDetails.setItems(observableList);
    }

    public void btnProductIdOnAction(ActionEvent actionEvent) {

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select finalProductId from finalProduct order by finalProductId desc limit 1");

            boolean next = resultSet.next();

            if (next) {

                String oldId = resultSet.getString(1);

                String id = oldId.substring(1, 4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if (intId < 10) {
                    txtProductId.setText("P00" + intId);
                } else if (intId < 100) {
                    txtProductId.setText("P0" + intId);
                } else {
                    txtProductId.setText("P" + intId);
                }
            } else {
                txtProductId.setText("P001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearForms();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        if (new ProductsController().deleteProducts(txtProductId.getText())) {
            Image image = new Image("/assests/images/pass.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Successfully Deleted !");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();

            clearForms();
            tblProductsDetails.getItems().clear();
            setItemValuesToTable(new ProductsController().selectAllProducts());
        } else {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        Products products = new Products(
                txtProductId.getText(),
                txtProductsName.getText(),
                txtProductsType.getText(),
                Integer.parseInt("".equals(txtQtyOnHand.getText()) ? "0" : txtQtyOnHand.getText()),
                Double.parseDouble("".equals(txtProductUnitPrice.getText()) ? "0" : txtProductUnitPrice.getText())
        );

        if (!products.getProductId().isEmpty() && !products.getProductName().isEmpty() && !products.getProductType().isEmpty()) {
            if (new ProductsController().updateProducts(products)) {
                try {

                    Image image = new Image("/assests/images/pass.png");
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Successfully Updated !");
                    notifications.title("Success Message");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_CENTER);
                    notifications.darkStyle();
                    notifications.show();


                    clearForms();
                    tblProductsDetails.getItems().clear();
                    setItemValuesToTable(new ProductsController().selectAllProducts());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }else {
                Image image = new Image("/assests/images/fail.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Something Went Wrong , Try Again !");
                notifications.title("Failed Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();
            }
        } else {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        Products products = new Products(
                txtProductId.getText(),
                txtProductsName.getText(),
                txtProductsType.getText(),
                Integer.parseInt("".equals(txtQtyOnHand.getText()) ? "0" : txtQtyOnHand.getText()),
                Double.parseDouble("".equals(txtProductUnitPrice.getText()) ? "0" : txtProductUnitPrice.getText())
        );
        if (!products.getProductId().isEmpty() && !products.getProductName().isEmpty() && !products.getProductType().isEmpty()) {

            if (new ProductsController().saveProduct(products)) {

                Image image = new Image("/assests/images/pass.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Successfully Saved !");
                notifications.title("Success Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();

                clearForms();
                tblProductsDetails.getItems().clear();
                setItemValuesToTable(new ProductsController().selectAllProducts());
            }else {
                Image image = new Image("/assests/images/fail.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Duplicate Entry, Try Again !");
                notifications.title("Failed Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();
            }

        } else {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        }
    }

    private void clearForms() {
        txtProductId.setText("");
        txtProductsName.setText("");
        txtProductsType.setText("");
        txtQtyOnHand.setText("");
        txtProductUnitPrice.setText("");
    }

    public void tblProductsDetailsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblProductsDetails.getSelectionModel().getSelectedItem() != null) {
            ProductsTM productsTM = tblProductsDetails.getSelectionModel().getSelectedItem();
            txtProductId.setText(productsTM.getProductId());
            txtProductsName.setText(productsTM.getProductName());
            txtProductsType.setText(productsTM.getProductType());
            txtQtyOnHand.setText(String.valueOf(productsTM.getQtyOnHand()));
            txtProductUnitPrice.setText(String.valueOf(productsTM.getUnitPrice()));

        }
    }

    private void storeValidations() {
        btnSaveProducts.setDisable(true);
        map.put(txtProductsName, namePattern);
        map.put(txtProductsType, productTypePattern);
        map.put(txtQtyOnHand, qtyOnHandPattern);
        map.put(txtProductUnitPrice, unitPricePattern);

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSaveProducts);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                Image image = new Image("/assests/images/pass.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Successfully Saved !");
                notifications.title("Success Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();
            }
        }
    }

}
