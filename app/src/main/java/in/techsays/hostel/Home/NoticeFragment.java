package in.techsays.hostel.Home;

import android.Manifest;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.techsays.hostel.Adapter.Homelist;
import in.techsays.hostel.R;

import static android.content.Context.MODE_PRIVATE;


public class NoticeFragment extends Fragment {

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
    ImageView   profileadharback,profileadharfrend  ;
     TextView profilename,profileemail,profilephonenumber,profilehomephonenumber,profileroomnumber,profiledate,profileaddress,
            profilesadharrcarennumber,profilevillage,profiledistrict,profilesate,profilepatcode;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notice, container, false);

        notificationlist = root.findViewById(R.id.userlis);
        mShimmerViewContainer = root.findViewById(R.id.shimmer_view_container1);
        mShimmerViewContainer.startShimmer();
et_searcfh=root.findViewById(R.id.et_search);

         sh = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);

        displayNotifications();
        return root;
    }
    private void displayNotifications() {
        adapter = new FirebaseListAdapter<Homelist>(getActivity(), Homelist.class,
                R.layout.user_list,
                reference = FirebaseDatabase.getInstance().getReference().child("adminapproved")) {
            @Override
            protected void populateView(View v, final Homelist model, int position) {

                final TextView adminusrname = (TextView) v.findViewById(R.id.adminusrname);
                final ImageView adminusrimg = v.findViewById(R.id.adminusrimg);
                final TextView adminusrroomnumber = (TextView) v.findViewById(R.id.adminusrroomnumber);
                final TextView adminusremail = (TextView) v.findViewById(R.id.adminusremail);
                final ImageButton morehomelist = (ImageButton) v.findViewById(R.id.morehomelist);




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


                        final View view = getLayoutInflater().inflate(R.layout.user_more_list, null);



                         ;
                        homeimageview=view.findViewById(R.id.bottamshetthometimage);
                        homenameview=(TextView) view.findViewById(R.id.botamshetthometname);
                        homeemailview=(TextView) view.findViewById(R.id.botamshetthometemail);
                        Picasso.get().load(model.getProfile_image()).into(homeimageview);

                        profilephonenumber=view.findViewById(R.id.profilephonenumber1);
                        profilehomephonenumber=view.findViewById(R.id.profilehomephonenumber1);
                        profileroomnumber=view.findViewById(R.id.profileroomnumber1);
                        profiledate=view.findViewById(R.id.profiledate1);
                        profileaddress=view.findViewById(R.id.profileaddress1);


                        profileadharback=view.findViewById(R.id.profileadharback1);
                        profileadharfrend=view.findViewById(R.id.profileadharfrend1);

                        profilesadharrcarennumber=view.findViewById(R.id.profilesadharrcarennumber1);
                        profilevillage=view.findViewById(R.id.profilevillage1);
                        profiledistrict=view.findViewById(R.id.profiledistrict1);
                        profilesate=view.findViewById(R.id.profilestate1);
                        profilepatcode=view.findViewById(R.id.profilepostcode1);
                        profilename=view.findViewById(R.id.profilename1);
                        profileemail=view.findViewById(R.id.profileemail1);





                        Picasso.get().load(model.getaAhaarcard_backside_image()).into(profileadharback);
                        Picasso.get().load(model.getAdhaarcard_frentside_image()).into(profileadharfrend);
                        profilephonenumber.setText(model.getPhone());
                        profilehomephonenumber.setText(model.getHome_phome());
                        profileroomnumber.setText(model.getRoom_Number());
                        profiledate.setText(model.getDate());
                        profileaddress.setText(model.getAddress());
                        profilesadharrcarennumber.setText(model.getAdhaar_cared_number());
                        profilevillage.setText(model.getVillage());
                        profiledistrict.setText(model.getDistrict());
                        profilesate.setText(model.getState());
                        profilepatcode.setText(model.getPostel_code());
                        profilename.setText(model.getName());
                        profileemail.setText(model.getEmail());
                        homenameview.setText(model.getName());
                        homeemailview.setText(model.getEmail());

                        (view.findViewById(R.id.callnoehome)).setOnClickListener(new View.OnClickListener() {
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

                        (view.findViewById(R.id.morelisshare)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                                String imgBitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),imgBitmap, String.valueOf(R.string.app_name),null);
                                Uri imgBitmapUri = Uri.parse(imgBitmapPath);
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
                                shareIntent.setType("*/*");
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                shareIntent.putExtra(Intent.EXTRA_TEXT, "Name:"+"\n" + model.getName()+"\n"+"Email:"+"\n" + model.getEmail()+"\n"+"Phone Number:"+"\n" + model.getPhone()+"\n"+"House Phone Number:"+"\n" + model.getHome_phome()+"\n"+"Adhaar Cared Number:"+"\n" + model.getAdhaar_cared_number()+"\n"+"Address:"+"\n" + model.getAddress());
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                                startActivity(Intent.createChooser(shareIntent, "Share this"));
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