package com.example.speechynew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.net.wifi.aware.WifiAwareManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechynew.Rertofit.ApiClient;
import com.example.speechynew.Rertofit.ApiInterface;
import com.example.speechynew.connectDB.DataUsernew;
import com.example.speechynew.connectDB.UserData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopActivity extends Activity {
    Button button_OK;
    EditText ET_NICKNAME;
    TextView TV_EMAIL;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        button_OK = findViewById(R.id.button_OK);
        ET_NICKNAME = findViewById(R.id.ET_NickName);
        TV_EMAIL = findViewById(R.id.TV_EMAIL);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        ET_NICKNAME.setText(acct.getDisplayName());
        String NICKNAME = ET_NICKNAME.getText().toString();
        String name = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String email = acct.getEmail();
        String personId = acct.getId();

        TV_EMAIL.setText(email);
        button_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String device = Build.BOOTLOADER;
                Log.d("SHOW_NICKNAME","NICKNAMEOLD: "+NICKNAME);
                Log.d("SHOW_NICKNAME","NICKNAMENEW: "+ET_NICKNAME.getText().toString());
                Log.d("SHOW_NICKNAME","ID: "+personId);
                DataUsernew DataUsernew = new DataUsernew(personId,device,email,ET_NICKNAME.getText().toString());
                //DataUsernew DataDevicenew = new DataUsernew(personId,device);


                Call<DataUsernew>calldata =apiInterface.DataUser_new(DataUsernew);
                calldata.enqueue(new Callback<com.example.speechynew.connectDB.DataUsernew>() {
                    @Override
                    public void onResponse(Call<com.example.speechynew.connectDB.DataUsernew> call, Response<com.example.speechynew.connectDB.DataUsernew> response) {
                        DataUsernew dataUsernew =response.body();
                        if (dataUsernew!=null){
                            Log.d("API_DATA_DataUser","SaveSuccessful");
                            Log.d("API_DATA_DataUser","MS:"+DataUsernew.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.speechynew.connectDB.DataUsernew> call, Throwable t) {

                    }
                });
            /*
                Call<UserData> calldatauser =apiInterface.DataUsernew(personId,email,ET_NICKNAME.getText().toString());
                calldatauser.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        UserData datauser = response.body();
                        if (datauser!=null){
                            Log.d("API_DATA_DataUser","SaveSuccessful");
                            Log.d("API_DATA_DataUser","MS:"+datauser.getMessages());
                        }
                        else {

                            Log.d("API_DATA_DataUser","MS:"+datauser.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        //Log.d("API_DATA_DataUser","Savefail T "+t);
                    }
                });

             */
                /*
                Call<UserData>calldeviceuser =apiInterface.deviceusernew(personId,device);
                calldeviceuser.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        UserData deviceuser = response.body();
                        if (deviceuser!=null){
                            Log.d("API_DATA_DataUser","SaveSuccessful");
                            Log.d("API_DATA_DataUser","MS:"+deviceuser.getMessages());
                        }
                        else {

                            Log.d("API_DATA_DataUser","MS:"+deviceuser.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {

                    }
                });
                 */
               /* Call<DataUsernew>calldatadevice=apiInterface.deviceuser_new(DataDevicenew);
                calldatadevice.enqueue(new Callback<com.example.speechynew.connectDB.DataUsernew>() {
                    @Override
                    public void onResponse(Call<com.example.speechynew.connectDB.DataUsernew> call, Response<com.example.speechynew.connectDB.DataUsernew> response) {
                        DataUsernew datadevice = response.body();
                        if (datadevice!=null){
                            Log.d("API_DATA_DataUser","SaveSuccessful");
                            Log.d("API_DATA_DataUser","MS:"+datadevice.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.speechynew.connectDB.DataUsernew> call, Throwable t) {

                    }
                });
                */


                Toast.makeText(PopActivity.this, "SaveData Successfully\n"+email+"\n"+ET_NICKNAME.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(PopActivity.this,SettinglangActivity.class));
                finish();
            }
        });



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int heigh = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(heigh*0.65));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x =0;
        params.y = -20;
        getWindow().setAttributes(params);
    }
}