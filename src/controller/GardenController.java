package controller;

import controller.common.GardenManage;
import db.DBConnection;
import model.Garden;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-12
 **/
public class GardenController implements GardenManage {

    public List<String> getGardenID() throws SQLException {
        ResultSet rst = DBConnection.getInstance().
                getConnection().prepareStatement("select * from garden").executeQuery();
        List<String> gardenId = new ArrayList<>();
        while (rst.next()) {
            gardenId.add(
                    rst.getString(1)
            );
        }
        return gardenId;
    }

    @Override
    public boolean saveGarden(Garden garden) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String query = "insert into garden values(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, garden.getGardenId());
        stm.setObject(2, garden.getGardenType());
        stm.setObject(3, garden.getGardenLocation());
        stm.setObject(4, garden.getExtendOfLand());
        stm.setObject(5, garden.getDescription());

        return stm.executeUpdate() > 0;
    }

    @Override
    public Garden searchGarden(String id) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("select * from garden where gardenId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Garden(
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
    public boolean updateGarden(Garden garden) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("update garden set gardenType=?, gardenLocation=?, extendOfLand=?, description=? where gardenId=?");
        stm.setObject(1, garden.getGardenType());
        stm.setObject(2, garden.getGardenLocation());
        stm.setObject(3, garden.getExtendOfLand());
        stm.setObject(4, garden.getDescription());
        stm.setObject(5, garden.getGardenId());

        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteGarden(String id) throws SQLException {
        return DBConnection.getInstance().getConnection().
                prepareStatement("delete from garden where gardenId='" + id + "'").executeUpdate() > 0;
    }

    @Override
    public ArrayList<Garden> selectAllGardens() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select * from garden");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Garden> gardens = new ArrayList<>();
        while (resultSet.next()) {
            gardens.add(new Garden(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return gardens;
    }
}
