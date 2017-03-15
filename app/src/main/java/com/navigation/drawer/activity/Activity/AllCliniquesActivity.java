package com.navigation.drawer.activity.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.navigation.drawer.activity.Classes.Clinique;
import com.navigation.drawer.activity.Classes.ListCliniques;
import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.Classes.Speciality;
import com.navigation.drawer.activity.R;

import java.util.HashMap;

public class AllCliniquesActivity extends BaseActivity implements View.OnClickListener{


    HashMap<Button,Clinique> list = new HashMap<Button,Clinique>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_all_cliniques, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        Button b = (Button) findViewById(R.id.backC);
        b.setOnClickListener(this);

        if(ListCliniques.myList != null) {
            /*TextView lastUpdate = (TextView) findViewById(R.id.updateP);
            lastUpdate.setText("Last update " + ListPharmacie.pharmacieList.get(ListPharmacie.pharmacieList.size() - 1).getPharmacie());
*/
            LinearLayout myCliniques = (LinearLayout) findViewById(R.id.CliniquesAll);
            for(int i=0;i<ListCliniques.myList.size() ;i++){
                Clinique clinique = ListCliniques.myList.get(i);
                Button but = new Button(getApplicationContext());
                but.setBackgroundColor(Color.parseColor("#008080"));
                but.setText(clinique.getName());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(2,2,2,2);
                but.setLayoutParams(layoutParams);
                but.setTextColor(Color.parseColor("#ffffff"));
                but.setOnClickListener(this);
                list.put(but,clinique);
                myCliniques.addView(but);
            }
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getClass().getSimpleName().equals("Button")) {
            Button b = (Button) v;
            if (b.getText().equals("Retour"))
                openActivity(0);
            else {
                CliniqueProfil.currentClinique = list.get(b);
                startActivity(new Intent(this,CliniqueProfil.class));
            }
        }
    }
}
