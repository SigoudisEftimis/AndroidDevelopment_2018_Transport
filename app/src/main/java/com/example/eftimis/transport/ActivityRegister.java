package com.example.eftimis.transport;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityRegister extends AppCompatActivity {


    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //To hide AppBar for fullscreen.
        ActionBar ab = getSupportActionBar();
        ab.hide();

        openHelper = new SQLiteDBHelper(this);

        //Referencing EditText widgets and Button placed inside in xml layout file

        final EditText txtLastname = (EditText) findViewById(R.id.lastName);
        final EditText txtFirstanme =(EditText) findViewById(R.id.firstName);
        final EditText txtEmail = (EditText) findViewById(R.id.emailTxt);
        final EditText txtPassword = (EditText) findViewById(R.id.password);
        final EditText txtconfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        Button  loginBtn = (Button) findViewById(R.id.loginBtn);
        final CheckBox checkb =  findViewById(R.id.checkBox3);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move2MainPage();
                finish();
            }
        });

        //Register Button Click Event
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = openHelper.getWritableDatabase();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityRegister.this);

                String firstname = txtFirstanme.getText().toString();
                String lastname = txtLastname.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String cpassword = txtconfirmPassword.getText().toString();

                if(TextUtils.isEmpty(firstname)) {
                    txtLastname.setError("You must fill this field");
                    return;
                }
                if(TextUtils.isEmpty(lastname)) {
                    txtFirstanme.setError("You must filled this field ");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    txtPassword.setError("You must filled this field ");
                    return;
                }

                if(TextUtils.isEmpty(firstname)) {
                    txtconfirmPassword.setError("You must filled this field");
                    return;
                }

                 if ( !ConfirmPassword(password  , cpassword) )  {
                     txtconfirmPassword.setError("Password is not the same  ");
                     return ;
                 }


                //Calling InsertData Method - Defined below
                InsertData(firstname,lastname,email,password);

                //Alert dialog after clicking the Register Account
                builder.setTitle("Information");
                builder.setMessage("Your Account is Successfully Created.");
                builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Finishing the dialog and removing Activity from stack.
                        dialogInterface.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    //Inserting Data into database - Like INSERT INTO QUERY.
    public void InsertData(String firstName, String lastName, String email , String password) {

        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_FIRSTNAME,firstName);
        values.put(SQLiteDBHelper.COLUMN_LASTNAME,lastName);
        values.put(SQLiteDBHelper.COLUMN_EMAIL,email);
        values.put(SQLiteDBHelper.COLUMN_PASSWORD,password);
        long id = db.insert(SQLiteDBHelper.TABLE_NAME,null,values);

    }

    public void move2MainPage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public boolean ConfirmPassword(String password  , String Confirm ){

        if (new String(password).equals(Confirm)) return true;
        else return false ;
    }

}
