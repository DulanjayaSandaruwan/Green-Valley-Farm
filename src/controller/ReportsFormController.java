package controller;

import db.DBConnection;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-08
 **/

public class ReportsFormController {
    public AnchorPane root8;
    public AnchorPane paneReportsView;

    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root8.getChildren().clear();
        root8.getChildren().add(load);
    }

    public void btnProductDetails(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream(""));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

        SwingNode swingNode = new SwingNode();
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
        JRViewer viewer = new JRViewer(jasperPrint);
        SwingUtilities.invokeLater(() -> swingNode.setContent(viewer));
        paneReportsView.getChildren().add(swingNode);

    }

    public void btnCollectDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CollectProductsDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root8.getChildren().clear();
        root8.getChildren().add(load);
    }
}
