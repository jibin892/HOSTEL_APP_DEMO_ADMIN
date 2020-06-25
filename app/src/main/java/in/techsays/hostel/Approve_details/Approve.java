package in.techsays.hostel.Approve_details;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import in.techsays.hostel.Home.Home_main;
import in.techsays.hostel.Login_and_Registration.Login;
import in.techsays.hostel.R;

public class Approve extends AppCompatActivity {
TextView approvename,regregisternowadmin,approvebuldingname,approveroomnumber,approvebednumber,approvreamount;
ImageView approveimage;
Intent approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }

        setContentView(R.layout.activity_approve);
        approve=getIntent();
        approveimage = findViewById(R.id.approveimage);
        approvename = findViewById(R.id.approvename);

        approvebuldingname = findViewById(R.id.approvebuldingname);
        approveroomnumber = findViewById(R.id.approveroomnumber);
        approvebednumber = findViewById(R.id.approvebednumber);
        approvreamount = findViewById(R.id.approvreamount);



        Picasso.get().load(approve.getStringExtra("profileimage")).into(approveimage);

        approvename.setText(approve.getStringExtra("name"));

        regregisternowadmin = findViewById(R.id.regregisternowadmin);





        regregisternowadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progress = new ProgressDialog(Approve.this);
                progress.setTitle("please wait");
                progress.setMessage("Wait a Moment...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();


                DatabaseReference object = FirebaseDatabase.getInstance().getReference();
                String uid = approve.getStringExtra("uid");

                DatabaseReference namesRef = object.child("adminapproved").child(uid);
                Map<String, Object> map = new HashMap<>();
                map.put("Adhaarcard_frentside_image", approve.getStringExtra("adharfrend"));
                map.put("aAhaarcard_backside_image", approve.getStringExtra("adharback"));
                map.put("Profile_image", approve.getStringExtra("profileimage"));
                map.put("Room_Number", approveroomnumber.getText().toString());
                map.put("Buliding_Name", approvebuldingname.getText().toString());
                map.put("Bed_Number", approvebednumber.getText().toString());
                map.put("Uid", approve.getStringExtra("uid"));
                map.put("Rent_Amount", approvreamount.getText().toString());
                map.put("Name", approve.getStringExtra("name"));
                map.put("Phone",approve.getStringExtra("phone"));
                map.put("Home_phome", approve.getStringExtra("homephone"));
                map.put("Email", approve.getStringExtra("email"));
                map.put("Address", approve.getStringExtra("address"));
                map.put("Date",approve.getStringExtra("date"));
                map.put("Staying_puropse",approve.getStringExtra("stayingpurpouse"));
                map.put("Age", approve.getStringExtra("age"));
                map.put("Passwored", approve.getStringExtra("pass"));
                map.put("YearOfBirth", approve.getStringExtra("dob"));
                map.put("Adhaar_cared_name", approve.getStringExtra("adhaarname"));
                map.put("Adhaar_cared_number", approve.getStringExtra("adharcarednumber"));
                map.put("Village", approve.getStringExtra("village"));
                map.put("State",approve.getStringExtra("state"));
                map.put("District", approve.getStringExtra("dis"));
                map.put("Gender",approve.getStringExtra("gender"));
                map.put("Postel_code", approve.getStringExtra("postcode"));
                namesRef.updateChildren(map);
                object.child("adminapproved");


                object.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("registration").orderByChild("Uid").equalTo(approve.getStringExtra("uid"));

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                                    appleSnapshot.getRef().removeValue();
                                    progress.dismiss();
                                    shodilog();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                             }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });

    }
    private void shodilog() {
        final Dialog dialog;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dilog_apporve);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.findViewById(R.id.gotomail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent back= new Intent(getApplicationContext(), Home_main.class);
              startActivity(back);
            }
        });

        dialog.findViewById(R.id.btclosedilogreset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}