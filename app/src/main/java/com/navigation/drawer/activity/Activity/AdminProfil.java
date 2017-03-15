package com.navigation.drawer.activity.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.navigation.drawer.activity.Classes.ListGarde;
import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.ListSpecialities;
import com.navigation.drawer.activity.R;
import com.navigation.drawer.activity.tools.HttpManager;
import com.navigation.drawer.activity.tools.XMLParser;

public class AdminProfil extends BaseActivity implements View.OnClickListener{

    TextView upP = null , lUpP = null , upM = null , lUpM = null ;
    public boolean isFinished = false ;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLayoutInflater().inflate(R.layout.activity_admin_profil, frameLayout);
    mDrawerList.setItemChecked(position, true);
    setTitle(listArray[position]);
    upP = (TextView) findViewById(R.id.updateP);
    lUpP = (TextView) findViewById(R.id.lastUpP);
    upM = (TextView) findViewById(R.id.updateM);
    lUpM = (TextView) findViewById(R.id.lastUpM);

    lUpP.setText("LAST UPDATE = "+ListPharmacie.get(ListPharmacie.pharmacieList.size()-1).getPharmacie());
    lUpM.setText("LAST UPDATE = "+ListSpecialities.get(ListSpecialities.Specialities.size()-1).getName());

    upP.setOnClickListener(this);
    upM.setOnClickListener(this);

}
@Override
public void onClick(View v) {
        if(v.getClass().getSimpleName().equals("TextView")){
            TextView b = (TextView) v;
            if(b.getText().equals("mettre a jours les pharmacies")){
                (new MyTask()).execute("Pharmacie");
            }
            if(b.getText().equals("mettre a jours les medecins")){
                (new MyTask()).execute("Medecin");
            }
        }
    }

    public void addClinique(View view) {
        startActivity(new Intent(AdminProfil.this,AddClinique.class));
    }

    public class MyTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            if(params[0].equals("Pharmacie")) {
                String currentResult = HttpManager.getDatas("http://www.pfesmi.tk/setPharmacies.php");
                if (currentResult != null) {
                    ListPharmacie.pharmacieList = XMLParser.XMLPharmaciesPARSER(HttpManager.getData("http://www.pfesmi.tk/getPharmacies.php"));
                }
            }
            if(params[0].equals("Medecin")) {
                String currentResult = HttpManager.getDatas("http://www.pfesmi.tk/setMedecins.php");
                if (currentResult != null) {
                    ListSpecialities.Specialities = XMLParser.XMLSpecialitiesPARSER(HttpManager.getData("http://www.pfesmi.tk/getMedecins.php"));
                }
            }
            isFinished = true ;
            return  null ;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isFinished){
                refresh();
                finish();
            }
        }
    }
    public void refresh(){
        startActivity(new Intent(this,AdminProfil.class));
    }
}
