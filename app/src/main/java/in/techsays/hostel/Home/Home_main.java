package in.techsays.hostel.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.techsays.hostel.About_Us.About;
import in.techsays.hostel.Contact_Us.Contact;
import in.techsays.hostel.Location.Location_Home;
import in.techsays.hostel.Login_and_Registration.Login;
import in.techsays.hostel.Login_and_Registration.User;
import in.techsays.hostel.R;
import in.techsays.hostel.Settings.Settings_home;

public class Home_main extends AppCompatActivity {
    ImageButton navbtn;
    DrawerLayout drawer;
    private BottomSheetDialog mBottomSheetDialog;
    SharedPreferences sh;
    FirebaseAuth mauth;
    ImageView homeimage,bottamshettlogoutimage;
    String profilepic,name,email;
     ImageView imagenavigation;
     TextView navigationemail,navigationname;
    SharedPreferences she;

    TextView botamshettlogoutname,botamshettlogoutemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }
        setContentView(R.layout.navigation);
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
        EnableRuntimePermission();
         she=getSharedPreferences("log",MODE_PRIVATE);
        SharedPreferences.Editor e=she.edit();
        e.putBoolean("id",true);
        e.apply();
            BottomNavigationView navView = findViewById(R.id.bottamnavigation);
        homeimage = findViewById(R.id.homeimage);
        imagenavigation = findViewById(R.id.imagenavigation);
        navigationname = findViewById(R.id.navigationname);
        navigationemail = findViewById(R.id.navigationemail);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_Payment,R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragmenthome);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        homeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imageshobottamsheet();
            }
        });


        drawer = findViewById(R.id.drawer_layout);
        navbtn =   findViewById(R.id.navbtn);
        navbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_home);
        View headerView = navigationView.getHeaderView(0);








    }

    private void Imageshobottamsheet() {


        final View view = getLayoutInflater().inflate(R.layout.botamsheet_logot, null);
//        ((TextView) view.findViewById(R.id.name)).setText( "name");
        bottamshettlogoutimage=view.findViewById(R.id.bottamshettlogoutimage);
        botamshettlogoutname=(TextView) view.findViewById(R.id.botamshettlogoutname);
        botamshettlogoutemail=(TextView) view.findViewById(R.id.botamshettlogoutemail);


        (view.findViewById(R.id.homeclosbottamsheet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.hide();
            }
        });

        (view.findViewById(R.id.bt_closehome)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.hide();

            }
        });
        (view.findViewById(R.id.homelogoutbottamsheet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences.Editor e = sh.edit();
                e.clear();
                e.apply();
                startActivity(new Intent(Home_main.this, Login.class));

            }
        });

        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        // set background transparent
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

    }







    public void navigationclick(View v) {
        // Handle navigation view item clicks here.
        int id = v.getId();
        if (id == R.id.homenavlogout) {

            Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.locatinhome) {

            Intent i = new Intent(Home_main.this, Location_Home.class);
            startActivity(i);
        }
        if (id == R.id.contacthome) {

            Intent i1 = new Intent(Home_main.this, Contact.class);
            startActivity(i1);
        }
        if (id == R.id.settingshome) {

            Intent i2 = new Intent(Home_main.this, Settings_home.class);
            startActivity(i2);
        }
        if (id == R.id.abouthome) {

            Intent i3 = new Intent(Home_main.this, About.class);
            startActivity(i3);
        }
        if (id == R.id.homenavlogout) {

            SharedPreferences.Editor e = she.edit();
            e.clear();
            e.apply();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

        drawer.closeDrawer(Gravity.LEFT);
     }


    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(this),
                Manifest.permission.CAMERA)) {

// Toast.makeText(Cpature_image.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Home_main.this, new String[]{
                    Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 12);


        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case 12:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

// Toast.makeText(Cpature_image.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(Home_main.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }}




}

