package in.techsays.hostel.Home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import in.techsays.hostel.Animation.ViewAnimation;
import in.techsays.hostel.Login_and_Registration.User;
import in.techsays.hostel.R;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {
ImageView  profilefragmentimage  ;
TextView profilefragmentemail,profilefragmentname;
TextView profilename,profileemail,profilephonenumber,profilehomephonenumber,profileroomnumber,profiledate,profileaddress;

    private boolean rotate = false;
SharedPreferences sh;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        sh = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
        userdataload();

        profilefragmentname=root.findViewById(R.id.profilefragmentname);
        profilefragmentemail=root.findViewById(R.id.profilefragmentemail);
        profilefragmentimage=root.findViewById(R.id.profilefragmentimage);

        profilename=root.findViewById(R.id.profilename);
        profileemail=root.findViewById(R.id.profileemail);
        profilephonenumber=root.findViewById(R.id.profilephonenumber);
        profilehomephonenumber=root.findViewById(R.id.profilehomephonenumber);
        profileroomnumber=root.findViewById(R.id.profileroomnumber);
        profiledate=root.findViewById(R.id.profiledate);
        profileaddress=root.findViewById(R.id.profileaddress);











        final FloatingActionButton fab_mic = (FloatingActionButton) root.findViewById(R.id.fab_mic);
        final FloatingActionButton fab_call = (FloatingActionButton)root. findViewById(R.id.fab_call);
        ViewAnimation.initShowOut(fab_mic);
        ViewAnimation.initShowOut(fab_call);

        ((FloatingActionButton) root.findViewById(R.id.fab_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate = ViewAnimation.rotateFab(v, !rotate);
                if (rotate) {
                    ViewAnimation.showIn(fab_mic);
                    ViewAnimation.showIn(fab_call);
                } else {
                    ViewAnimation.showOut(fab_mic);
                    ViewAnimation.showOut(fab_call);
                }
            }
        });

        fab_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Voice clicked", Toast.LENGTH_SHORT).show();
            }
        });

        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Call clicked", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    private void userdataload() {



        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("registration").child(sh.getString("usernodeuid",null));
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userr = dataSnapshot.getValue(User.class);

                    String email=userr.getEmail().toString();
                   String profilepic=userr.getProfile_image().toString();

                    Picasso.get().load(userr.getProfile_image()).into(profilefragmentimage);
                     profilefragmentemail.setText(userr.getEmail());
                    profilefragmentname.setText(userr.getName());
                    profilename.setText(userr.getName());
                    profilefragmentemail.setText(userr.getEmail());
                    profilephonenumber.setText(userr.getPhone());
                    profilehomephonenumber.setText(userr.getHome_phome());
                    profileroomnumber.setText(userr.getRoom_Number());
                    profiledate.setText(userr.getDate());
                    profileaddress.setText(userr.getAddress());
                    profileemail.setText(userr.getEmail());


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}