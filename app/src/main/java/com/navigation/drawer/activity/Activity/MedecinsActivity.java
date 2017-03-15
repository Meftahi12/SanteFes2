package com.navigation.drawer.activity.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.Medecin;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.Classes.Speciality;
import com.navigation.drawer.activity.R;

import java.util.HashMap;

public class MedecinsActivity extends BaseActivity implements View.OnClickListener{

    HashMap<Button,Medecin> list = new HashMap<Button,Medecin>();
    public static Speciality currentSpeciality = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        Main2Activity.AllIntent.add("Medecins");
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_medecins, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        Button b = (Button) findViewById(R.id.backMo);
        b.setOnClickListener(this);

        if(currentSpeciality.getMyList() != null) {


            LinearLayout myMedecins = (LinearLayout) findViewById(R.id.MedecinO);
            for(int i=0;i<currentSpeciality.getMyList().size() - 1;i++){
                Medecin medecin = currentSpeciality.getMyList().get(i);
                Button but = new Button(getApplicationContext());
                but.setBackgroundColor(Color.parseColor("#008080"));
                but.setText(medecin.getName());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(2,2,2,2);
                but.setLayoutParams(layoutParams);
                but.setTextColor(Color.parseColor("#ffffff"));
                but.setOnClickListener(this);
                list.put(but,medecin);
                myMedecins.addView(but);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getClass().getSimpleName().equals("Button")){
            Button b = (Button) v;
            if(b.getText().equals("Retour"))
                openActivity(3);
            else {

                Intent intent = new Intent(this,MedecinProfil.class);
                MedecinProfil.currentMedecin = list.get(b);
                startActivity(intent);
            }
        }
    }
}
