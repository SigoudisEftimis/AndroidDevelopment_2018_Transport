package com.example.eftimis.transport;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;



public class MainActivity extends AppCompatActivity {

    //Intent Actions
    private static final String HOME_ACTIVITIES = "com.example.eftimis.transport.Homepage";
    // Request Code
    private static final int HOME_ACTIVITIES_REQUEST_CODE = 10;

    private Button customButton;
    private LoginButton fbLoginButton;
    private Button  RegisterButton ;
    private CallbackManager callbackManager;
    private Button loginBtn;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private SQLiteOpenHelper dbhelper;
    private SQLiteDatabase db;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        fbLoginButton = (LoginButton) findViewById(R.id.facebookBtn); //built in facebook button
        customButton =  (Button) findViewById(R.id.loginFacebookBtn); //my custom button
        loginBtn = (Button) findViewById(R.id.loginBtn);
        RegisterButton = (Button) findViewById(R.id.registerButton) ;
        //To hide AppBar for fullscreen.
        ActionBar ab = getSupportActionBar();
        ab.hide();
        //Referencing UserEmail, Password EditText and TextView for SignUp Now
        final EditText  emailTxt = (EditText) findViewById(R.id.emailTxt);
        final EditText  password = (EditText) findViewById(R.id.passwordTxt);

        //Opening SQLite Pipeline
        dbhelper = new SQLiteDBHelper(this);
        db = dbhelper.getReadableDatabase();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailTxt.getText().toString();
                String pass = password.getText().toString();

                cursor = db.rawQuery("SELECT *FROM "+SQLiteDBHelper.TABLE_NAME+" WHERE "+SQLiteDBHelper.COLUMN_EMAIL+"=? AND "+SQLiteDBHelper.COLUMN_PASSWORD+"=?",new String[] {email,pass});
                if (cursor != null) {
                    if(cursor.getCount() > 0) {

                        cursor.moveToFirst();
                        //Retrieving User FullName and Email after successfull login and passing to HomepageActivity

                        String _fname = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_FIRSTNAME));
                        String _email= cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EMAIL));
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,HomepageActivity.class);
                        intent.putExtra("fullname",_fname);
                        intent.putExtra("email",_email);
                        startActivity(intent);

                        //Removing MainActivity[Login Screen] from the stack for preventing back button press.

                    }
                    else {

                        //I am showing Alert Dialog Box here for alerting user about wrong credentials
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Username or Password is wrong.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        //-------Alert Dialog Code Snippet End Here
                    }
                }

            }
        });


        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move2RegisterActivity();

            }
        });

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginButton.performClick();
            }
        });
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LOGIN_SUCCESS", "Success");
                fbLoginButton.setVisibility(View.INVISIBLE); //<- IMPORTANT
                move2HomePage();

            }

            @Override
            public void onCancel() {
                Log.d("LOGIN_CANCEL", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LOGIN_ERROR", "Error");
            }





        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            Intent intent = new Intent(this , HomepageActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void move2RegisterActivity(){
        Intent intent = new Intent(this , ActivityRegister.class );
        startActivity(intent);
    }
    public void move2HomePage(){
        Intent intent = new Intent(this , HomepageActivity.class );
        startActivity(intent);
    }


}


