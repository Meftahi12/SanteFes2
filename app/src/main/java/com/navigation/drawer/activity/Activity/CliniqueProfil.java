package com.navigation.drawer.activity.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navigation.drawer.activity.Blocs.InfoBloc;
import com.navigation.drawer.activity.Blocs.InfoLayout;
import com.navigation.drawer.activity.Classes.Clinique;
import com.navigation.drawer.activity.Classes.Medecin;
import com.navigation.drawer.activity.Classes.Speciality;
import com.navigation.drawer.activity.R;
import com.navigation.drawer.activity.tools.MyGPS;

import org.w3c.dom.Text;

import java.util.Map;
import java.util.Vector;

public class CliniqueProfil extends BaseActivity implements View.OnClickListener {

    public static Clinique currentClinique = null;
    public String currentTel = null;
    public static boolean isFavoris = false;
    LinearLayout ll =  null ;

    /*public boolean loginVerifed = false;
    public static MyGPS result = null;
    public static Speciality currentSpeciality = null;*/
    ImageView fav = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_clinique_profil, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        Main2Activity.AllIntent.add("Clinique " + currentClinique.getName());

        TextView categorie = (TextView) findViewById(R.id.CategorieC);
        ImageView tel = (ImageView) findViewById(R.id.telC);
        TextView name = (TextView) findViewById(R.id.CliniqueName);
        TextView Adresse = (TextView) findViewById(R.id.AdresseC);

        Button back = (Button) findViewById(R.id.retourcp);
        back.setOnClickListener(this);

        name.setText(currentClinique.getName());
        categorie.setText(currentClinique.getName());

        currentTel = currentClinique.getTel();

        tel.setOnClickListener(this);
        Adresse.setText(currentClinique.getAdresse());


        fav = (ImageView) findViewById(R.id.fav_cli);
        if (FavorisActivity.lf.contains(currentClinique)) {
            isFavoris = true;
            fav.setImageDrawable(getResources().getDrawable(R.drawable.fav));
        } else {
            isFavoris = false;
            fav.setImageDrawable(getResources().getDrawable(R.drawable.notfav));
        }
        fav.setOnClickListener(this);

        ll = (LinearLayout) findViewById(R.id.cliniqueInformations);

        for(Map.Entry<String, Vector<String>> entry : currentClinique.getInformations().entrySet()) {
            String key = entry.getKey();
            Vector<String> value = entry.getValue();
            InfoBloc il = new InfoBloc(getApplicationContext(),key,value);

            ll.addView(il);

        }
        Button localisation = (Button) findViewById(R.id.localClinique);
        localisation.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }
}
