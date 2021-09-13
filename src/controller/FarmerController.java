package controller;

import controller.common.FarmerManage;
import db.DBConnection;
import model.Farmer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-13
 **/
public class FarmerController implements FarmerManage {

    @Override
    public boolean saveFarmer(Farmer farmer) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String query = "insert into farmer values(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, farmer.getFarmerId());
        stm.setObject(2, farmer.getFarmerName());
        stm.setObject(3, farmer.getFarmerAddress());
        stm.setObject(4, farmer.getFarmerContact());
        stm.setObject(5, farmer.getGardenId());

        return stm.executeUpdate() > 0;
    }

    @Override
    public Farmer searchFarmer(String id) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("select * from farmer where farmerId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Farmer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }

    @Override
    public boolean updateFarmer(Farmer farmer) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("update farmer set farmerName=?, farmerAddress=?, farmerContact=?, gardenId=? where farmerId=?");
        stm.setObject(1, farmer.getFarmerName());
        stm.setObject(2, farmer.getFarmerAddress());
        stm.setObject(3, farmer.getFarmerContact());
        stm.setObject(4, farmer.getGardenId());
        stm.setObject(5, farmer.getFarmerId());

        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteFarmer(String id) throws SQLException {
        return DBConnection.getInstance().getConnection().
                prepareStatement("delete from farmer where farmerId='" + id + "'").executeUpdate() > 0;
    }

    @Override
    public ArrayList<Farmer> selectAllFarmers() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select * from farmer");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Farmer> farmers = new ArrayList<>();
        while (resultSet.next()) {
            farmers.add(new Farmer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return farmers;
    }
}
