package com.navigation.drawer.activity.tools;

import android.util.Log;


import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.Medecin;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.Classes.Speciality;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pro on 19/02/2017.
 */

public class XMLParser {

    public static List<Pharmacie> XMLPharmaciesPARSER(String content){
        List<Pharmacie> myList = new ArrayList<Pharmacie>();
        String [] pharmacies = content.split(":::");
        for(String pharma : pharmacies){
            String [] attributs = pharma.split(";;;");
            if(attributs.length>=5) {
                Pharmacie pharmacie = new Pharmacie();
                pharmacie.setPharmacie(attributs[0].trim());
                pharmacie.setPharmacien(attributs[1].trim());
                pharmacie.setAdresse(attributs[2].trim());
                pharmacie.setSecteur(attributs[3].trim());
                pharmacie.setTel(attributs[4].trim());

                myList.add(pharmacie);
            }
        }
        return myList ;
    }

    public static List<Pharmacie> XMLPharmaciesGARDESPARSER(String content){
        String contentWithoutSlashes = "";
        for(int i=0;i<content.length();i++){
            if(content.charAt(i)!='\'' && content.charAt(i) != ' ' && content.charAt(i)!= '\n'){
                contentWithoutSlashes+=""+content.charAt(i);
            }
        }

        Log.d("haaa",contentWithoutSlashes);
        if(ListPharmacie.get("MOULAY DRISS") != null){
            Log.d("haaaaa moulay driss",contentWithoutSlashes.indexOf(ListPharmacie.get("MOULAY DRISS").getTel())+"");
        }
        if(ListPharmacie.get("MOULAY DRISS") != null){
            Log.d("haaaaa moulay driss",contentWithoutSlashes.indexOf(ListPharmacie.get("MOULAY DRISS").getTel())+"");
        }

        List<Pharmacie> myList = new ArrayList<Pharmacie>();
        for(int i = 0; i< ListPharmacie.pharmacieList.size(); i++){

            if(contentWithoutSlashes.contains(ListPharmacie.get(i).getTel()))
                if(ListPharmacie.get(i).getTel().length()==10)
                    myList.add(ListPharmacie.get(i));
                else
                    if(contentWithoutSlashes.contains(ListPharmacie.get(i).getPharmacie()))
                        myList.add(ListPharmacie.get(i));
            }
        return  myList;
    }

    public static List<Speciality> XMLSpecialitiesPARSER(String content){
        List<Speciality> myList = new ArrayList<Speciality>();
        String [] Medecins = content.split(":::");
        for(String med : Medecins){

            String [] attributs = med.split(";;;");
            Medecin medecin = new Medecin();
            if(attributs.length>=4) {
                medecin.setName(attributs[0]);
                medecin.setAdresse(attributs[1]);
                medecin.setTel(attributs[2]);
                medecin.setSpeciality(attributs[3]);
                boolean drap = true;
                for (int i = 0; drap && i < myList.size(); i++) {
                    if (myList.get(i).getName().equals(attributs[3])) {
                        drap = false;
                        myList.get(i).addMedecin(medecin);
                    }
                }
                if (drap) {
                    Speciality spec = new Speciality(attributs[3]);
                    spec.addMedecin(medecin);
                    myList.add(spec);

                }
            }
        }
        return myList;
    }
    public static MyGPS getGPS(String name){
        String URL = toURL(name);
        Log.d("haa liens ===> ","http://maps.google.com/maps/api/geocode/xml?address=FES%20"+URL+"%20&sensor=false");
        String content = HttpManager.getData("http://maps.google.com/maps/api/geocode/xml?address=FES%20"+URL+"%20&sensor=false");
        try{
            double x = 0 ;
            double y = 0 ;
            boolean isXFound = false ;
            boolean isYFound = false ;
            String currentTagName = "";
            MyGPS GPS = new MyGPS();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser  = factory.newPullParser();
            parser.setInput(new StringReader(content));
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        break;
                    case XmlPullParser.END_TAG:
                        currentTagName = "";
                        break;
                    case XmlPullParser.TEXT:
                        switch (currentTagName){
                            case "lat":
                                isXFound = true ;
                                GPS.setLaltitude(Double.parseDouble(parser.getText()));
                                break;
                            case "lng":
                                isYFound = true ;
                                GPS.setLongitude(Double.parseDouble(parser.getText()));
                                break;
                            default:
                                break;
                        }
                }
                if(isXFound && isYFound){
                    System.out.println(name +" "+GPS.getLaltitude()+","+GPS.getLongitude());
                    return GPS ;
                }

                eventType = parser.next();
            }
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null ;
        }
    }
    public static String toURL(String name){
        String URL = "";
        for(int i=0;i<name.length();i++){
            if(name.charAt(i)==' ')
                URL+="%20";
            else
                URL+=""+name.charAt(i);
        }
        return URL;
    }
}
