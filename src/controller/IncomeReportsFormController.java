package controller;

import db.DBConnection;
import javafx.scene.control.Label;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-25
 **/
public class IncomeReportsFormController {
    public Label lblTodayIncome;
    public Label lblThisMonthIncome;
    public Label lblThisYearIncome;
    public double todayIncome;
    public double thisMonthIncome;
    public double thisYearIncome;

    public void initialize() {
        getIncome();
    }

    private void getIncome() {
        String year, month, day;
        SimpleDateFormat simpleDateFormat;
        Date date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy");
        year = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        month = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        day = simpleDateFormat.format(date);

        todayIncome = 0;
        thisMonthIncome = 0;
        thisYearIncome = 0;
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("select * from `order` where orderDate like '" + year + "%'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                thisYearIncome += Double.parseDouble(resultSet.getString("orderCost"));
            }
            lblThisYearIncome.setText("" + thisYearIncome);

            preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("select * from `order` where orderDate like '" + month + "%'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                thisMonthIncome += Double.parseDouble(resultSet.getString("orderCost"));
            }
            lblThisMonthIncome.setText("" + thisMonthIncome);

            preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("select * from `order` where orderDate= '" + day + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                todayIncome += Double.parseDouble(resultSet.getString("orderCost"));
            }
            lblTodayIncome.setText("" + todayIncome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
