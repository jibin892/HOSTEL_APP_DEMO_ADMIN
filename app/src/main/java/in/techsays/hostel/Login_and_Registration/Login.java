package in.techsays.hostel.Login_and_Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

 import in.techsays.hostel.Home.Home_main;
import in.techsays.hostel.R;


public class Login extends AppCompatActivity {
Button loginbutton;
LinearLayout loginhome;
      ProgressDialog progress;
    EditText username,passwored;
 TextView loginadmin;
    String usernameenter;
    private boolean loggedIn = false;
    SharedPreferences log,sharedPreferences;
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
        setContentView(R.layout.activity_login);
        log=getSharedPreferences("log",MODE_PRIVATE);
        loggedIn=log.getBoolean("id",false);
        sharedPreferences=getSharedPreferences("phone",MODE_PRIVATE);
        if (loggedIn) {
            startActivity(new Intent(getApplicationContext(),Home_main.class));
            // Snackbar.make(v,"Enter emergency number",Snackbar.LENGTH_SHORT).show();

        }
       username=findViewById(R.id.Usernamelogin);
         loginadmin=findViewById(R.id.loginadmin);
        loginhome=findViewById(R.id.loginhome);

          usernameenter=username.getText().toString();




        loginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(username.getText().toString().isEmpty()){

                    username.setError("Field is Empty");
                }




                else {


                    progress = new ProgressDialog(Login.this);
                    progress.setMessage("Wait a Moment...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                    final DatabaseReference users = FirebaseDatabase.getInstance().getReference("adminlogin");
                    Query query = users.orderByChild("Username").equalTo(username.getText().toString());
                    final List<Adminlogin> user = new ArrayList<>();
                    query.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if(dataSnapshot.exists()) {
                                user.add(dataSnapshot.getValue(Adminlogin.class));
                                Adminlogin userlogin = dataSnapshot.getValue(Adminlogin.class);
                                String Uid = userlogin.getUsername().toString();
                                String pass = userlogin.getPasswored().toString();
                                Intent loginintent = new Intent(getApplicationContext(), Passwored.class);
                                final SharedPreferences sh = getSharedPreferences("adminlogincheck", MODE_PRIVATE);
                                SharedPreferences.Editor ee = sh.edit();
                                ee.putString("passwored", pass);
                                ee.apply();
                                progress.dismiss();
                                startActivity(loginintent);
                            }
                            else {

                                progress.dismiss();
                                snackBarloginerror();
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                            snackBarloginerror();


                        }
                    });


                }
            }
        });



    }


    private void snackBarloginerror() {
        final Snackbar snackbar = Snackbar.make(loginhome, "", Snackbar.LENGTH_LONG);
        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.snackbar_wrong_msg, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding(0, 0, 0, 0);
        (custom_view.findViewById(R.id.tv_undo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
             }
        });

        snackBarView.addView(custom_view, 0);
        snackbar.show();
    }

        }
