package main.java.DAO;

import main.java.Interfaces.AdresDAO;
import main.java.Model.Adres;
import main.java.Model.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO
{
    private Connection conn;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {

        System.out.println("save is being called");

        String SQL;
        try
        {
            SQL =   "INSERT INTO adres" +
                    "(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) " +
                    "VALUES(?,?,?,?,?,?)";
        }
        catch(Exception exception)
        {
            System.out.println("Er is iets misgegaan tijdens aanmaken van de SQL query");
            return false;
        }


        System.out.println("The query to be executed: " + SQL);

        System.out.println("Executing PreparedStatement");

        try (PreparedStatement preparedStatement = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6, adres.getReiziger_id());


            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("affectedRows = " + affectedRows);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;    }

    @Override
    public boolean update(Adres adres) {
        System.out.println("update is being called");

        String SQL;
        try
        {
            SQL =   "UPDATE adres " +
                    "SET adres_id = ?, postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? " +
                    "WHERE adres_id = " + adres.getId();
        }
        catch(Exception exception)
        {
            System.out.println("Er is iets misgegaan tijdens aanmaken van de SQL query");
            return false;
        }

        System.out.println("The query to be executed: " + SQL);

        System.out.println("Executing PreparedStatement");

        try (PreparedStatement preparedStatement = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6, adres.getReiziger_id());


            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("affectedRows = " + affectedRows);

        } catch (SQLException ex) {
            System.out.println("Error tijdens uitvoeren van PreparedStatement");
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        System.out.println("delete is being called");

        String SQL;
        try
        {
            SQL =   "DELETE FROM adres " +
                    "WHERE adres_id = " + adres.getId();
        }
        catch(Exception exception)
        {
            System.out.println("Er is iets misgegaan tijdens aanmaken van de SQL query");
            return false;
        }

        System.out.println("The query to be executed: " + SQL);

        System.out.println("Executing PreparedStatement");

        try (PreparedStatement preparedStatement = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("affectedRows = " + affectedRows);

        } catch (SQLException ex) {
            System.out.println("Error tijdens uitvoeren van PreparedStatement");
            System.out.println(ex.getMessage());
            return false;
        }
        return true;    }


    @Override
    public Adres findById(int id) {
        System.out.println("findById is being called");

        String SQL;
        try
        {
            SQL =   "SELECT * FROM adres " +
                    "WHERE adres_id = " + id;
        }
        catch(Exception exception)
        {
            System.out.println("Er is iets misgegaan tijdens aanmaken van de SQL query");
            return null;
        }

        System.out.println("The query to be executed: " + SQL);

        System.out.println("Executing PreparedStatement");

        try (PreparedStatement preparedStatement = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            boolean result = preparedStatement.execute();
            System.out.println("PreparedStatement succesfull executed? : " + result);

            if (result) {
                try (ResultSet rs = preparedStatement.getResultSet()) {
                    if (rs.next()) {
                        return new Adres(
                                rs.getInt("adres_id"),
                                rs.getString("postcode"),
                                rs.getString("huisnummer"),
                                rs.getString("straat"),
                                rs.getString("woonplaats"),
                                rs.getInt("reiziger_id")

                        );
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error tijdens uitvoeren van PreparedStatement");
            System.out.println(ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        System.out.println("findByReiziger is being called");

        String SQL;
        try
        {
            SQL =   "SELECT * FROM adres " +
                    "WHERE reiziger_id = " + reiziger.getId();
        }
        catch(Exception exception)
        {
            System.out.println("Er is iets misgegaan tijdens aanmaken van de SQL query");
            return null;
        }

        System.out.println("The query to be executed: " + SQL);

        System.out.println("Executing PreparedStatement");

        try (PreparedStatement preparedStatement = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            boolean result = preparedStatement.execute();
            System.out.println("PreparedStatement succesfull executed? : " + result);

            if (result) {
                try (ResultSet rs = preparedStatement.getResultSet()) {
                    if (rs.next()) {
                        return new Adres(
                                rs.getInt("adres_id"),
                                rs.getString("postcode"),
                                rs.getString("huisnummer"),
                                rs.getString("straat"),
                                rs.getString("woonplaats"),
                                rs.getInt("reiziger_id")
                                );
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error tijdens uitvoeren van PreparedStatement");
            System.out.println(ex.getMessage());
            return null;
        }
        return null;    }

    @Override
    public List<Adres> findAll() {
        System.out.println("findAll is being called");

        String SQL;
        try
        {
            SQL =   "SELECT * FROM adres";
        }
        catch(Exception exception)
        {
            System.out.println("Er is iets misgegaan tijdens aanmaken van de SQL query");
            return null;
        }

        System.out.println("The query to be executed: " + SQL);

        System.out.println("Executing PreparedStatement");

        try (PreparedStatement preparedStatement = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            boolean result = preparedStatement.execute();
            System.out.println("PreparedStatement succesfull executed? : " + result);

            List<Adres> resultaten = new ArrayList<>();
            if (result) {
                System.out.println("resultaat gevonden!");

                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        resultaten.add(new Adres(
                                resultSet.getInt("adres_id"),
                                resultSet.getString("postcode"),
                                resultSet.getString("huisnummer"),
                                resultSet.getString("straat"),
                                resultSet.getString("woonplaats"),
                                resultSet.getInt("reiziger_id")
                        ));
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return resultaten;

        } catch (SQLException ex)
        {
            System.out.println("Error tijdens uitvoeren van de PreparedStatement");
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
