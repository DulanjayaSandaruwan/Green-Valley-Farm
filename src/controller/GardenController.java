package controller;

import controller.common.GardenManage;
import model.Garden;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-12
 **/
public class GardenController implements GardenManage {

    @Override
    public boolean saveGarden(Garden garden) throws SQLException {
        return false;
    }

    @Override
    public Garden searchGarden(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean updateGarden(Garden garden) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteGarden(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Garden> selectAllGardens() throws SQLException {
        return null;
    }
}
