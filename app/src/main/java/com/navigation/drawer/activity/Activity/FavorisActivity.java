package com.navigation.drawer.activity.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class FavorisActivity  extends BaseActivity implements View.OnClickListener{

    public static HashMap<ResultBloc,Object> mapList = null ;
    public static Vector<ResultBloc> listFav = null ;
    public static ArrayList<Object> lf = null ;
    @Override


    protected void onCreate(Bundle savedInstanceState) {

        Main2Activity.AllIntent.add("Favoris");
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_favoris, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        LinearLayout layout = (LinearLayout) findViewById(R.id.favAll);

        lf = Main2Activity.getFavoris();

        setListFav();

        for(int i=0;i<listFav.size();i++){
            layout.addView(listFav.get(i));
            listFav.get(i).setOnClickListener(this);
            LinearLayout.LayoutParams res = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            res.setMargins(5,2,5,2);
            listFav.get(i).setLayoutParams(res);
            listFav.get(i).setBackgroundColor(Color.parseColor("#f2e2cd"));
        }

        Button back = (Button) findViewById(R.id.backfav);
        back.setOnClickListener(this);


    }

    public void setListFav(){
        listFav = new Vector<ResultBloc>();
        mapList = new HashMap<ResultBloc,Object>();
        for(int i=0;i<lf.size();i++){
            listFav.add(new ResultBloc(getApplicationContext(), lf.get(i)));
            mapList.put(listFav.get(i),lf.get(i));
        }
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
