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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import in.techsays.hostel.Adapter.Homelist;
import in.techsays.hostel.Login_and_Registration.User;
import in.techsays.hostel.Payment.Payment_view_user;
import in.techsays.hostel.R;

import static android.content.Context.MODE_PRIVATE;


public class Due_date_Today_Fragment extends Fragment  {
    FirebaseListAdapter<Homelist> adapter;
    ListView notificationlist;
    Query reference;
    LinearLayout view;
    private BottomSheetDialog mBottomSheetDialog;
    ImageView homeimageview,homeviewprofileimage;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView homenameview,homeemailview,homeviewprofilename,homeviewprofileemail;
    final int RequestPermissionCode=1;
    ArrayAdapter<String> arrayAdapter;
    public static final int DIALOG_QUEST_CODE = 300;
    SharedPreferences roomnumber,sh;
    EditText et_searcfh;
     TextView profilename,profileemail,profilephonenumber,profilehomephonenumber,profileroomnumber,profiledate ;
    String cugrrentDay;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_duedate, container, false);

        notificationlist = root.findViewById(R.id.userlisduedate);
        mShimmerViewContainer = root.findViewById(R.id.shimmer_view_container12);
        mShimmerViewContainer.startShimmer();
        et_searcfh=root.findViewById(R.id.et_search);

        sh = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat gsdf = new SimpleDateFormat("dd");
          cugrrentDay = String.valueOf(gsdf.format(new Date()));



         displayNotifications();
        return root;
    }
    private void displayNotifications() {
        adapter = new FirebaseListAdapter<Homelist>(getActivity(), Homelist.class,
                R.layout.user_list_duedate,
                reference = FirebaseDatabase.getInstance().getReference().child("adminapproved").orderByChild("Day").equalTo(cugrrentDay)) {
            @Override
            protected void populateView(View v, final Homelist model, int position) {

                final TextView adminusrname = (TextView) v.findViewById(R.id.adminusrname1);
                final ImageView adminusrimg = v.findViewById(R.id.adminusrimg1);
                final TextView adminusrroomnumber = (TextView) v.findViewById(R.id.adminusrroomnumber1);
                final TextView adminusremail = (TextView) v.findViewById(R.id.adminusremail1);
                final FloatingActionButton morehomelist = (FloatingActionButton) v.findViewById(R.id.morehomelist1);




                adminusrname.setText(model.getName());
                Picasso.get().load(model.getProfile_image()).into(adminusrimg);
                adminusrroomnumber.setText(model.getRoom_Number());
                adminusremail.setText(model.getEmail());
                morehomelist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Imageshobottamsheet();
                    }
                    private void Imageshobottamsheet() {


                        final View view = getLayoutInflater().inflate(R.layout.user_more_list_duedate, null);



                        ;
                        homeimageview=view.findViewById(R.id.bottamshetthometimage1);
                        homenameview=(TextView) view.findViewById(R.id.botamshetthometname1);
                        homeemailview=(TextView) view.findViewById(R.id.botamshetthometemail1);
                        Picasso.get().load(model.getProfile_image()).into(homeimageview);

                         profilehomephonenumber=view.findViewById(R.id.profilehomephonenumber11);
                        profileroomnumber=view.findViewById(R.id.profileroomnumber11);
                        profiledate=view.findViewById(R.id.profiledate11);

                        profilephonenumber=view.findViewById(R.id.profilephonenumber11);


                        profilename=view.findViewById(R.id.profilename11);
                        profileemail=view.findViewById(R.id.profileemail11);






                        profilephonenumber.setText(model.getPhone());
                        profilehomephonenumber.setText(model.getHome_phome());
                        profileroomnumber.setText(model.getRoom_Number());
                        profiledate.setText(model.getDate());

                        profilename.setText(model.getName());
                        profileemail.setText(model.getEmail());
                        homenameview.setText(model.getName());
                        homeemailview.setText(model.getEmail());



                        (view.findViewById(R.id.viewdueammount)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Intent i = new Intent(getActivity(), Payment_view_user.class);
                                i.putExtra("uid",model.getUid());
                                startActivity(i);

                            }
                        });



                        (view.findViewById(R.id.callnoehome11)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + model.getPhone()));
                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                startActivity(callIntent);
                            }
                        });
                        (view.findViewById(R.id.callhomephonumerd)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + model.getHome_phome()));
                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                startActivity(callIntent);
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
                });




                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
//
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.INVISIBLE);

                        }
                        else {



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



}