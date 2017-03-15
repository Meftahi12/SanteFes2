package com.navigation.drawer.activity.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.navigation.drawer.activity.Classes.ListGarde;
import com.navigation.drawer.activity.Classes.ListPharmacie;
import com.navigation.drawer.activity.Classes.ListSpecialities;
import com.navigation.drawer.activity.Classes.Medecin;
import com.navigation.drawer.activity.Classes.Pharmacie;
import com.navigation.drawer.activity.Classes.Speciality;
import com.navigation.drawer.activity.R;
import com.navigation.drawer.activity.tools.MyGPS;

import java.util.ArrayList;
import java.util.Vector;


public class SearchActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    CheckBox cPha = null, cCli = null, cMed = null;
    EditText searchField = null;
    RadioButton name = null, quartier = null;
    Button search = null;
    ImageView addMedecin = null , addClinique = null , addPharmacie = null ;
    public SQLiteDatabase myDB = null;
    private LocationManager locationManager;
    private LocationListener listener;
    public Location currentLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);


        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);


        Spinner spinner = (Spinner) findViewById(R.id.Spinner_choice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Annuaire_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        searchField = (EditText) findViewById(R.id.editText3);

        cPha = (CheckBox) findViewById(R.id.CheckPharmacies);
        cMed = (CheckBox) findViewById(R.id.CheckMedecins);
        cCli = (CheckBox) findViewById(R.id.CheckCliniques);

        name = (RadioButton) findViewById(R.id.ByName);
        quartier = (RadioButton) findViewById(R.id.BySecteur);

        search = (Button) findViewById(R.id.Search);
        search.setOnClickListener(this);

        ImageView Closer = (ImageView) findViewById(R.id.proche);
        Closer.setOnClickListener(this);

        addMedecin = (ImageView) findViewById(R.id.addMedecin);
        addMedecin.setOnClickListener(this);
        addClinique = (ImageView) findViewById(R.id.addClinique);
        addClinique.setOnClickListener(this);
        addPharmacie = (ImageView) findViewById(R.id.addPharmacie);
        addPharmacie.setOnClickListener(this);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);




        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 20, listener);

        configure_button();
    }

    public boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void DoTask() throws InterruptedException {

        if(isNetworkAvailable()){
        while (currentLocation == null && isGPSEnabled(getApplicationContext())) {

        }
        if (ListGarde.pharmacieList != null) {
                double dis = 1000000000;
                Pharmacie closer = null;
                for (int i = 0; i < ListGarde.pharmacieList.size() - 1; i++) {

                    Pharmacie currentPharmacie = ListGarde.pharmacieList.get(i);
                    Location crLoc = new Location("");
                    crLoc.setLongitude(currentPharmacie.getLocalisation().getLongitude());
                    crLoc.setLatitude(currentPharmacie.getLocalisation().getLaltitude());
                    double currDistance = currentLocation.distanceTo(crLoc);

                    if (dis > currDistance) {
                        dis = currDistance;
                        closer = currentPharmacie;
                    }
                }
                MapsActivity.laltitude = closer.getLocalisation().getLaltitude();
                MapsActivity.longitude = closer.getLocalisation().getLongitude();

                MapsActivity.curLaltitude = currentLocation.getLatitude();
                MapsActivity.curLongitude = currentLocation.getLongitude();

                MapsActivity.currentPharmacie = closer;

                startActivity(new Intent(this, MapsActivity.class));
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"This service is not available in Offline",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
    }

    public TextView getNameTV(String name){
        TextView tv = new TextView(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setText(name);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextSize(16);

        return tv ;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position - 1) {
            case 0:
                openActivity(2);
                break;
            case 1:
                openActivity(1);
                break;
            case 2:
                openActivity(3);
                break;
            case 3:
                openActivity(4);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getClass().getSimpleName().equals("Button")) {
            Button b = (Button) v;
            if (b.getText().equals("Search")) {
                if (searchField.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Entrer quelque chose svp", Toast.LENGTH_SHORT).show();
                else {
                    ResultActivity.setVar(searchField.getText().toString(), cPha.isChecked(), cMed.isChecked(), cCli.isChecked(), name.isChecked());
                    Intent intent = new Intent(this, ResultActivity.class);
                    startActivity(intent);
                }
            }
        }
        if (v.getClass().getSimpleName().equals("ImageView")) {
            ImageView b = (ImageView) v;
            if (b.getId() == R.id.proche) {
                try {
                    DoTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (b.getId() == R.id.addClinique) {
                ClientOrAdminActivity.nextActivity = AddClinique.class;
                startActivity(new Intent(SearchActivity.this, ClientOrAdminActivity.class));
            }
            /*if (b.getId() == R.id.addMedecin) {
                startActivity(new Intent(SearchActivity.this, AddMedecin.class));
            }
            if (b.getId() == R.id.addPharmacie) {
                startActivity(new Intent(SearchActivity.this, AddPharmacie.class));
            }*/
        }
    }
    private  boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

