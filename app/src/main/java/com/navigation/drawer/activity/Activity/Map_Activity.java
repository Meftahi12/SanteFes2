package com.navigation.drawer.activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.navigation.drawer.activity.Classes.Medecin;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.R;

public class Map_Activity extends BaseActivity implements View.OnClickListener {

    public static Object currentObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_map_, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        TextView infor = (TextView) findViewById(R.id.mapInformation);
        TextView GPS = (TextView) findViewById(R.id.markerMap);


        if (currentObject.getClass().getSimpleName().equals("Pharmacie")) {
            Pharmacie pharmacie = (Pharmacie) currentObject;
            infor.setText("La localisation de " + pharmacie.getPharmacie() + "," + pharmacie.getAdresse()+","+pharmacie.getSecteur());
            if (pharmacie.getLocalisation() == null)
                GPS.setText("pas d'initaire disponible pour cette adresse");
            else
                GPS.setText(pharmacie.getLocalisation().toString());
        }
        if (currentObject.getClass().getSimpleName().equals("Medecin")) {
            Medecin medecin = (Medecin) currentObject;
            infor.setText("La localisation de " + medecin.getName() + "," + medecin.getAdresse());
            if (medecin.getLocalisation() == null)
                GPS.setText("pas d'initaire disponible pour cette adresse");
            else
                GPS.setText(medecin.getLocalisation().toString());
        }

        Button ret = (Button) findViewById(R.id.RetourMap);

        ret.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getClass().getSimpleName().equals("Button")) {
            Button b = (Button) v;
            if (b.getText().equals("Retour")) {
                String[] var = Main2Activity.AllIntent.get(Main2Activity.AllIntent.size() - 1).split(" ");
                if (var[0].equals("Pharmacie")) {
                    Pharmacie p = (Pharmacie) currentObject;
                    Intent intent = new Intent(this, PharmacieProfil.class);
                    intent.putExtra("name", p.getPharmacie());
                    startActivity(intent);
                }
                else {
                    MedecinProfil.currentMedecin = (Medecin) currentObject;
                    Intent intent = new Intent(this, MedecinProfil.class);
                    startActivity(intent);
                }
            }
        }
    }
}
