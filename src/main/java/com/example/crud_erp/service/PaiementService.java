package com.example.crud_erp.service;

import com.example.crud_erp.model.Paiement;
import com.example.crud_erp.utils.DBConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementService implements IService <Paiement>{

    private static Connection connection;
    public PaiementService(){connection = DBConnexion.getInstance().getConnection();}



    @Override
    public void create(Paiement paiement) throws SQLException {
        String sql = "insert into overview ( code, date,mail, name, total, grand, due, method) values (?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {  //requête dynamiques précompilees
            ps.setString(1, paiement.getCode());
            ps.setString(2, paiement.getDate());
            ps.setString(3, paiement.getMail());
            ps.setString(4, paiement.getName());
            ps.setInt(5, paiement.getTotal());
            ps.setDouble(6, paiement.getGrand());
            ps.setDouble(7, paiement.getDue());
            ps.setString(8, paiement.getMethod());
            ps.executeUpdate();
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Paiement paiement) throws SQLException {
        String sql = "update overview set code = ?, date = ?,mail= ?, name = ?, total = ?, grand = ?, due = ?, method = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, paiement.getCode());
        ps.setString(2, paiement.getDate());
        ps.setString(3, paiement.getMail());
        ps.setString(4, paiement.getName());
        ps.setInt(5, paiement.getTotal());
        ps.setDouble(6, paiement.getGrand());
        ps.setDouble(7, paiement.getDue());
        ps.setString(8, paiement.getMethod());
        ps.setInt(9,paiement.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {

        String sql = "delete from overview where id = ?";
        PreparedStatement ps =connection.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
        System.out.println(id);
    }

    @Override
    public List<Paiement> read() throws SQLException {
        String sql = "select * from overview";  //hadhi requête SQL
        Statement statement = connection.createStatement();  //3malna connextion bel base de donne
        ResultSet rs = statement.executeQuery(sql);  //exécution taa requête sql w nhotouha fi rs (kan bech naamlou ajout wala modif wala sup nhotou executeUpdate fi blaset executeQuery)
        List <Paiement> paiement = new ArrayList<>();
        while (rs.next()){
            Paiement p = new Paiement();
            p.setId(rs.getInt("id"));
            p.setCode(rs.getString("code"));
            p.setDate(rs.getString("date"));
            p.setMail(rs.getString("mail"));
            p.setName(rs.getString("name"));
            p.setTotal(rs.getInt("total"));
            p.setGrand(rs.getInt("grand"));
            p.setDue(rs.getInt("due"));
            p.setMethod(rs.getString("method"));
            //tw bech nzidou el user fi liste
            paiement.add(p);
        }
        return paiement;
    }
    public List<Paiement> searchByCode(String code) throws SQLException {
        String sql = "SELECT * FROM overview WHERE id = ? OR total = ? OR grand = ? OR due = ? OR code = ? OR date = ? OR name = ? OR method = ? OR mail = ?";
        List<Paiement> paiementList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 1; i <= 9; i++) {
                ps.setString(i, code);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Paiement p = new Paiement();
                p.setId(rs.getInt("id"));
                p.setTotal(rs.getInt("total"));
                p.setGrand(rs.getDouble("grand"));
                p.setDue(rs.getDouble("due"));
                p.setCode(rs.getString("code"));
                p.setDate(rs.getString("date"));
                p.setName(rs.getString("name"));
                p.setMethod(rs.getString("method"));
                p.setMail(rs.getString("mail"));
                paiementList.add(p);
            }
        }
        return paiementList;
    }

}

