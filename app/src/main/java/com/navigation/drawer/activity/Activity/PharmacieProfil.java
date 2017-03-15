package com.navigation.drawer.activity.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.R;
import com.navigation.drawer.activity.tools.MyGPS;
import com.navigation.drawer.activity.tools.XMLParser;

public class PharmacieProfil extends BaseActivity implements View.OnClickListener{
    public String currentTel = null ;
    public boolean loginVerifed = false ;
    public static MyGPS result = null ;
    public static Pharmacie currentPharmacie = null ;
    public boolean isFavoris = false ;
    ImageView fav = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_pharmacie_profil, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);


        Main2Activity.AllIntent.add("Pharmacie " + currentPharmacie.getPharmacie());

        TextView pharmacie = (TextView) findViewById(R.id.PharmacieName);
        TextView pharmacien = (TextView) findViewById(R.id.pharmacienP);
        ImageView tel = (ImageView) findViewById(R.id.telp);
        TextView secteur = (TextView) findViewById(R.id.SecteurP);
        TextView Adresse = (TextView) findViewById(R.id.AdresseP);

        ImageView back = (ImageView) findViewById(R.id.retourpp);
        back.setOnClickListener(this);

        pharmacie.setText("Pharmacie : "+currentPharmacie.getPharmacie());
        pharmacien.setText(currentPharmacie.getPharmacien());
        currentTel = currentPharmacie.getTel();
        tel.setOnClickListener(this);
        secteur.setText(currentPharmacie.getSecteur());
        Adresse.setText(currentPharmacie.getAdresse());

        fav = (ImageView) findViewById(R.id.fav_phar);
        if(FavorisActivity.lf.contains(currentPharmacie)){
            isFavoris = true ;
            fav.setImageDrawable(getResources().getDrawable(R.drawable.fav));
        }
        else {
            isFavoris = false ;
            fav.setImageDrawable(getResources().getDrawable(R.drawable.notfav));
        }
        fav.setOnClickListener(this);


        ImageView localisation = (ImageView) findViewById(R.id.localPharmacie);
        localisation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getClass().getSimpleName().equals("Button")) {
            Button b = (Button) v;
            if (b.getText().equals("Retour")) {


            }

        }
        else if (v.getClass().getSimpleName().equals("ImageView")) {
            ImageView curr = (ImageView) v;
            if(curr.getId()==R.id.telp || curr.getId()==R.id.localPharmacie || curr.getId()==R.id.retourpp) {
                if(curr.getId()==R.id.telp) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + currentTel));
                    startActivity(callIntent);

                }
                if(curr.getId()==R.id.localPharmacie){
                    new MyTask().execute();
                }
                if(curr.getId()==R.id.retourpp){
                    int indexP = Main2Activity.AllIntent.lastIndexOf("Pharmacies");
                    int indexG = Main2Activity.AllIntent.lastIndexOf("Gardes");
                    int indexR = Main2Activity.AllIntent.lastIndexOf("Result");

                    int max = Math.max(Math.max(indexG,indexR),indexP);

                    if(indexP == max)
                        openActivity(2);
                    if(indexG == max)
                        openActivity(1);
                    if(indexR == max){
                        Intent intent = new Intent(this,ResultActivity.class);
                        startActivity(intent);
                    }
                }
            }
            else{
                if(isFavoris){
                    isFavoris = false ;
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.notfav));
                    Main2Activity.removeFav("Pharmacie",currentPharmacie.getPharmacie());
                }
                else{
                    isFavoris = true ;
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.fav));
                    Main2Activity.insertFav("Pharmacie",currentPharmacie.getPharmacie());
                }
            }
        }
    }
    public class MyTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            if(currentPharmacie.getLocalisation()!=null){
                Map_Activity.currentObject = currentPharmacie;
                loginVerifed = true;
                return null ;
            }

            MyGPS t = XMLParser.getGPS(currentPharmacie.getPharmacie() + " PHARMACIE");
            if(t != null){
                currentPharmacie.setLocalisation(t);
                Map_Activity.currentObject = currentPharmacie;
                loginVerifed = true;
                return null ;
            }
            t = XMLParser.getGPS(currentPharmacie.getAdresse() + " PHARMACIE");
            if(t != null){
                currentPharmacie.setLocalisation(t);
                Map_Activity.currentObject = currentPharmacie;
                loginVerifed = true;
                return null ;
            }
            t = XMLParser.getGPS(currentPharmacie.getSecteur() + " PHARMACIE");
            if(t != null){
                currentPharmacie.setLocalisation(t);
                Map_Activity.currentObject = currentPharmacie;
                loginVerifed = true;
                return null ;
            }
            Map_Activity.currentObject = currentPharmacie;
            loginVerifed = true;
            return  null ;
        }

        @Override
        protected void onPostExecute(String s) {
            if(loginVerifed == true)
            {
                Intent homepage = new Intent(getApplicationContext(),Map_Activity.class);
                startActivity(homepage);
                finish();
            }
        }
    }
}
