package in.techsays.hostel.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import in.techsays.hostel.Adapter.Homelist;
import in.techsays.hostel.Approve_details.Approve;
import in.techsays.hostel.R;


public class HomeFragment extends Fragment {
    FirebaseListAdapter<Homelist> adapter;
    ListView notificationlist;
    Query reference;
    LinearLayout view;
    final int RequestPermissionCode=1;
    public static final int DIALOG_QUEST_CODE = 300;
     ProgressBar progress_determinate;
    private ShimmerFrameLayout mShimmerViewContainer;
    ImageView  profilefragmentimage,profileadharback,profileadharfrend  ;
    TextView profilefragmentemail,profilefragmentname;
    TextView profilename,profileemail,profilephonenumber,profilehomephonenumber,profileroomnumber,profiledate,profileaddress,
            profilesadharrcarennumber,profilevillage,profiledistrict,profilesate,profilepatcode;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        notificationlist = root.findViewById(R.id.notificationlist);
        view=root.findViewById(R.id.imageid);
        mShimmerViewContainer = root.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();

        displayNotifications();
        return root;
    }
    private void displayNotifications() {
        adapter = new FirebaseListAdapter<Homelist>(getActivity(), Homelist.class,
                R.layout.list_home,
                reference = FirebaseDatabase.getInstance().getReference().child("registration")) {
            @Override
            protected void populateView(View v, final Homelist model, int position) {
                final TextView homelistname = (TextView) v.findViewById(R.id.homelistname);
                final TextView homelisemail = (TextView) v.findViewById(R.id.homelisemail);
                final TextView homelistdate = (TextView) v.findViewById(R.id.homelistdate);
                final TextView homelistph = (TextView) v.findViewById(R.id.homelistph);
                final FloatingActionButton fabhomeaddlist = (FloatingActionButton) v.findViewById(R.id.fabhomeaddlist);

                final ImageView personimagehomelist = v.findViewById(R.id.personimagehomelist);



                homelisemail.setText(model.getEmail());
                homelistname.setText(model.getName());
                homelistdate.setText(model.getDate());
                homelistph.setText(model.getPhone());

                Picasso.get().load(model.getProfile_image()).into(personimagehomelist);

        fabhomeaddlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadpopup();


            }

            private void loadpopup() {


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.home_list_view_more);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                profilefragmentname=dialog.findViewById(R.id.profilefragmentname);
                profilefragmentemail=dialog.findViewById(R.id.profilefragmentemail);
                profilefragmentimage=dialog.findViewById(R.id.profilefragmentimage);

                profilename=dialog.findViewById(R.id.profilename);
                profileemail=dialog.findViewById(R.id.profileemail);
                profilephonenumber=dialog.findViewById(R.id.profilephonenumber);
                profilehomephonenumber=dialog.findViewById(R.id.profilehomephonenumber);
                profileroomnumber=dialog.findViewById(R.id.profileroomnumber);
                profiledate=dialog.findViewById(R.id.profiledate);
                profileaddress=dialog.findViewById(R.id.profileaddress);


                profileadharback=dialog.findViewById(R.id.profileadharback);
                profileadharfrend=dialog.findViewById(R.id.profileadharfrend);

                profilesadharrcarennumber=dialog.findViewById(R.id.profilesadharrcarennumber);
                profilevillage=dialog.findViewById(R.id.profilevillage);
                profiledistrict=dialog.findViewById(R.id.profiledistrict);
                profilesate=dialog.findViewById(R.id.profilestate);
                profilepatcode=dialog.findViewById(R.id.profilepostcode);






                Picasso.get().load(model.getProfile_image()).into(profilefragmentimage);
                Picasso.get().load(model.getaAhaarcard_backside_image()).into(profileadharback);
                Picasso.get().load(model.getAdhaarcard_frentside_image()).into(profileadharfrend);

                profilefragmentemail.setText(model.getEmail());
                profilefragmentname.setText(model.getName());
                profilename.setText(model.getName());
                profilefragmentemail.setText(model.getEmail());
                profilephonenumber.setText(model.getPhone());
                profilehomephonenumber.setText(model.getHome_phome());
                profileroomnumber.setText(model.getRoom_Number());
                profiledate.setText(model.getDate());
                profileaddress.setText(model.getAddress());
                profileemail.setText(model.getEmail());
                profilesadharrcarennumber.setText(model.getAdhaar_cared_number());
                profilevillage.setText(model.getVillage());
                profiledistrict.setText(model.getDistrict());
                profilesate.setText(model.getState());
                profilepatcode.setText(model.getPostel_code());


                final TextView bt_submit = (TextView) dialog.findViewById(R.id.bt_submit);
                   final ImageButton btnclosehomelist = (ImageButton) dialog.findViewById(R.id.btnclosehomelist);


                bt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent approve=new Intent(getActivity(), Approve.class);

                        approve.putExtra("name",model.getName());
                        approve.putExtra("profileimage",model.getProfile_image());
                        approve.putExtra("email",model.getEmail());
                        approve.putExtra("address",model.getAddress());
                        approve.putExtra("age",model.getAge());
                        approve.putExtra("dob",model.getYearOfBirth());
                        approve.putExtra("village",model.getVillage());
                        approve.putExtra("dis",model.getDistrict());
                        approve.putExtra("state",model.getState());
                        approve.putExtra("adharfrend",model.getAdhaarcard_frentside_image());
                        approve.putExtra("adharback",model.getaAhaarcard_backside_image());
                        approve.putExtra("postcode",model.getPostel_code());
                        approve.putExtra("adharcarednumber",model.getAdhaar_cared_number());
                        approve.putExtra("homephone",model.getHome_phome());
                        approve.putExtra("date",model.getDate());
                        approve.putExtra("stayingpurpouse",model.getStaying_puropse());
                        approve.putExtra("pass",model.getPasswored());
                        approve.putExtra("uid",model.getUid());
                        approve.putExtra("phone",model.getPhone());
                        approve.putExtra("homephone",model.getHome_phome());
                        approve.putExtra("adhaarname",model.getAdhaar_cared_name());
                        approve.putExtra("gender",model.getGender());





                        startActivity(approve);


                    }
                });


                btnclosehomelist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                     }
                });




                dialog.show();
                dialog.getWindow().setAttributes(lp);

            }
        });





//                 String s=String.valueOf(model.getTime_stamp());
//                s=s.substring(1);
//                Calendar cal = Calendar.getInstance();
//                TimeZone tz = cal.getTimeZone();//get your local time zone.
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
//                sdf.setTimeZone(tz);//set time zone.
//                String localTime = sdf.format(new Date(Long.parseLong(model.getTime_stamp())* 1000));
//                Date date = new Date();
//                try {
//                    date = sdf.parse(localTime);//get local date
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                if(date == null) {
//                    //  return null;
//                }
//
//                long time = date.getTime();
//
//                Date curDate = currentDate();
//                long now = curDate.getTime();
//                if (time > now || time <= 0) {
//                    //  return null;
//                }
//
//                float timeDIM = getTimeDistanceInMinutes(time);
//
//                String timeAgo = null;
//
//                if (timeDIM == 0) {
//                    timeAgo = "just now";
//                } else if (timeDIM == 1) {
//                    //  return  "1 minute";
//                    timeAgo="1 minute ago";
//                } else if (timeDIM >= 2 && timeDIM <= 44) {
//                    timeAgo = (Math.round(timeDIM)) + " minutes ago";
//                } else if (timeDIM >= 45 && timeDIM <= 89) {
//                    timeAgo = " 1 hour ago";
//                } else if (timeDIM >= 90 && timeDIM <= 1439) {
//                    timeAgo =   (Math.round(timeDIM / 60)) + " hours ago";
//                } else if (timeDIM >= 1440 && timeDIM <= 2519) {
//                    timeAgo = "1 day ago";
//                } else if (timeDIM >= 2520 && timeDIM <= 43199) {
//                    timeAgo = (Math.round(timeDIM / 1440)) + " days";
//                } else if (timeDIM >= 43200 && timeDIM <= 86399) {
//                    timeAgo = "about a month ago";
//                } else if (timeDIM >= 86400 && timeDIM <= 525599) {
//                    timeAgo = (Math.round(timeDIM / 43200)) + " months";
//                } else if (timeDIM >= 525600 && timeDIM <= 655199) {
//                    timeAgo = "about a year ago";
//                } else if (timeDIM >= 655200 && timeDIM <= 914399) {
//                    timeAgo = "over a year ago";
//                } else if (timeDIM >= 914400 && timeDIM <= 1051199) {
//                    timeAgo = "almost 2 years ago";
//                } else {
//                    timeAgo = "about " + (Math.round(timeDIM / 525600)) + " years";
//                }
//                // return timeAgo + " ago";
//                uploadedtime.setText(timeAgo);







                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.INVISIBLE);

                        }


else {
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                            dialog.setContentView(R.layout.no_new_addmisions);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setCancelable(false);
                            dialog.show();
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