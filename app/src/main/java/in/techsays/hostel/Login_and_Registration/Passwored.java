package in.techsays.hostel.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.techsays.hostel.Home.Home_main;
import in.techsays.hostel.R;

public class Passwored extends AppCompatActivity {

    Button loginbutton;
    LinearLayout loginhome;
    ProgressDialog progress;
    EditText passwored;
     TextView loginadmin;

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
        setContentView(R.layout.activity_passwored);




         passwored=findViewById(R.id.Passworedlogin);
         loginadmin=findViewById(R.id.loginadminpass);
         loginhome=findViewById(R.id.loginhomepass);





        loginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(passwored.getText().toString().isEmpty()){

                    passwored.setError("Field is Empty");
                }




                else {

                    progress = new ProgressDialog(Passwored.this);
                    progress.setMessage("Wait a Moment...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                    SharedPreferences adminlog=getSharedPreferences("adminlogincheck",MODE_PRIVATE);

                    String pass= adminlog.getString("passwored",null);


                    if(pass.equals(passwored.getText().toString())){

                      Intent login=new Intent(getApplicationContext(),Home_main.class);
                      startActivity(login);


                    }
                    else {
                         progress.dismiss();
                        snackBarloginerror();
                    }


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
