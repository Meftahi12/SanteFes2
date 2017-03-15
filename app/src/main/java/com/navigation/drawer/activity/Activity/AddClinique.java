package com.navigation.drawer.activity.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.navigation.drawer.activity.Blocs.LayoutEdit;
import com.navigation.drawer.activity.Classes.Clinique;
import com.navigation.drawer.activity.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AddClinique  extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {



    private String current_Info = "";

    EditText name = null , adresse = null , tel = null ;
    RadioButton administ = null ;
    String current_Categorie = null ;
    LinearLayout myLayout = null ;
    TextView tv = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_clinique, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        myLayout = (LinearLayout) findViewById(R.id.clInfo);
        administ = (RadioButton) findViewById(R.id.AdminClinique);


        tv = new TextView(getApplicationContext());
        tv.setText("Ajouter une information");
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
        layoutParams.weight = 1 ;
        myLayout.addView(tv);
        tv.setLayoutParams(layoutParams);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myLayout.removeView(tv);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                myLayout.addView(new LayoutEdit(getApplicationContext(),lp));
                myLayout.addView(tv);
                tv.setOnClickListener(this);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.categ_choice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Categorie_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        name = (EditText) findViewById(R.id.clName);
        adresse = (EditText) findViewById(R.id.clAdresse);
        tel = (EditText) findViewById(R.id.clTel);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                current_Categorie = "public";
                break;
            case 2:
                current_Categorie = "privée";
                break;
            case 3:
                current_Categorie = "semi-public";
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void AddClinique(View view) {
        Clinique cl = new Clinique();
        cl.setName(name.getText().toString());
        cl.setAdresse(adresse.getText().toString());
        cl.setTel(tel.getText().toString());
        cl.setCategorie(current_Categorie);
        HashMap<String,Vector<String>> map = new HashMap<String,Vector<String>>();
        for(int i=0;i<myLayout.getChildCount();i++){
            if(myLayout.getChildAt(i).getClass().getSimpleName().equals("LayoutEdit")){
                LayoutEdit le = (LayoutEdit) myLayout.getChildAt(i);
                map.put(le.getInfoName().getText().toString(),le.getInformations());
            }
        }
        cl.setInformations(map);

        current_Info = clToString(cl);

        if(administ.isChecked()){
            startActivity(new Intent(this,AdministratorActivity.class));
        }
    }

    public String clToString(Clinique clinique){
        String ret = "" ;

        ret+="name:="+ clinique.getName()+";;\n" ;
        ret+="categorie:="+clinique.getCategorie()+";;\n";
        ret+="adresse:="+clinique.getAdresse()+";;\n";
        ret+="tel:="+clinique.getTel()+";;\n";

        for(Map.Entry<String, Vector<String>> entry : clinique.getInformations().entrySet()) {
            String key = entry.getKey();
            Vector<String> value = entry.getValue();
            ret+=key+":=";
            for(int i=1;i<value.size();i++){
                if(i==1)
                    ret+=value.get(1);
                else{
                    ret+="&&"+value.get(i);
                }
            }
            ret+=";;\n";
        }
        return ret ;
    }

}
