package com.example.eftimis.transport;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

public class HomepageActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks
        
    {

    protected Button LogoutButton;
    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private AutoCompleteTextView mAutocompleteTextView_2;
    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mNameTextView_2;
    private TextView mAddressTextView_2;
    private Button weigthBtn;
    private Button breakBtn;
    private Button fastBtn;
    private TextView weightTxt ;
    private TextView breakTxt ;
    private TextView fastTxt ;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private Calendar calendar;
    private TextView dateView;
    public  static  boolean checkBreakable = false ;
    public  static boolean checkFast = false ;
    public static int packages ;
    public static int weight ;
    private int year, month, day;
    private final Context context = this;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
                new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private TextView numberTxt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //To hide AppBar for fullscreen.
        ActionBar ab = getSupportActionBar();
        ab.hide();
        LogoutButton = (Button) findViewById(R.id.searchButton);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               move2ResultsPage();
            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(HomepageActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView_2 =(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView_2.setThreshold(3);
        mAutocompleteTextView.setThreshold(3);
        mNameTextView = (TextView) findViewById(R.id.name);
        mAddressTextView = (TextView) findViewById(R.id.address);
        mNameTextView_2 = findViewById(R.id.name2);
        mAddressTextView_2= findViewById(R.id.address2);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mAutocompleteTextView_2.setOnItemClickListener(mAutocompleteClickListener_2);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
        mAutocompleteTextView_2.setAdapter(mPlaceArrayAdapter);
        Button logout  = findViewById(R.id.loginBtn2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectFromFacebook();
                move2MainPage();
            }
        });

        dateView = (TextView) findViewById(R.id.button2);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        weigthBtn = (Button) findViewById(R.id.weightBtn);
        breakBtn = (Button)  findViewById(R.id.breakBtn);
        fastBtn = (Button) findViewById(R.id.fastBtn);

        fastTxt = (TextView) findViewById(R.id.delivery);
        breakTxt= (TextView) findViewById(R.id.breakableTxt);
        weightTxt = (TextView) findViewById(R.id.weighTxt);
        numberTxt = (TextView) findViewById(R.id.numberTxt);

        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBreakable){
                    breakTxt.setText("Yes");
                    breakBtn.setCompoundDrawablesWithIntrinsicBounds( R.drawable.breakable2, 0, 0, 0);
                    checkBreakable = true ;

                }else if(checkBreakable){
                    breakTxt.setText("No");
                    breakBtn.setCompoundDrawablesWithIntrinsicBounds( R.drawable.breakable, 0, 0, 0);
                    checkBreakable = false ;

                }else{
                    Log.d("Error","ERROR");
                }
            }
        });
        fastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkFast){
                    fastTxt.setText("Yes");
                    fastBtn.setCompoundDrawablesWithIntrinsicBounds( R.drawable.fast2, 0, 0, 0);
                    checkFast = true ;

                }else if(checkFast){
                    fastTxt.setText("No");
                    fastBtn.setCompoundDrawablesWithIntrinsicBounds( R.drawable.fast, 0, 0, 0);
                    checkFast = false ;

                }else{
                    Log.d("Error","ERROR");
                }
            }
        });

        weigthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.weight);
                dialog.setTitle("Title...");
                Button dialogButton = (Button) dialog.findViewById(R.id.submit);
                final EditText weightinput = (EditText) dialog.findViewById(R.id.weightinput);
                final EditText numberinput = (EditText) dialog.findViewById(R.id.number);

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String w = weightinput.getText().toString();
                        String n = numberinput.getText().toString();
                        weight  = Integer.valueOf(w);
                        packages = Integer.valueOf(n);
                        if(TextUtils.isEmpty(w)) {
                            weightinput.setError("You must fill this field");
                            return;
                        }
                        if(TextUtils.isEmpty(n)) {
                            numberinput.setError("You must filled this field ");
                            return;
                        }
                        numberTxt.setText(n);
                        weightTxt.setText(w);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });
    }

        @SuppressWarnings("deprecation")
        public void setDate(View view) {
            showDialog(999);
            Toast.makeText(getApplicationContext(), "ca",
                    Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected Dialog onCreateDialog(int id) {
            // TODO Auto-generated method stub
            if (id == 999) {
                return new DatePickerDialog(this,
                        myDateListener, year, month, day);
            }
            return null;
        }

        private DatePickerDialog.OnDateSetListener myDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0,
                                          int arg1, int arg2, int arg3) {
                        // TODO Aug3 = day;
                        showDate(arg1, arg2+1, arg3);
                    }
                };

        private void showDate(int year, int month, int day) {
            dateView.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }

        private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
            }
        };
        private AdapterView.OnItemClickListener mAutocompleteClickListener_2 = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback_2);
                Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
            }
        };
        private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
                = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    Log.e(LOG_TAG, "Place query did not complete. Error: " +
                            places.getStatus().toString());
                    return;
                }

                // Selecting the first object buffer.
                final Place place = places.get(0);
                mNameTextView.setText(Html.fromHtml(place.getName() + ""));
                mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
                CharSequence attributions = places.getAttributions();
            }
        };
        private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_2
                = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    Log.e(LOG_TAG, "Place query did not complete. Error: " +
                            places.getStatus().toString());
                    return;
                }

                // Selecting the first object buffer.
                final Place place = places.get(0);
                mNameTextView_2.setText(Html.fromHtml(place.getName() + ""));
                mAddressTextView_2.setText(Html.fromHtml(place.getAddress() + ""));
                CharSequence attributions = places.getAttributions();

            }


        };



        public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {

            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();


    }
    public void move2ResultsPage(){
        Intent intent = new Intent(this,Results.class);
        startActivity(intent);
    }
        public void move2MainPage(){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
             getMenuInflater().inflate(R.menu.main,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                int id  = item.getItemId();
                if  (id == R.id.logout){
                    disconnectFromFacebook();
                    move2MainPage();
                }
            return true;
        }
    }
