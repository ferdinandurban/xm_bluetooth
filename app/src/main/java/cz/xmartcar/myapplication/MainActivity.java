/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import cz.xmartcar.communication.Model.RestResults;
import cz.xmartcar.communication.bluetooth.BluetoothProtocol;
import cz.xmartcar.communication.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private BluetoothProtocol   mBTProtocol;
    private RestClient          mRestService;

    private Subscriber<String> mXMCSubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            System.out.println(s);
            showToast("User data obtained");
        }

        @Override
        public void onCompleted() {
            System.out.print("");
        }

        @Override
        public void onError(Throwable e) { }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBTProtocol = new BluetoothProtocol(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // test BT
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), DeviceListActivity.class);
//                startActivity(intent);

                // testing
                Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                startActivity(intent);
            }
        });


        // test rest
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "loading...", true);

                RestClient.XMRestApiInterface service = RestClient.getClient();

                Call<RestResults> call = service.getUsersNamedTom("tom");

                call.enqueue(new Callback<RestResults>() {
                    @Override
                    public void onResponse(Call<RestResults> call, Response<RestResults> response) {
                        dialog.dismiss();
                        Log.d("MainActivity", "Status Code = " + response.code());
                        if (response.isSuccess()) {
                            // request successful (status code 200, 201)
                            RestResults result = response.body();
                            Log.d("MainActivity", "response = " + new Gson().toJson(result));
                        } else {
                            // response received but request not successful (like 400,401,403 etc)
                            //Handle errors
                        }
                    }

                    public void onFailure(Call<RestResults> call, Throwable t) {
                        showToast(t.getMessage());
                        dialog.dismiss();
                    }

                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
