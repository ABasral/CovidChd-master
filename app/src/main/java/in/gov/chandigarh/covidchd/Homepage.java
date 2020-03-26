package in.gov.chandigarh.covidchd;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity implements OnMapReadyCallback {

    String pdflink;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private TextView suspectst,confirmedt,recoveredt,deathst,ideath,iconf,wconf,wdeath,hindimsg;
    private LinearLayout call,aware,travel,hospital,faq,share,massgather,about,notices,feedback,adm_msg,extnotice,conf,fluclinic,quarantine;
    private Button coronaWeb,symptoms;
    private TextView chd,chdhelp,india,indiahelp;

    private static final String TAG = "Homepage";
    private static final String FINE_LOC = "Manifest.permission.ACCESS_FINE_LOCATION";
    private static final String COARSE_LOC = "Manifest.permission.ACCESS_COARSE_LOCATION";
    private static final int REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 12f;
    private GoogleMap gMap;
    private boolean permissionGrant = false;
    private FusedLocationProviderClient mfusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(Homepage.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_menu);




        coronaWeb = (Button) findViewById(R.id.website);
        suspectst = (TextView) findViewById(R.id.suspect_num);
        confirmedt = (TextView) findViewById(R.id.confirmed_num);
        recoveredt = (TextView) findViewById(R.id.recovered_num);
        deathst = (TextView) findViewById(R.id.death_num);
        iconf = (TextView) findViewById(R.id.indconf);
        ideath = (TextView) findViewById(R.id.inddeath);
        wconf = (TextView) findViewById(R.id.worldconf);
        wdeath = (TextView) findViewById(R.id.worlddeath);

        call = (LinearLayout) findViewById(R.id.call);
        aware = (LinearLayout) findViewById(R.id.aware);
//        prevprods = (LinearLayout)findViewById(R.id.prevprods);
        travel = (LinearLayout) findViewById(R.id.travel);
        hospital = (LinearLayout) findViewById(R.id.hospital);
        faq = (LinearLayout) findViewById(R.id.faq);
//        massgather = (LinearLayout) findViewById(R.id.massgather);
        about = (LinearLayout) findViewById(R.id.about);
        notices = (LinearLayout) findViewById(R.id.notices);
        share = (LinearLayout) findViewById(R.id.share);
        feedback = (LinearLayout) findViewById(R.id.feedback);
        extnotice = (LinearLayout) findViewById(R.id.externalnotices);
//        videos = (LinearLayout) findViewById(R.id.videos);
        conf = (LinearLayout) findViewById(R.id.confirmed);
        symptoms = (Button) findViewById(R.id.symptomscheck);
        fluclinic = (LinearLayout) findViewById(R.id.fluclinics);
        quarantine = (LinearLayout) findViewById(R.id.quarentine);
        adm_msg = (LinearLayout) findViewById(R.id.adm_desk);
        hindimsg = (TextView) findViewById(R.id.hindiwala);



        chd = (TextView) findViewById(R.id.chd);
        india = (TextView) findViewById(R.id.india);
        chdhelp = (TextView) findViewById(R.id.chdhelp);
        indiahelp = (TextView) findViewById(R.id.indiahelp);


        chd.setShadowLayer(1, 0, 0, Color.BLACK);
        chdhelp.setShadowLayer(1, 0, 0, Color.BLACK);
        india.setShadowLayer(1, 0, 0, Color.BLACK);
        indiahelp.setShadowLayer(1, 0, 0, Color.BLACK);


        onclicklisteners();
        refreshPage();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.homepage_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
            case R.id.alert:
                startActivity(new Intent(Homepage.this,UTNotices.class));
                Toast.makeText(Homepage.this,"Fetching Notices...",Toast.LENGTH_SHORT).show();
                break;

            case R.id.refresh:
                Toast.makeText(Homepage.this,"Refreshing...",Toast.LENGTH_SHORT).show();
                refreshPage();

        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshPage() {

        suspectst.setText("-");
        confirmedt.setText("-");
        recoveredt.setText("-");
        deathst.setText("-");
        iconf.setText("-");
        ideath.setText("-");
        wconf.setText("-");
        wdeath.setText("-");

        getLocationPermissions();
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("suspects");
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        suspectst.setText(Integer.toString(dataSnapshot.getValue(Integer.class)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        final DatabaseReference mDatabase2;
        mDatabase2 = FirebaseDatabase.getInstance().getReference("confirmed");
        mDatabase2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        confirmedt.setText(Integer.toString(dataSnapshot.getValue(Integer.class)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        final DatabaseReference mDatabase3;
        mDatabase3 = FirebaseDatabase.getInstance().getReference("recovered");
        mDatabase3.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        recoveredt.setText(Integer.toString(dataSnapshot.getValue(Integer.class)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        final DatabaseReference mDatabase4;
        mDatabase4 = FirebaseDatabase.getInstance().getReference("deaths");
        mDatabase4.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        deathst.setText(Integer.toString(dataSnapshot.getValue(Integer.class)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        final DatabaseReference mDatabase5;
        mDatabase5 = FirebaseDatabase.getInstance().getReference("indiaconfirmed");
        mDatabase5.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        iconf.setText(Integer.toString(dataSnapshot.getValue(Integer.class))+"\nConfirmed");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        final DatabaseReference mDatabase6;
        mDatabase6 = FirebaseDatabase.getInstance().getReference("indiadeaths");
        mDatabase6.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ideath.setText(Integer.toString(dataSnapshot.getValue(Integer.class))+"\nDeaths");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        final DatabaseReference mDatabase7;
        mDatabase7 = FirebaseDatabase.getInstance().getReference("worldconfirmed");
        mDatabase7.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        wconf.setText(Integer.toString(dataSnapshot.getValue(Integer.class))+"\nConfirmed");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        final DatabaseReference mDatabase8;
        mDatabase8 = FirebaseDatabase.getInstance().getReference("worlddeaths");
        mDatabase8.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        wdeath.setText(Integer.toString(dataSnapshot.getValue(Integer.class))+"\nDeaths");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        final DatabaseReference mDatabase9;
        mDatabase9 = FirebaseDatabase.getInstance().getReference("confirmedcasepdf");
        mDatabase9.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pdflink = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    public void onclicklisteners() {
        coronaWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Homepage.this, "Redirecting", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Homepage.this, websiteView.class);
                intent.putExtra("url", "http://mygov.in/covid-19/");
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "112", null));
                startActivity(intent);
            }
        });

        aware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,Awareness.class));
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,Guidelines.class));
                Toast.makeText(Homepage.this,"Travel Guidelines Selected",Toast.LENGTH_SHORT).show();
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,hospitals.class));
            }
        });
        quarantine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, quarantine.class);
                startActivity(intent);
            }
        });
        fluclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, fluclinics.class);
                startActivity(intent);
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = "+91 9013151515";
                String url = "https://api.whatsapp.com/send?phone="+number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        chd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "112", null));
                startActivity(intent);
            }
        });

        india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "1075", null));
                startActivity(intent);
            }
        });

        chdhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "9779558282", null));
                startActivity(intent);
            }
        });

        indiahelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "1123978046", null));
                startActivity(intent);
            }
        });



//        massgather.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Homepage.this, report_gather.class);
//                startActivity(intent);
//
//            }
//        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,about.class));
            }
        });


        notices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this,UTNotices.class);
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String message = "Chandigarh Administration (UT), Chandigarh is taking all precautions and essential steps to ensure the safety of people. UT Administration is prepared with necessary measures to face the challenge and threat caused by the growing pandemic of COVID-19 (CoronaVirus). The extremely important factor in preventing the spread of the Corona Virus is to encourage the citizens with the right information and taking precautions as per the advisories and information being issued by  Health department,  Chandigarh Administration (UT) as well as Ministry of Health &amp; Family Welfare, GOI."
                            +"\n\nWith this approach, The Department of Technical Education, Chandigarh (UT) Administration, has taken responsibility to design this Mobile App in association with CSE Department, CCET (Degree Wing) Chandigarh that would help to provide real time information about precaution and prevention of spread of CoronaVirus in Chandigarh."
                            +"\n\n Download the Application from : ";
                    message = message + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    Toast.makeText(Homepage.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + Homepage.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Homepage.this.getPackageName())));
                }
            }
        });

//        videos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Homepage.this,"No Data Provided !",Toast.LENGTH_SHORT).show();
//            }
//        });

        extnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Homepage.this,"Redirecting !",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Homepage.this,websiteView.class);
                i.putExtra("url","https://www.mohfw.gov.in/");
                startActivity(i);

            }
        });

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Homepage.this,websiteView.class);
//                i.putExtra("url","https://docs.google.com/viewerng/viewer?url="+pdflink);
//                startActivity(i);

                Intent Getintent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://chandigarh.gov.in/pdf/mc20-listqrnt.pdf"));
                startActivity(Getintent);
            }
        });

        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,Symptoms.class));
            }
        });

        adm_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,adminDesk.class));
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        getNodesLocationAndMap();

        if (permissionGrant) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gMap.setMyLocationEnabled(true);
            gMap.setBuildingsEnabled(true);
            gMap.getUiSettings().setAllGesturesEnabled(true);
            gMap.getUiSettings().setRotateGesturesEnabled(true);
            gMap.getUiSettings().setZoomControlsEnabled(true);
            gMap.getUiSettings().setCompassEnabled(true);
            gMap.getUiSettings().setTiltGesturesEnabled(true);
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            gMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int i) {
                    ScrollView scrollView = (ScrollView) findViewById(R.id.scrollviewhome);
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            });
        }else{
            getLocationPermissions();
        }
    }

    private void getNodesLocationAndMap() {

        DatabaseReference hospListReference = FirebaseDatabase.getInstance().getReference().child("hospitals");
        hospListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();

                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("hospitals").child(key);
                    keyReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            double lat = dataSnapshot.child("latitude").getValue(Double.class);
                            double lon = dataSnapshot.child("longitude").getValue(Double.class);
                            String address = dataSnapshot.child("address").getValue(String.class);


                            LatLng latLng1 =new LatLng(lat,lon);
                            MarkerOptions options = new MarkerOptions()
                                    .title(name+" "+address)
                                    .position(latLng1)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                            gMap.addMarker(options);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "Read failed");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Read failed");
            }
        });

        DatabaseReference quarListReference = FirebaseDatabase.getInstance().getReference().child("quarantine");
        quarListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();

                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("quarantine").child(key);
                    keyReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            double lat = dataSnapshot.child("latitude").getValue(Double.class);
                            double lon = dataSnapshot.child("longitude").getValue(Double.class);
                            String address = dataSnapshot.child("address").getValue(String.class);


                            LatLng latLng1 =new LatLng(lat,lon);
                            MarkerOptions options = new MarkerOptions()
                                    .title(name+" "+address)
                                    .position(latLng1)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            gMap.addMarker(options);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "Read failed");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Read failed");
            }
        });

        DatabaseReference fluListReference = FirebaseDatabase.getInstance().getReference().child("fluclinics");
        fluListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();

                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("fluclinics").child(key);
                    keyReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            double lat = dataSnapshot.child("latitude").getValue(Double.class);
                            double lon = dataSnapshot.child("longitude").getValue(Double.class);
                            String address = dataSnapshot.child("address").getValue(String.class);
                            LatLng latLng1 =new LatLng(lat,lon);
                            MarkerOptions options = new MarkerOptions()
                                    .title(name+" "+address)
                                    .position(latLng1)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                            gMap.addMarker(options);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "Read failed");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Read failed");
            }
        });


    }

    private void getLocationPermissions(){
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOC) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOC)== PackageManager.PERMISSION_GRANTED){
                permissionGrant = true;
                initializeMap();
            }else {
                ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"onRequestPermissionResult: called");
        permissionGrant = false;
        switch (requestCode){
            case REQUEST_CODE:{
                if (grantResults.length>0){

                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            permissionGrant = false;
                            Log.d(TAG,"onRequestPermissionResult: permission failed !");
                            return;
                        }
                    }
                    permissionGrant = true;
                    initializeMap();
                }
            }
        }
    }


    private void initializeMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Homepage.this);
    }

    private void getDeviceLocation(){

        LatLng latLng = new LatLng(30.739175, 76.774311);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));

    }



}
