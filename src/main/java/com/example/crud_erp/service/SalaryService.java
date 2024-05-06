package com.example.crud_erp.service;

import com.example.crud_erp.model.Salary;
import com.example.crud_erp.utils.DBConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryService implements IService<Salary> {
    public static Connection connection;
    public SalaryService(){
        connection = DBConnexion.getInstance().getConnection();
    }



    @Override
    public void create(Salary salary) {
        String sql = "insert into erp.emppaiment ( ref, name, department, paimentdate, paiment, tax, deduction, net) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {  //requête dynamiques précompilees
            ps.setInt(1, salary.getRef());
            ps.setString(2, salary.getName());
            ps.setString(3, salary.getDepartment());
            ps.setString(4, salary.getPaimentdate());
            ps.setInt(5, salary.getPaiment());
            // Calculate tax, deduction, and net based on payment
            double tax = salary.getPaiment() * 0.1; // Assume tax is 10% of payment
            double deduction = salary.getPaiment() * 0.05; // Assume deduction is 5% of payment
            double net = salary.getPaiment() - (tax + deduction);
            ps.setDouble(6, tax); // Use setDouble for tax
            ps.setDouble(7,  deduction); // Use setDouble for deduction
            ps.setDouble(8,net);// Use setDouble for net

            ps.executeUpdate();
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void update(Salary salary) throws SQLException {
        String sql = "update erp.emppaiment set ref = ?, name = ?, department = ?, paimentdate = ?, paiment = ?, tax = ?, deduction = ?, net = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, salary.getRef());
        ps.setString(2, salary.getName());
        ps.setString(3, salary.getDepartment());
        ps.setString(4, salary.getPaimentdate());
        ps.setInt(5, salary.getPaiment());
        // Calculate tax, deduction, and net based on payment
        double tax = salary.getPaiment() * 0.1; // Assume tax is 10% of payment
        double deduction = salary.getPaiment() * 0.05; // Assume deduction is 5% of payment
        double net = salary.getPaiment() - (tax + deduction);
        ps.setDouble(6, tax); // Use setDouble for tax
        ps.setDouble(7, deduction); // Use setDouble for deduction
        ps.setDouble(8, net); // Use setDouble for net
        ps.setInt(9,salary.getId());
        ps.executeUpdate();
        System.out.println("Salary updated");
        System.out.println(salary.getId());
    }

    @Override
    public void delete(int id) {
        try {
            String sql ="DELETE FROM `emppaiment`  WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println(id);
            System.out.println("Salary supprimée avec succées");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Salary> read() throws SQLException {
        String sql = "select * from erp.emppaiment";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Salary> salaryList = new ArrayList<>();
        while (rs.next()) {
            // Create a new Salary object for each row in the result set
            Salary s = new Salary();
             s.setId(rs.getInt("id"));
            s.setRef(rs.getInt("ref"));
            s.setName(rs.getString("name"));
            s.setDepartment(rs.getString("department"));
            s.setPaimentdate(rs.getString("paimentdate"));
            s.setPaiment(rs.getInt("paiment"));

            s.setTax(rs.getDouble("tax")); // Use getDouble for Tax
            s.setDeduction(rs.getDouble("deduction")); // Use getDouble for Deduction
            s.setNet(rs.getDouble("net")); // Use getDouble for Tax
            // Add salary object to list
            // Add the Salary object to the list
            salaryList.add(s);
        }
        return salaryList;
    }


    public List<Salary> searchByCode(String code) throws SQLException {
        String sql = "SELECT * FROM erp.emppaiment WHERE ref = ? OR name = ? OR department = ? OR paimentdate = ? OR paiment = ? OR tax = ? OR deduction = ? OR net = ?";
        List<Salary> salaryList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 1; i <= 8; i++) {
                ps.setString(i, code);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Salary s = new Salary();
                s.setId(rs.getInt("id"));
                s.setRef(rs.getInt("ref"));
                s.setName(rs.getString("name"));
                s.setDepartment(rs.getString("department"));
                s.setPaimentdate(rs.getString("paimentdate"));
                s.setPaiment(rs.getInt("paiment"));
                s.setTax(rs.getDouble("tax"));
                s.setDeduction(rs.getDouble("deduction"));
                s.setNet(rs.getDouble("net"));
                salaryList.add(s);
            }
        }
        return salaryList;
    }


}

