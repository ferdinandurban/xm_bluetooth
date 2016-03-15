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
import android.widget.ViewAnimator;

import cz.xmartcar.communication.bluetooth.BluetoothProtocol;
import cz.xmartcar.communication.rest.RestClient;
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
//                // SSO Test
//                XMSSOInterface xmssoInterface = SSO.getSSOService();
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
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
