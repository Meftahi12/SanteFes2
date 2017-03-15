package com.navigation.drawer.activity.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navigation.drawer.activity.Blocs.ResultBloc;
import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.ListSpecialities;
import com.navigation.drawer.activity.Classes.Medecin;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.Classes.Speciality;
import com.navigation.drawer.activity.R;

import java.util.HashMap;
import java.util.Vector;

public class ResultActivity extends BaseActivity implements View.OnClickListener{
    public static String searched = null ;
    public static HashMap<ResultBloc,Object> mapList = null ;
    public static boolean pharmacie, medecin, clinique, name;
    public static Vector<ResultBloc> listResult ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main2Activity.AllIntent.add("Result");
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_result, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        setListResult();

        TextView message = (TextView) findViewById(R.id.resultMsg);
        message.setText(listResult.size() +" résultat(s) disponible(s) pour <"+searched+"> : ");

        LinearLayout layout = (LinearLayout) findViewById(R.id.resultAll);
        for(int i=0;i<listResult.size();i++){
            layout.addView(listResult.get(i));
            listResult.get(i).setOnClickListener(this);
            LinearLayout.LayoutParams res = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            res.setMargins(5,2,5,2);
            listResult.get(i).setLayoutParams(res);
            listResult.get(i).setBackgroundColor(Color.parseColor("#f2e2cd"));

        }
        Button back = (Button) findViewById(R.id.backresult);
        back.setOnClickListener(this);


    }

    public void setListResult(){
        listResult = new Vector<ResultBloc>();
        mapList = new HashMap<ResultBloc,Object>();
        if(name) {
            if (pharmacie) {
                for (int i = 0; i < ListPharmacie.pharmacieList.size() - 1; i++) {
                    Pharmacie pharmacieCurr = ListPharmacie.get(i);
                    if (isEquals(pharmacieCurr.getPharmacie(), searched)) {
                        listResult.add(new ResultBloc(getApplicationContext(), pharmacieCurr));
                        mapList.put(listResult.get(listResult.size()-1),pharmacieCurr);
                    }
                }
            }
            if (medecin) {
                for (int i = 0; i < ListSpecialities.Specialities.size() - 1; i++) {
                    Speciality specialityCurr = ListSpecialities.Specialities.get(i);
                    for(int j=0;j<specialityCurr.getMyList().size()-1;j++){
                        Medecin medecinCurr = specialityCurr.get(j);
                        if (isEquals(medecinCurr.getName(), searched)) {
                            listResult.add(new ResultBloc(getApplicationContext(), medecinCurr));
                            mapList.put(listResult.get(listResult.size()-1),medecinCurr);
                        }
                    }
                }
            }
            //Cliiiniques
        }
        else {
            if (pharmacie) {
                for (int i = 0; i < ListPharmacie.pharmacieList.size() - 1; i++) {
                    Pharmacie pharmacieCurr = ListPharmacie.get(i);
                    if (isEquals(pharmacieCurr.getAdresse(), searched) || isEquals(pharmacieCurr.getSecteur(), searched)) {
                        listResult.add(new ResultBloc(getApplicationContext(), pharmacieCurr));
                        mapList.put(listResult.get(listResult.size() - 1), pharmacieCurr);
                    }

                }
            }
            if (medecin) {
                for (int i = 0; i < ListSpecialities.Specialities.size() - 1; i++) {
                    Speciality specialityCurr = ListSpecialities.Specialities.get(i);
                    for(int j=0;j<specialityCurr.getMyList().size()-1;j++){
                        Medecin medecinCurr = specialityCurr.get(j);
                        if (isEquals(medecinCurr.getAdresse(), searched)) {
                            listResult.add(new ResultBloc(getApplicationContext(), medecinCurr));
                            mapList.put(listResult.get(listResult.size()-1),medecinCurr);

                        }
                    }
                }
            }
            //Cliiiniques
        }
    }


    public static void setVar(String Searched,boolean Pharmacie,boolean Medecin,boolean Clinique,boolean Name){
        searched = Searched ;
        pharmacie = Pharmacie;
        medecin = Medecin;
        clinique = Clinique ;
        name = Name ;
    }

    public static boolean isEquals(String a,String b){
        String A  = a.toLowerCase();
        String B = b.toLowerCase();
        return A.contains(B) || B.contains(A);
    }

    @Override
    public void onClick(View v) {
        if (v.getClass().getSimpleName().equals("Button")) {
            Button b = (Button) v;
            if (b.getText().equals("Retour")) {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
        }
        if (v.getClass().getSimpleName().equals("ResultBloc")){
            ResultBloc rb = (ResultBloc) v;
            if(mapList.get(rb).getClass().getSimpleName().equals("Medecin")) {
                Intent intent = new Intent(this, MedecinProfil.class);
                Medecin curr = (Medecin) mapList.get(rb);
                MedecinProfil.currentMedecin = curr;
                startActivity(intent);
            }
            if(mapList.get(rb).getClass().getSimpleName().equals("Pharmacie")) {
                Pharmacie curr = (Pharmacie) mapList.get(rb);
                Intent intent = new Intent(this, PharmacieProfil.class);
                intent.putExtra("name", curr.getPharmacie());
                startActivity(intent);
            }
        }
    }
}
