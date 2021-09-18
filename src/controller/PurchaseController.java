package controller;

import db.DBConnection;
import model.Purchase;
import model.PurchaseDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-18
 **/
public class PurchaseController {

    public String getBuyId() throws SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select buyingId from buy order by buyingId desc limit 1").executeQuery();
        if (rst.next()) {
            String oldId = rst.getString(1);

                String id = oldId.substring(1, 4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if (intId <= 9) {
                    return "B00" + intId;
                } else if (intId < 99) {
                    return "B0" + intId;
                } else {
                    return "B" + intId;
                }
        } else {
            return "B001";
        }
    }

    public boolean purchase(Purchase purchase) {

        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();

            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into buy values (?, ?, ?, ?)");
            preparedStatement.setObject(1, purchase.getBuyingId());
            preparedStatement.setObject(2, purchase.getSupId());
            preparedStatement.setObject(3, purchase.getBuyingDate());
            preparedStatement.setObject(4, purchase.getBuyingCost());

            if (preparedStatement.executeUpdate() > 0) {
                if (saveBuyingDetails(purchase.getBuyingId(), purchase.getPurchaseDetails())) {

                    connection.commit();

                    return true;

                } else {

                    connection.rollback();

                    return false;
                }

            } else {

                connection.rollback();

                return false;
            }

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    private boolean saveBuyingDetails(String buyingId, ArrayList<PurchaseDetails> purchaseDetails) throws SQLException {
        for (PurchaseDetails temp : purchaseDetails
        ) {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(
                    "insert into buyingDetails values (?, ?, ?)");

            preparedStatement.setObject(1, temp.getBuyingId());
            preparedStatement.setObject(2, temp.getSupItemCode());
            preparedStatement.setObject(3, temp.getBuyingQty());

        }
        return true;
    }

}
