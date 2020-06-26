package in.techsays.hostel.Payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.techsays.hostel.Adapter.Homelist;
import in.techsays.hostel.Adapter.Payment_Adapter;
import in.techsays.hostel.R;

public class Payment_View_Admin extends AppCompatActivity {
    FirebaseListAdapter<Payment_Adapter> adapter;
    ListView notificationlist;
    Query reference;
    private BottomSheetDialog mBottomSheetDialog;
    private ShimmerFrameLayout mShimmerViewContainer;
    final int RequestPermissionCode=1;
    ArrayAdapter<String> arrayAdapter;
    public static final int DIALOG_QUEST_CODE = 300;
    SharedPreferences roomnumber,sh;
    EditText et_searcfh;
    TextView todayfuulammount;
    // int sum ;
    View view;
    String amounttotal;
    ImageView todayscolluction;
    String cugrrentDay;
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
        setContentView(R.layout.activity_payment__view__admin);
        notificationlist =  findViewById(R.id.userpaymentlistadminview);
        mShimmerViewContainer =  findViewById(R.id.shimmer_view_containerpaymentadminview);
        mShimmerViewContainer.startShimmer();
        et_searcfh= findViewById(R.id.et_search);
        todayscolluction= findViewById(R.id.todayscolluctionadminview);

        todayscolluction.setVisibility(View.INVISIBLE);
        sh =  getSharedPreferences("userdata", MODE_PRIVATE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat gsdf = new SimpleDateFormat("dd");
        cugrrentDay = String.valueOf(gsdf.format(new Date()));


        displayNotifications();



        todayscolluction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadtodaypayment();
            }


        });



    }
    private void displayNotifications() {
        adapter = new FirebaseListAdapter<Payment_Adapter>(this, Payment_Adapter.class,
                R.layout.payment_list_admin_view,
                reference = FirebaseDatabase.getInstance().getReference().child("payment")) {

            @Override
            protected void populateView(View v, final Payment_Adapter model, int position) {

                final TextView paymentname = (TextView) v.findViewById(R.id.paymentnameadminview);
                final ImageView paymentimage = v.findViewById(R.id.paymentimageadminview);
                final TextView paymentemail = (TextView) v.findViewById(R.id.paymentemailadminview);
                final TextView paymenttransactionid = (TextView) v.findViewById(R.id.paymenttransactionidadminview);
                final TextView paymentammount = (TextView) v.findViewById(R.id.paymentammountadminview);
                final TextView paymenttime = (TextView) v.findViewById(R.id.paymenttimeadminview);
                final TextView paymentdate = (TextView) v.findViewById(R.id.paymentdateadminview);
                final TextView roomnumberpayment = (TextView) v.findViewById(R.id.roomnumberpaymentadminview);


                if(model.getAmmount()==null)
                {
                    todayscolluction.setVisibility(View.INVISIBLE);
                }
                else {
                    todayscolluction.setVisibility(View.VISIBLE);
                }

                paymentname.setText(model.getPersonName());
                Picasso.get().load(model.getPersonPhoto()).into(paymentimage);
                paymentemail.setText(model.getPersonName());
                paymenttransactionid.setText(model.getTransaction_id());
                paymentammount.setText(model.getAmmount());
                paymenttime.setText(model.getPaymentTime());
                paymentdate.setText(model.getPaymentdate());
                roomnumberpayment.setText(model.getRoomnumber());






                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                        if(dataSnapshot.exists()){
//
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.INVISIBLE);



                            int  sum=0;

                            for (DataSnapshot ds:dataSnapshot.getChildren()
                            ) {
                                Payment_Adapter p=ds.getValue(Payment_Adapter.class);


                                int am=Integer.parseInt(p.getAmmount());
                                sum=sum+am;
                                amounttotal=String.valueOf(sum);



                            }
                        }


                        else {
                            mShimmerViewContainer.startShimmer();
                            mShimmerViewContainer.setVisibility(View.VISIBLE);
                            todayscolluction.setVisibility(View.INVISIBLE);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                //  }




            }



        };


        notificationlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();





    }

    private void loadtodaypayment() {

        view = getLayoutInflater().inflate(R.layout.botamsheet_todaymmount, null);
        todayfuulammount=view.findViewById(R.id.todayfuulammount);
        todayfuulammount.setText(String.valueOf(amounttotal));

        (view.findViewById(R.id.close123)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.hide();
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

}