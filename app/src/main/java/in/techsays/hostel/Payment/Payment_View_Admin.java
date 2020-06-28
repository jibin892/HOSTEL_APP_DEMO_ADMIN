package in.techsays.hostel.Payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.techsays.hostel.Adapter.Homelist;
import in.techsays.hostel.Adapter.Payment_Adapter;
import in.techsays.hostel.Adapter.Payment_Admin_View_ViewHolder;
import in.techsays.hostel.R;

public class Payment_View_Admin extends AppCompatActivity {
    LinearLayoutManager mLayoutManager; //for sorting
    SharedPreferences mSharedPref; //for saving sort settings
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    ShimmerFrameLayout mShimmerViewContainer;
    String amounttotal;
    private BottomSheetDialog mBottomSheetDialog;
    TextView todayfuulammount;
    ImageButton todayscolluctionadninview;
    EditText et_search;
    String  start,end;
    View view;
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
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mShimmerViewContainer =  findViewById(R.id.shimmer_view_containerpaymentadminview);
mShimmerViewContainer.startShimmer();
        //RecyclerView
        todayscolluctionadninview  = findViewById(R.id.todayscolluctionadninview);
        et_search = findViewById(R.id.et_searchadmibviewpayment);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(mLayoutManager);

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("payment");


        et_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = et_search.getText().toString();

                Query firebaseSearchQuery = mRef.orderByChild("personName").startAt(query).endAt(query + "\uf8ff");

                FirebaseRecyclerAdapter<Payment_Adapter, Payment_Admin_View_ViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<Payment_Adapter, Payment_Admin_View_ViewHolder>(
                                Payment_Adapter.class,
                                R.layout.payment_list_admin_view,
                                Payment_Admin_View_ViewHolder.class,
                                firebaseSearchQuery
                        ) {
                            @Override
                            protected void populateViewHolder(Payment_Admin_View_ViewHolder viewHolder, Payment_Adapter model, int position) {
                                viewHolder.setDetails(getApplicationContext(), model.getPersonName(), model.getAmmount(), model.getPersonPhoto(), model.getRoomnumber(), model.getPayment_Method(),
                                        model.getTransaction_id(), model.getPersonEmail(), model.getPaymentTime(), model.getPaymentdate());

                            }
                                @Override
                            public Payment_Admin_View_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                Payment_Admin_View_ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);


                                return viewHolder;
                            }


                        };

                //set adapter to recyclerview
                mRecyclerView.setAdapter(firebaseRecyclerAdapter);
            }
        });
        todayscolluctionadninview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadtodaypayment();
            }
        });



    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Payment_Adapter, Payment_Admin_View_ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Payment_Adapter, Payment_Admin_View_ViewHolder>(
                        Payment_Adapter.class,
                        R.layout.payment_list_admin_view,
                        Payment_Admin_View_ViewHolder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(Payment_Admin_View_ViewHolder viewHolder, Payment_Adapter model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getPersonName(), model.getAmmount(), model.getPersonPhoto(),model.getRoomnumber(),model.getPayment_Method(),
                                model.getTransaction_id(),model.getPersonEmail(),model.getPaymentTime(),model.getPaymentdate());

//
//                        if(model.getAmmount()==null){
//
//                            mShimmerViewContainer.setVisibility(View.VISIBLE);
//                            mShimmerViewContainer.startShimmer();
//
//                        }
//                        else {
//
//                            mShimmerViewContainer.setVisibility(View.INVISIBLE);
//                            mShimmerViewContainer.stopShimmer();
//                        }





                    }

                    @Override
                    public Payment_Admin_View_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        Payment_Admin_View_ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);



                        return viewHolder;
                    }

                };
        mRef.addValueEventListener(new ValueEventListener() {
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




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    private void loadtodaypayment() {

        view = getLayoutInflater().inflate(R.layout.botamsheet_total_amount, null);
        todayfuulammount = view.findViewById(R.id.todayfuulammount1);
        todayfuulammount.setText("Rs:" + String.valueOf(amounttotal));

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






