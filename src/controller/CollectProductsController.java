package controller;

import db.DBConnection;
import model.Collect;
import model.FinalProductDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-18
 **/
public class CollectProductsController {
    public String getCollectId() throws SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select collectId from collect order by collectId desc limit 1").executeQuery();
        if (rst.next()) {
            String oldId = rst.getString(1);

            String id = oldId.substring(1, 4);

            int intId = Integer.parseInt(id);

            intId = intId + 1;

            if (intId <= 9) {
                return "S00" + intId;
            } else if (intId < 99) {
                return "S0" + intId;
            } else {
                return "S" + intId;
            }
        } else {
            return "S001";
        }
    }

    public boolean collectProducts(Collect collect) {

        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();

            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into collect values (?, ?, ?)");
            preparedStatement.setObject(1, collect.getCollectId());
            preparedStatement.setObject(2, collect.getCollectDate());
            preparedStatement.setObject(3, collect.getGardenId());

            if (preparedStatement.executeUpdate() > 0) {
                if (saveCollectDetails(collect.getCollectId(), collect.getFinalProductDetails())) {

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

    private boolean saveCollectDetails(String gardenId, ArrayList<FinalProductDetails> finalProductDetails) throws SQLException {
        for (FinalProductDetails temp : finalProductDetails
        ) {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(
                    "insert into finalProductDetails values (?, ?, ?)");

            preparedStatement.setObject(1, temp.getFinalProductId());
            preparedStatement.setObject(2, temp.getCollectId());
            preparedStatement.setObject(3, temp.getProductQty());


            if (preparedStatement.executeUpdate() > 0) {
                if (updateQty(temp.getFinalProductId(), temp.getProductQty())) {

                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String finalProductId, int qty) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(
                "update finalProduct set qtyOnHand =(qtyOnHand + ?) where finalProductId = ?");
        preparedStatement.setInt(1, qty);
        preparedStatement.setString(2, finalProductId);
        return preparedStatement.executeUpdate() > 0;
    }


}
