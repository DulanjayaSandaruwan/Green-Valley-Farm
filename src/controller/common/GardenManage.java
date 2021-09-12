package controller.common;

import model.Garden;
import model.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-12
 **/
public interface GardenManage {
    boolean saveGarden(Garden garden) throws SQLException;

    Garden searchGarden(String id) throws SQLException;

    boolean updateGarden(Garden garden) throws SQLException;

    boolean deleteGarden(String id) throws SQLException;

    ArrayList<Garden> selectAllGardens() throws SQLException;
}
