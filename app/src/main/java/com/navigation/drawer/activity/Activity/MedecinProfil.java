package com.navigation.drawer.activity.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.Medecin;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.Classes.Speciality;
import com.navigation.drawer.activity.R;
import com.navigation.drawer.activity.tools.MyGPS;
import com.navigation.drawer.activity.tools.XMLParser;

public class MedecinProfil extends BaseActivity implements View.OnClickListener {

    public boolean loginVerifed = false;
    public String currentTel = null;
    public static MyGPS result = null;
    public static boolean isFavoris = false;
    public static Medecin currentMedecin = null;
    ImageView fav = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_medecin_profil, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        Main2Activity.AllIntent.add("Medecin " + currentMedecin.getName());

        TextView speciality = (TextView) findViewById(R.id.SpecialityM);
        ImageView tel = (ImageView) findViewById(R.id.telM);
        TextView name = (TextView) findViewById(R.id.MedecinName);
        TextView Adresse = (TextView) findViewById(R.id.AdresseM);

        ImageView back = (ImageView) findViewById(R.id.retourmp);
        back.setOnClickListener(this);

        name.setText("Dr. " + currentMedecin.getName());
        speciality.setText(currentMedecin.getSpeciality());

        currentTel = currentMedecin.getTel();

        tel.setOnClickListener(this);
        Adresse.setText(currentMedecin.getAdresse());

        fav = (ImageView) findViewById(R.id.fav_med);
        if (FavorisActivity.lf.contains(currentMedecin)) {
            isFavoris = true;
            fav.setImageDrawable(getResources().getDrawable(R.drawable.fav));
        } else {
            isFavoris = false;
            fav.setImageDrawable(getResources().getDrawable(R.drawable.notfav));
        }
        fav.setOnClickListener(this);


        ImageView localisation = (ImageView) findViewById(R.id.localMedecin);
        localisation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         if (v.getClass().getSimpleName().equals("ImageView")) {
            ImageView curr = (ImageView) v;

            if (curr.getId() == R.id.telM || curr.getId() == R.id.retourmp || curr.getId() == R.id.localMedecin) {
                if(curr.getId() == R.id.telM) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + currentTel));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                }
                if (curr.getId() == R.id.retourmp) {
                    int indexP = Main2Activity.AllIntent.lastIndexOf("Pharmacies");
                    int indexG = Main2Activity.AllIntent.lastIndexOf("Gardes");
                    int indexM = Main2Activity.AllIntent.lastIndexOf("Medecins");

                    int max = Math.max(Math.max(indexP, indexG), indexM);
                    if (max == indexP)
                        openActivity(2);
                    if (max == indexG)
                        openActivity(1);
                    if (max == indexM)
                        openActivity(3);
                }
                if (curr.getId() == R.id.localMedecin) {
                    new MyTask().execute();
                }
            }
            else{
                if(isFavoris){
                    isFavoris = false ;
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.notfav));
                    Main2Activity.removeFav("Medecin",currentMedecin.getName());
                }
                else{
                    isFavoris = true ;
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.fav));
                    Main2Activity.insertFav("Medecin",currentMedecin.getName());
                }
            }
           }
    }
    public class MyTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            currentMedecin.setLocalisation(XMLParser.getGPS(currentMedecin.getAdresse()));
            Map_Activity.currentObject = currentMedecin;
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
