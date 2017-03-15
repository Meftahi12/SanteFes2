package com.navigation.drawer.activity.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.navigation.drawer.activity.R;

public class ClientOrAdminActivity extends BaseActivity{

    public static Class nextActivity = null ;
    public static Object currentObject = null;
    Button asClient  = null ;
    Button asAdministateur = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_client_or_admin, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        asClient = (Button) findViewById(R.id.addAsClient);
        asAdministateur = (Button) findViewById(R.id.addAsAdministateur);

        asClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientOrAdminActivity.this,nextActivity.getClass()));
            }
        });
        asAdministateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AdministratorActivity.nextActivity = nextActivity ;
                    startActivity(new Intent(ClientOrAdminActivity.this,AdministratorActivity.class));
            }
        });
    }
}
