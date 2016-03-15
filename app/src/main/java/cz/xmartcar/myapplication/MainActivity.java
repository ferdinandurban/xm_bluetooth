/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cz.xmartcar.communication.Communication;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

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

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothFragment fragment = new BluetoothFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        // test BT
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(getApplicationContext(), DeviceListActivity.class);
////                startActivity(intent);
//
//                // testing
//                Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        // test rest
//        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
//        fab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "loading...", true);
//
//                // SSOService Test
//                XMSsoApi xmssoInterface = SSOService.getSSOService();
//                Call<SSOResults> ssoResultsCall = xmssoInterface.getToken("grant_type=password&username=tester&password=tester123&scope=roles");
//
//                ssoResultsCall.enqueue(new Callback<SSOResults>() {
//                    @Override
//                    public void onResponse(Call<SSOResults> call, Response<SSOResults> response) {
//                        dialog.dismiss();
//
//                        if(response.isSuccess()){
//                            SSOResults ssoResults = response.body();
//                            Log.d("MainActivity", "response = " + new Gson().toJson(ssoResults));
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<SSOResults> call, Throwable t) {
//                        showToast(t.getMessage());
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem create_sso_user = menu.findItem(R.id.create_sso_user);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.create_sso_user:
                Communication comm = new Communication();
                comm.registerUser("jan_novak", "1234");


                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
