package com.navigation.drawer.activity.Classes;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Pro on 09/03/2017.
 */

public class Clinique {
    private String name ;
    private String categorie;
    private String adresse ;
    private String tel ;
    private HashMap<String,Vector<String>> informations ;

    public Clinique(String name, String categorie, String adresse, String tel, HashMap<String, Vector<String>> informations) {
        this.name = name;
        this.categorie = categorie;
        this.adresse = adresse;
        this.tel = tel;
        this.informations = informations;
    }

    public Clinique() {
        informations = new HashMap<String,Vector<String>>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public HashMap<String, Vector<String>> getInformations() {
        return informations;
    }

    public void setInformations(HashMap<String, Vector<String>> informations) {
        this.informations = informations;
    }
}
