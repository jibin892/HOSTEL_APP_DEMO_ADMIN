package in.techsays.hostel.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.techsays.hostel.Adapter.Homelist;
import in.techsays.hostel.Adapter.Payment_Adapter;
import in.techsays.hostel.Animation.ViewAnimation;
import in.techsays.hostel.Login_and_Registration.User;
import in.techsays.hostel.R;

import static android.content.Context.MODE_PRIVATE;


public class TodaypaymentFragment extends Fragment {
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_todaypayment, container, false);
        notificationlist = root.findViewById(R.id.userpaymentlist);
        mShimmerViewContainer = root.findViewById(R.id.shimmer_view_containerpayment);
        mShimmerViewContainer.startShimmer();
        et_searcfh=root.findViewById(R.id.et_search);
        todayscolluction=root.findViewById(R.id.todayscolluction);

        todayscolluction.setVisibility(View.INVISIBLE);
        sh = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat gsdf = new SimpleDateFormat("dd");
        cugrrentDay = String.valueOf(gsdf.format(new Date()));


        displayNotifications();



        todayscolluction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadtodaypayment();
            }


        });


        return root;
    }
    private void displayNotifications() {
        adapter = new FirebaseListAdapter<Payment_Adapter>(getActivity(), Payment_Adapter.class,
                R.layout.payment_list,
                reference = FirebaseDatabase.getInstance().getReference().child("payment").orderByChild("Day").equalTo(cugrrentDay)) {

             @Override
            protected void populateView(View v, final Payment_Adapter model, int position) {

                final TextView paymentname = (TextView) v.findViewById(R.id.paymentname);
                final ImageView paymentimage = v.findViewById(R.id.paymentimage);
                final TextView paymentemail = (TextView) v.findViewById(R.id.paymentemail);
                final TextView paymenttransactionid = (TextView) v.findViewById(R.id.paymenttransactionid);
                final TextView paymentammount = (TextView) v.findViewById(R.id.paymentammount);
                final TextView paymenttime = (TextView) v.findViewById(R.id.paymenttime);
                final TextView paymentdate = (TextView) v.findViewById(R.id.paymentdate);
                 final TextView roomnumberpayment = (TextView) v.findViewById(R.id.roomnumberpayment);


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
               // roomnumberpayment.setText(model.getRoomnumber());






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

                                        Toast.makeText(mActivity, String.valueOf(sum), Toast.LENGTH_SHORT).show();


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




        mBottomSheetDialog = new BottomSheetDialog(getActivity());
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