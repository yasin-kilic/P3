package main.java;

import main.java.DAO.AdresDAOPsql;
import main.java.DAO.ReizigerDAOPsql;
import main.java.Interfaces.AdresDAO;
import main.java.Interfaces.ReizigerDAO;
import main.java.Model.Adres;
import main.java.Model.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main
{
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();
        var reizerDAO = new ReizigerDAOPsql(connection);
        testReizigerDAO(reizerDAO);

        var adresDAO = new AdresDAOPsql(connection);
        testAdresDAO(adresDAO);
        closeConnection();
    }

    private static void getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=windows16";
        connection = DriverManager.getConnection(url);
    }

    private static void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();


        // verwijderd Reiziger uit database.
        Reiziger sietskeVoorDelete = rdao.findById(sietske.getId());
        System.out.println("Dietske voor delete: ");
        System.out.println(sietskeVoorDelete != null ? "Dietske gevonden in database!" : "Dietske niet gevonden in database!");
        rdao.delete(sietske);
        Reiziger sietskeNaDelete = rdao.findById(sietske.getId());
        System.out.println("Dietske na delete: ");
        System.out.println(sietskeNaDelete != null ? "Dietske gevonden in database!" : "Dietske niet gevonden in database!");

        System.out.println();
        // Maak een nieuwe reiziger aan en persisteer deze in de database
        var reizigers11 = rdao.findAll();

        System.out.print("[Test]  " + reizigers11.size() + " reizigers, voor ReizigerDAO.save() ");
        rdao.save(sietske);
        var reizigers22 = rdao.findAll();
        System.out.print("[Test]  " + reizigers22.size() + " reizigers, na ReizigerDAO.save() ");

        // Haal alle reizigers op uit de database bij geboortedatum
        List<Reiziger> reizigers2 = rdao.findByGbdatum("1981-03-14");
        System.out.println("[Test] ReizigerDAO.findByGbdatum('1981-03-14') geeft de volgende reizigers:");
        for (Reiziger r : reizigers2) {
            System.out.println(r);
        }
        System.out.println();


        // Haal reiziger op uit de database bij id
        Reiziger reiziger = rdao.findById(3);
        System.out.println("[Test] ReizigerDAO.findById('3') geeft de volgende reiziger:");
        System.out.println(reiziger);
        System.out.println();

        // update de achternaam van een reiziger
        sietske.setAchternaam("achternaam voor update");
        rdao.update(sietske);
        Reiziger sietskeVoorUpdate = rdao.findById(sietske.getId());
        System.out.println("sietske voor update: " + sietskeVoorUpdate);

        sietske.setAchternaam("achternaam na update!!!");
        rdao.update(sietske);
        Reiziger dietskeNaUpdate = rdao.findById(sietske.getId());

        System.out.println("sietske na update: " + dietskeNaUpdate);
        System.out.println();

    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle aderessen op uit de database
        List<Adres> adressen = adao.findAll();

        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();


        // Maak een nieuwe adres aan en persisteer deze in de database

        Adres adres = new Adres(100, "234FG", "234", "daanderl", "wefwef", 77);
        System.out.print("[Test] Aantal " + adressen.size() + " adressen, voor AdressenDAO.save() ");
        adao.save(adres);
        adressen = adao.findAll();
        System.out.print("[Test] Aantal " + adressen.size() + " adressen, na AdressenDAO.save() ");


        // Haal adres op uit de database bij reiziger id
        Adres adres2 = adao.findByReiziger(
                new Reiziger(3, "", "", "", Date.valueOf("1999-02-02")));

        System.out.println("[Test] AdresDAO.findByReiziger('3') geeft de volgende adres:");
        System.out.println(adres2);
        System.out.println();

        // update de postcode van een adres
        adres2.setPostcode("voorUpdate");
        adao.update(adres2);
        Adres adres2VoorUpdate = adao.findById(adres.getId());
        System.out.println("voorUpdate: " + adres2VoorUpdate);

        adres2.setPostcode("naUpdate");
        adao.update(adres2);
        Adres adresNaUpdate = adao.findById(adres2.getId());

        System.out.println("naupdate: " + adresNaUpdate);
        System.out.println();

        // verwijderd adres uit database.
        Adres adresVoorDelete = adao.findById(adres.getId());
        System.out.println("adres voor delete: ");
        System.out.println(adresVoorDelete != null ? "adres gevonden in database!" : "adres niet gevonden in database!");
        adao.delete(adresVoorDelete);
        Adres adresNaDelete = adao.findById(adres.getId());
        System.out.println("adres na delete: ");
        System.out.println(adresNaDelete != null ? "adres gevonden in database!" : "adres niet gevonden in database!");
    }
}