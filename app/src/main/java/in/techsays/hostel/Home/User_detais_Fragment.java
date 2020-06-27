package in.techsays.hostel.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Constants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.compiler.PluginProtos;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import in.techsays.hostel.Adapter.Homelist;
import in.techsays.hostel.Adapter.Payment_Adapter;
import in.techsays.hostel.Approve_details.Approve;
import in.techsays.hostel.Location.Location_Home;
import in.techsays.hostel.Payment.Payment_view_user;
import in.techsays.hostel.R;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.*;


public class User_detais_Fragment extends Fragment {

    FirebaseListAdapter<Homelist> adapter;
    FirebaseListAdapter<Homelist> adapterroomate;

    ListView notificationlist,notificationlistroommate;
    Query reference,referenceromate;
    LinearLayout view;
    ArrayList<Uri> imageUriArray = new ArrayList<Uri>();
    ImageView adminusrimg;
    private BottomSheetDialog mBottomSheetDialog,mBottomSheetDialog1;
    ImageView homeimageview,homeviewprofileimage;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView homenameview,homeemailview,homeviewprofilename,homeviewprofileemail;
    final int RequestPermissionCode=1;
    ArrayAdapter<String> arrayAdapter;
    public static final int DIALOG_QUEST_CODE = 300;
    SharedPreferences roomnumber,sh;
    EditText et_searcfh;
     String payid;
    RelativeLayout parent_view;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
    String currentDate,cugrrentDay;
    ProgressDialog progress;
    ImageView   profileadharback,profileadharfrend  ;
     TextView profilename,profileemail,profilephonenumber,profilehomephonenumber,profileroomnumber,profiledate,profileaddress,
            profilesadharrcarennumber,profilevillage,profiledistrict,profilesate,profilepatcode;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notice, container, false);

        notificationlist = root.findViewById(R.id.userlis);
        mShimmerViewContainer = root.findViewById(R.id.shimmer_view_container1);
        parent_view = root.findViewById(R.id.parent_view);

        mShimmerViewContainer.startShimmer();
et_searcfh=root.findViewById(R.id.et_search);





        sh = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
        Date today = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
           currentDate = format.format(today);        @SuppressLint("SimpleDateFormat") SimpleDateFormat gsdf = new SimpleDateFormat("dd");
        cugrrentDay = valueOf(gsdf.format(new Date()));

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
                 adminusrimg = v.findViewById(R.id.adminusrimg);
                final TextView adminusrroomnumber = (TextView) v.findViewById(R.id.adminusrroomnumber);
                final TextView adminusremail = (TextView) v.findViewById(R.id.adminusremail);
                final ImageButton morehomelist = (ImageButton) v.findViewById(R.id.morehomelist);
                final ImageButton callpersondirectuserlist = (ImageButton) v.findViewById(R.id.callpersondirectuserlist);
                final ImageButton callhomepersondirectuserlist = (ImageButton) v.findViewById(R.id.callhomepersondirectuserlist);
                final ImageButton userlistshoowrommnumber = (ImageButton) v.findViewById(R.id.userlistshoowrommnumber);




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

                        (view.findViewById(R.id.morepaymentdetails)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Intent i = new Intent(getActivity(), Payment_view_user.class);
                                i.putExtra("uid",model.getUid());
                                startActivity(i);

                            }
                        });




                        (view.findViewById(R.id.morepaymentaddcash)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                final Dialog dialogcashadd = new Dialog(getActivity());
                                dialogcashadd.setContentView(R.layout.add_cash_manually);
                                dialogcashadd.setCancelable(true);

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialogcashadd.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                final    EditText   cashammountadd=dialogcashadd.findViewById(R.id.cashammountadd);
                                final   TextView   addcashmanualluroomnumber=dialogcashadd.findViewById(R.id.addcashmanualluroomnumber);
                                final   TextView     addcashmanualludate=dialogcashadd.findViewById(R.id.addcashmanualludate);
                                final  ImageView     profilefragmentimageaddcash=dialogcashadd.findViewById(R.id.profilefragmentimageaddcash);
                                final  TextView     profilefragmentnameaddcash=dialogcashadd.findViewById(R.id.profilefragmentnameaddcash);
                                final   TextView     profilefragmentemailaddcash=dialogcashadd.findViewById(R.id.profilefragmentemailaddcash);
                                final   TextView     addcashsudmit=dialogcashadd.findViewById(R.id.addcashsudmit);


                                cashammountadd.setText(model.getRent_Amount());
                                addcashmanualluroomnumber.setText(model.getRoom_Number());
                               // addcashmanualludate.setText(currentDate);

                               addcashmanualludate.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       final Calendar c = Calendar.getInstance();
                                       int mYear = c.get(Calendar.YEAR); // current year
                                       int mMonth = c.get(Calendar.MONTH); // current month
                                       int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                       // date picker dialog
                                       DatePickerDialog  datePickerDialog = new DatePickerDialog(getActivity(),
                                               new DatePickerDialog.OnDateSetListener() {

                                                   @Override
                                                   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                       // set day of month , month and year value in the edit text
                                                       addcashmanualludate.setText(dayOfMonth + "-"+ (monthOfYear + 1) + "-" + year);

                                                   }
                                               }, mYear, mMonth, mDay);
                                       datePickerDialog.show();
                                   }
                               });
                                Picasso.get().load(model.getProfile_image()).into(profilefragmentimageaddcash);

                                profilefragmentemailaddcash.setText(model.getEmail());
                                profilefragmentnameaddcash.setText(model.getName());



                                 final ImageButton btnclosehomelist = (ImageButton) dialogcashadd.findViewById(R.id.btnclosehomelistmore);
                                btnclosehomelist.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogcashadd.dismiss();
                                    }
                                });

                                addcashsudmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

 if(addcashmanualludate.getText().toString().isEmpty()){

    addcashmanualludate.setError("Choose Date");
}
else {
    payid = getRandomString(15);
    progress = new ProgressDialog(getActivity());
    progress.setMessage("Please wait...");
    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
// progress.setIndeterminate(true);
    progress.show();

     String cal = new SimpleDateFormat("E MMM dd HH:mm:ss z uuuu", Locale.getDefault()).format(new Date());

 String mm=String.valueOf(cal);
    DatabaseReference object = FirebaseDatabase.getInstance().getReference();
    DatabaseReference namesRef = object.child("payment").push();
    Map<String, Object> map = new HashMap<>();
    map.put("personName", model.getName());
    map.put("personEmail", model.getEmail());
    map.put("personPhoto", model.getProfile_image());
    map.put("Uid", model.getUid());
    map.put("Day", cugrrentDay);
     map.put("Mounth",mm );
     map.put("Date", addcashmanualludate.getText().toString());
    map.put("Payment_Method", "Cash Payment");
    map.put("Roomnumber", model.getRoom_Number());
    map.put("ammount", cashammountadd.getText().toString());
    map.put("discription", model.getName() + "Room Numbr" + model.getRoom_Number() + "Rent Ammount");
    map.put("transaction_id", payid);
    String timeStamp = valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    map.put("paymenttime", timeStamp);
    map.put("phone_number", model.getPhone());
    String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    map.put("paymentTime", currentTime);
    map.put("paymentdate", currentDate);
    namesRef.updateChildren(map);
    object.child("payment");
    object.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            dialogcashadd.dismiss();
            progress.dismiss();
            tostsususs();
            //   smsuser();


        }

        private void tostsususs() {

            Toast toast = new Toast(getActivity());
            toast.setDuration(Toast.LENGTH_LONG);

            //inflate view
            View custom_view = getLayoutInflater().inflate(R.layout.snakbar_susseus, null);
            ((TextView) custom_view.findViewById(R.id.message)).setText("Success!");
            ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.checkmark);
            ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));
            toast.setView(custom_view);
            toast.show();
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}
                                    }
                                    private void smsuser() {


                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://techsays.in/paysms.php",
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
//dilogsuseuss();
                                                        smsuser();
                                                    }

                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                                                    }

                                                }) {

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<>();
//Adding parameters to request
                                                params.put("phone",model.getPhone());
                                                params.put("name",model.getName());
                                                params.put("pay",cashammountadd.getText().toString());
                                                params.put("id",payid);
//returning parameter
                                                return params;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                        requestQueue.add(stringRequest);






                                     }
                                });







                                dialogcashadd.show();
                                dialogcashadd.getWindow().setAttributes(lp);

                            }
                        });

                        (view.findViewById(R.id.morehomelistview)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                                dialog.setContentView(R.layout.edit_more_user_details);
                                dialog.setCancelable(true);

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;





                                dialog.show();
                                dialog.getWindow().setAttributes(lp);
                                }


                        });

                        (view.findViewById(R.id.morelisshare)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                BitmapDrawable bitmapDrawable = ((BitmapDrawable) homeimageview.getDrawable());
                                Bitmap bitmap = bitmapDrawable .getBitmap();
                                String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap,"sometitle", null);
                                Uri bitmapUri = Uri.parse(bitmapPath);
                                BitmapDrawable bitmapDrawable1 = ((BitmapDrawable) profileadharfrend.getDrawable());
                                Bitmap bitmap1 = bitmapDrawable1 .getBitmap();
                                String bitmapPath1 = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap1,"sometitle", null);
                                Uri bitmapUri1 = Uri.parse(bitmapPath1);
                                BitmapDrawable bitmapDrawable11 = ((BitmapDrawable) profileadharback.getDrawable());
                                Bitmap bitmap11= bitmapDrawable11 .getBitmap();
                                String bitmapPath11 = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap11,"sometitle", null);
                                Uri bitmapUri11 = Uri.parse(bitmapPath11);
                                imageUriArray.add(bitmapUri);
                                imageUriArray.add(bitmapUri1);
                                imageUriArray.add(bitmapUri11);
                                Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUriArray);
//                                shareIntent.putExtra(Intent.EXTRA_STREAM,bitmapUri1);
                                shareIntent.setType("*/*");
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                shareIntent.putExtra(Intent.EXTRA_TEXT, "Name:"+"\n" + model.getName()+"\n"+"Email:"+"\n" + model.getEmail()+"\n"+"Phone Number:"+"\n" + model.getPhone()+"\n"+"House Phone Number:"+"\n" + model.getHome_phome()+"\n"+"Adhaar Cared Number:"+"\n" + model.getAdhaar_cared_number()+"\n"+"Address:"+"\n" + model.getAddress());
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                                startActivity(Intent.createChooser(shareIntent, "Share this"));
                                imageUriArray.clear();
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

                callpersondirectuserlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent callIntentt = new Intent(Intent.ACTION_CALL);
                        callIntentt.setData(Uri.parse("tel:" + model.getPhone()));
                        callIntentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        startActivity(callIntentt);


                    }
                });

                callhomepersondirectuserlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent callIntentt = new Intent(Intent.ACTION_CALL);
                        callIntentt.setData(Uri.parse("tel:" + model.getHome_phome()));
                        callIntentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        startActivity(callIntentt);


                    }
                });




                userlistshoowrommnumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final Dialog dialogroommate = new Dialog(getActivity());
                        dialogroommate.setContentView(R.layout.view_romemates_admin);
                        dialogroommate.setCancelable(true);

                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialogroommate.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                           notificationlistroommate = dialogroommate.findViewById(R.id.userlisroommate);
                       ImageView romateclose = dialogroommate.findViewById(R.id.romateclose);
                        ImageView romatelishomeimage = dialogroommate.findViewById(R.id.romatelishomeimage);

                        TextView romatelisname = dialogroommate.findViewById(R.id.romatelisname);
                        TextView romatelisemail = dialogroommate.findViewById(R.id.romatelisemail);
                        Picasso.get().load(model.getProfile_image()).into(romatelishomeimage);
                        romatelisemail.setText(model.getEmail());
                        romatelisname.setText(model.getName());



                        romateclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogroommate.dismiss();
                            }
                        });


                        adapterroomate = new FirebaseListAdapter<Homelist>(getActivity(), Homelist.class,
                                R.layout.rommete_list,
                                referenceromate = FirebaseDatabase.getInstance().getReference().child("adminapproved").orderByChild("Room_Number").equalTo(model.getRoom_Number())) {

                            @Override
                            protected void populateView(View romate, final Homelist models, int position) {

                                final TextView adminusrnameromateimgview = (TextView) romate.findViewById(R.id.adminusrname1);
                                final ImageView romateimgview = (ImageView) romate.findViewById(R.id.romateimgview);
                                final TextView adminusrroomnumberromateimgview = (TextView) romate.findViewById(R.id.adminusrroomnumber1);
                                final TextView adminusremailromateimgview = (TextView) romate.findViewById(R.id.adminusremailss);
                                final ImageButton callromateviewnumber = (ImageButton) romate.findViewById(R.id.callromateviewnumber);
                                final ImageButton callromateviewnumberhouse = (ImageButton) romate.findViewById(R.id.callromateviewnumberhouse);
                            final ImageButton romatedatashare = (ImageButton) romate.findViewById(R.id.romatedatashare);




                                adminusrnameromateimgview.setText(models.getName());
                                Picasso.get().load(models.getProfile_image()).into(romateimgview);
                                adminusrroomnumberromateimgview.setText(models.getRoom_Number());
                          adminusremailromateimgview.setText(models.getEmail());


                                romatedatashare.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        BitmapDrawable bitmapDrawable = ((BitmapDrawable) romateimgview.getDrawable());
                                        Bitmap bitmap = bitmapDrawable .getBitmap();
                                        String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap,"sometitle", null);
                                        Uri bitmapUri = Uri.parse(bitmapPath);
                                         imageUriArray.add(bitmapUri);
                                         Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUriArray);
//                                shareIntent.putExtra(Intent.EXTRA_STREAM,bitmapUri1);
                                        shareIntent.setType("*/*");
                                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Name:"+"\n" + models.getName()+"\n"+"Email:"+"\n" + models.getEmail()+"\n"+"Phone Number:"+"\n" + models.getPhone()+"\n"+"House Phone Number:"+"\n" + models.getHome_phome()+"\n"+"Adhaar Cared Number:"+"\n" + models.getAdhaar_cared_number()+"\n"+"Address:"+"\n" + models.getAddress());
                                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                                        startActivity(Intent.createChooser(shareIntent, "Share this"));

                                    }
                                });
                                callromateviewnumber.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Intent callInte  = new Intent(Intent.ACTION_CALL);
                                        callInte .setData(Uri.parse("tel:" + models.getPhone()));
                                        callInte .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                                        startActivity(callInte);


                                    }
                                });
                                callromateviewnumberhouse.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Intent callIntes  = new Intent(Intent.ACTION_CALL);
                                        callIntes .setData(Uri.parse("tel:" + models.getHome_phome()));
                                        callIntes .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                                        startActivity(callIntes);


                                    }
                                });


                                referenceromate.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {





                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });




                                //  }




                            }



                        };


                        notificationlistroommate.setAdapter(adapterroomate);
                        adapterroomate.notifyDataSetChanged();





                        dialogroommate.show();
                        dialogroommate.getWindow().setAttributes(lp);


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

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

}