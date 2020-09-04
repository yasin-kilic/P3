package main.java.Interfaces;

import main.java.Model.Adres;
import main.java.Model.Reiziger;

import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);
    Adres findByReiziger(Reiziger reiziger);
    List<Adres> findAll();
    Adres findById(int id);
    }
