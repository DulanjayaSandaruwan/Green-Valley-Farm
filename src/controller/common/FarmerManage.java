package controller.common;

import model.Customer;
import model.Farmer;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-13
 **/
public interface FarmerManage {
    boolean saveFarmer(Farmer farmer) throws SQLException;

    Farmer searchFarmer(String id) throws SQLException;

    boolean updateFarmer(Farmer farmer) throws SQLException;

    boolean deleteFarmer(String id) throws SQLException;

    ArrayList<Farmer> selectAllFarmers() throws SQLException;
}
