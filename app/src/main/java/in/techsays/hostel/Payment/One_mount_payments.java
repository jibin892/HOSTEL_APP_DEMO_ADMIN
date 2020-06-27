package in.techsays.hostel.Payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;

import in.techsays.hostel.Adapter.Payment_Adapter;
import in.techsays.hostel.R;

import static java.util.Calendar.MONTH;

public class One_mount_payments extends AppCompatActivity {
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
    String  start,end;
    View view;
    String amounttotal;
    ImageButton todayscolluction,searchdate;
    String cugrrentDay;
    EditText et_searchsatrtdate;
     TextView   startdate,enddate,et_searchend;
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
        setContentView(R.layout.activity_one_mount_payments);
         notificationlist =  findViewById(R.id.onemountpaymentslist);
        mShimmerViewContainer =  findViewById(R.id.shimmer_view_onemountpayment);
        mShimmerViewContainer.startShimmer();
        et_searcfh= findViewById(R.id.et_search);
        todayscolluction= findViewById(R.id.onemounthpayment);
        et_searchsatrtdate= findViewById(R.id.et_searchsatrtdate);
        et_searchend= findViewById(R.id.et_searchend);

        searchdate= findViewById(R.id.searchdate);


        todayscolluction.setVisibility(View.INVISIBLE);
        sh =  getSharedPreferences("userdata", MODE_PRIVATE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat gsdf = new SimpleDateFormat("dd");
        cugrrentDay = String.valueOf(gsdf.format(new Date()));
        todayscolluction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadtodaypayment();
            }


        });


        et_searchsatrtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog  datePickerDialog = new DatePickerDialog(One_mount_payments.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                et_searchsatrtdate.setText(dayOfMonth + "-"+ (monthOfYear + 1) + "-" + year);
                             }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        et_searchend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYeare = c.get(Calendar.YEAR); // current year
                int mMonthe = c.get(Calendar.MONTH); // current month
                int mDaye = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog  datePickerDialoge = new DatePickerDialog(One_mount_payments.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                et_searchend.setText(dayOfMonth + "-"+ (monthOfYear + 1) + "-" + year);

                            }
                        }, mYeare, mMonthe, mDaye);
                datePickerDialoge.show();
            }
        });


        searchdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotifications();

            }
        });

    }






    private void displayNotifications() {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MONTH, 0);
//        cal.set(Calendar.DATE, 1);
//        Date firstDateOfPreviousMonth = cal.getTime();
//
//        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal
//
//        Date lastDateOfPreviousMonth = cal.getTime();
//
//        String st=String.valueOf(firstDateOfPreviousMonth);
//        String en=String.valueOf(lastDateOfPreviousMonth);

        adapter = new FirebaseListAdapter<Payment_Adapter>(this, Payment_Adapter.class,
                R.layout.one_mounth_pay,
                reference = FirebaseDatabase.getInstance().getReference().child("payment").orderByChild("Date").startAt(et_searchsatrtdate.getText().toString()).endAt( et_searchend.getText().toString())) {

            @Override
            protected void populateView(View v, final Payment_Adapter model, int position) {

                final TextView paymentname = (TextView) v.findViewById(R.id.paymentnameadminviewonemounth);
                final ImageView paymentimage = v.findViewById(R.id.paymentimageadminviewonemounth);
                final TextView paymentemail = (TextView) v.findViewById(R.id.paymentemailadminviewonemounth);
                final TextView paymenttransactionid = (TextView) v.findViewById(R.id.paymenttransactionidadminviewonemounth);
                final TextView paymentammount = (TextView) v.findViewById(R.id.paymentammountadminviewonemounth);
                final TextView paymenttime = (TextView) v.findViewById(R.id.paymenttimeadminviewonemounth);
                final TextView paymentdate = (TextView) v.findViewById(R.id.paymentdateadminviewonemounth);
                final TextView roomnumberpayment = (TextView) v.findViewById(R.id.roomnumberpaymentadminviewonemounth);

                final TextView todaypaymetmethedtext = (TextView) v.findViewById(R.id.adminviespaymentononemounth);

                final LinearLayout todaypaymentmethod = (LinearLayout) v.findViewById(R.id.adminviewpaymetslonemounth);




                if (model.getPayment_Method().contains("Cash Payment")){

                    todaypaymentmethod.setBackgroundColor(getResources().getColor(R.color.red_A700));

                    todaypaymetmethedtext.setText(model.getPayment_Method());

                }
                else {
                    todaypaymentmethod.setBackgroundColor(getResources().getColor(R.color.green_A700));
                    todaypaymetmethedtext.setText(model.getPayment_Method());

                }

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

        view = getLayoutInflater().inflate(R.layout.botamsheet_total_amount, null);
        todayfuulammount=view.findViewById(R.id.todayfuulammount1);
        todayfuulammount.setText("Rs:"+String.valueOf(amounttotal));

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