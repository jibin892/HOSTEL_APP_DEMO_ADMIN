package in.techsays.hostel.Home;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;
import com.squareup.picasso.Picasso;

import java.util.Random;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import in.techsays.hostel.Login_and_Registration.User;
import in.techsays.hostel.R;

import static android.content.Context.MODE_PRIVATE;


public class PaymentFragment extends Fragment implements PaymentStatusListener {

    int random;
    EditText ammount;
    EditText discription;
    Button sudmit, paymentcard;
    final int min = 100;
    final int max = 1000000;
    SharedPreferences sh;
    TextView paymentfragmentname,paymentfragmentemail,paymentfragmentroomnumber,paymentfragmentdate;
ImageView payametfragmetprofileimage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        random = new Random().nextInt((max - min)) + min;
        sh = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
        userdataload();

        paymentfragmentname =  root.findViewById(R.id.paymentfragmentname);
        paymentfragmentemail =  root.findViewById(R.id.paymentfragmentemail);
        paymentfragmentroomnumber =  root.findViewById(R.id.paymentfragmentroomnumber);
        payametfragmetprofileimage =  root.findViewById(R.id.payametfragmetprofileimage);
        paymentfragmentdate =  root.findViewById(R.id.paymentfragmentdate);


        return root;
    }

    public void onclick1(View view) {
        int id = view.getId();
        switch (id) {
            // replace with different fragments
            case R.id.googelpay:
            {

                final Dialog dialoggoogelpay = new Dialog(getContext());
                dialoggoogelpay.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialoggoogelpay.setContentView(R.layout.paymentpopup);
                dialoggoogelpay.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final TextView namepay = (TextView) dialoggoogelpay.findViewById(R.id.payementpopupname);
                final  TextView emailpay = (TextView) dialoggoogelpay.findViewById(R.id.paymentpopuemail);
                final ImageView payimage = (ImageView) dialoggoogelpay.findViewById(R.id.paymentpopupimage);

                ammount = (EditText) dialoggoogelpay.findViewById(R.id.paymentammount);
                discription = (EditText) dialoggoogelpay.findViewById(R.id.paymentdiscription);
                sudmit = (Button) dialoggoogelpay.findViewById(R.id.paymentcontinu);

                dialoggoogelpay.setCancelable(false);




                (dialoggoogelpay.findViewById(R.id.paymentcontinu)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(ammount.getText().toString().isEmpty()||discription.getText().toString().isEmpty()){


                            Crouton.makeText(getActivity(), "fill all the fields", Style.ALERT).show();

                        }

                        else{
                            //  payUsingUpi(user.getDisplayName(),"jibincherian892-1@okicici",discription.getText().toString(),ammount.getText().toString());
                            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                                    .with(getActivity())
                                    .setPayeeVpa("jibincherian892-1@okicici")
                                    .setPayeeName("TECHSAYS SOFTWARE SOLUTIONS PVT LTD")
                                    .setTransactionId(String.valueOf(random))
                                    .setTransactionRefId(String.valueOf(random))
                                    .setDescription(discription.getText().toString())
                                    .setAmount(ammount.getText().toString()+".0").build();
                            easyUpiPayment.setDefaultPaymentApp(PaymentApp.GOOGLE_PAY);
                            easyUpiPayment.startPayment();
                            easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) getActivity());

                        }}
                });




                (dialoggoogelpay.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialoggoogelpay.dismiss();
                    }
                });
                dialoggoogelpay.show();
            }

            break;

            case R.id.phonepay:
            {

                final Dialog dialogphonpey = new Dialog(getContext());
                dialogphonpey.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialogphonpey.setContentView(R.layout.paymentpopup);
                dialogphonpey.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final  TextView namepay = (TextView) dialogphonpey.findViewById(R.id.payementpopupname);
                final  TextView emailpay = (TextView) dialogphonpey.findViewById(R.id.paymentpopuemail);
                final  ImageView payimage = (ImageView) dialogphonpey.findViewById(R.id.paymentpopupimage);

                ammount = (EditText) dialogphonpey.findViewById(R.id.paymentammount);
                discription = (EditText) dialogphonpey.findViewById(R.id.paymentdiscription);
                sudmit = (Button) dialogphonpey.findViewById(R.id.paymentcontinu);
                dialogphonpey.setCancelable(false);



                (dialogphonpey.findViewById(R.id.paymentcontinu)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ammount.getText().toString().isEmpty()||discription.getText().toString().isEmpty()){


                            Crouton.makeText(getActivity(), "fill sll field", Style.ALERT).show();

                        }

                        else{
                            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                                    .with(getActivity())
                                    .setPayeeVpa("jibincherian892-1@okicici")
                                    .setPayeeName("TECHSAYS SOFTWARE SOLUTIONS PVT LTD")
                                    .setTransactionId(String.valueOf(random))
                                    .setTransactionRefId(String.valueOf(random+1))
                                    .setDescription(discription.getText().toString())
                                    .setAmount(ammount.getText().toString()+".0").build();
                            easyUpiPayment.setDefaultPaymentApp(PaymentApp.PHONE_PE);
                            easyUpiPayment.startPayment();
                            easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) getActivity());
                        }}
                });



                (dialogphonpey.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogphonpey.dismiss();
                    }
                });
                dialogphonpey.show();
            }

            break;
            case R.id.paytem:
            {

                final Dialog dialogpaytem = new Dialog(getContext());
                dialogpaytem.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialogpaytem.setContentView(R.layout.paymentpopup);
                dialogpaytem.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final  TextView namepay = (TextView) dialogpaytem.findViewById(R.id.payementpopupname);
                final  TextView emailpay = (TextView) dialogpaytem.findViewById(R.id.paymentpopuemail);
                final  ImageView payimage = (ImageView) dialogpaytem.findViewById(R.id.paymentpopupimage);
                ammount = (EditText) dialogpaytem.findViewById(R.id.paymentammount);
                discription = (EditText) dialogpaytem.findViewById(R.id.paymentdiscription);



                dialogpaytem.setCancelable(false);


                (dialogpaytem.findViewById(R.id.paymentcontinu)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ammount.getText().toString().isEmpty()||discription.getText().toString().isEmpty()){


                            Crouton.makeText(getActivity(), "fill sll field", Style.ALERT).show();

                        }

                        else{
                            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                                    .with(getActivity())
                                    .setPayeeVpa("jibincherian892-1@okicici")
                                    .setPayeeName("TECHSAYS SOFTWARE SOLUTIONS PVT LTD")
                                    .setTransactionId(String.valueOf(random))
                                    .setTransactionRefId(String.valueOf(random))
                                    .setDescription(discription.getText().toString())
                                    .setAmount(ammount.getText().toString()+".0").build();
                            easyUpiPayment.setDefaultPaymentApp(PaymentApp.PAYTM);
                            easyUpiPayment.startPayment();
                            easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) getActivity());

                        }}
                });


                (dialogpaytem.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogpaytem.dismiss();
                    }
                });
                dialogpaytem.show();
            }

            break;
            case R.id.barathpays:
            {

                final Dialog dialogbarathpays = new Dialog(getContext());
                dialogbarathpays.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialogbarathpays.setContentView(R.layout.paymentpopup);
                dialogbarathpays.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final  TextView namepay = (TextView) dialogbarathpays.findViewById(R.id.payementpopupname);
                final  TextView emailpay = (TextView) dialogbarathpays.findViewById(R.id.paymentpopuemail);
                final  ImageView payimage = (ImageView) dialogbarathpays.findViewById(R.id.paymentpopupimage);
                ammount = (EditText) dialogbarathpays.findViewById(R.id.paymentammount);
                discription = (EditText) dialogbarathpays.findViewById(R.id.paymentdiscription);


                dialogbarathpays.setCancelable(false);


                (dialogbarathpays.findViewById(R.id.paymentcontinu)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ammount.getText().toString().isEmpty()||discription.getText().toString().isEmpty()){


                            Crouton.makeText(getActivity(), "fill sll field", Style.ALERT).show();

                        }

                        else{
                            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                                    .with(getActivity())
                                    .setPayeeVpa("jibincherian892-1@okicici")
                                    .setPayeeName("TECHSAYS SOFTWARE SOLUTIONS PVT LTD")
                                    .setTransactionId(String.valueOf(random))
                                    .setTransactionRefId(String.valueOf(random))
                                    .setDescription(discription.getText().toString())
                                    .setAmount(ammount.getText().toString()+".0").build();
                            easyUpiPayment.setDefaultPaymentApp(PaymentApp.BHIM_UPI);
                            easyUpiPayment.startPayment();
                            easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) getActivity());

                        }}
                });

                (dialogbarathpays.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogbarathpays.dismiss();
                    }
                });
                dialogbarathpays.show();
            }

            break;
            case R.id.amazonepay:
            {

                final Dialog dialogamazonepay = new Dialog(getContext());
                dialogamazonepay.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialogamazonepay.setContentView(R.layout.paymentpopup);
                dialogamazonepay.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final  TextView namepay = (TextView) dialogamazonepay.findViewById(R.id.payementpopupname);
                final  TextView emailpay = (TextView) dialogamazonepay.findViewById(R.id.paymentpopuemail);
                final  ImageView payimage = (ImageView) dialogamazonepay.findViewById(R.id.paymentpopupimage);

                ammount = (EditText) dialogamazonepay.findViewById(R.id.paymentammount);
                discription = (EditText) dialogamazonepay.findViewById(R.id.paymentdiscription);




                dialogamazonepay.setCancelable(false);



                (dialogamazonepay.findViewById(R.id.paymentcontinu)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ammount.getText().toString().isEmpty()||discription.getText().toString().isEmpty()){


                            Crouton.makeText(getActivity(), "fill sll field", Style.ALERT).show();

                        }

                        else{
                            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                                    .with(getActivity())
                                    .setPayeeVpa("jibincherian892-1@okicici")
                                    .setPayeeName("TECHSAYS SOFTWARE SOLUTIONS PVT LTD")
                                    .setTransactionId(String.valueOf(random))
                                    .setTransactionRefId(String.valueOf(random))
                                    .setDescription(discription.getText().toString())
                                    .setAmount(ammount.getText().toString()+".0").build();
                            easyUpiPayment.setDefaultPaymentApp(PaymentApp.AMAZON_PAY);
                            easyUpiPayment.startPayment();
                            easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) getActivity());

                        }}
                });


//
                (dialogamazonepay.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogamazonepay.dismiss();
                    }
                });
                dialogamazonepay.show();
            }
            break;


        }
    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {

    }

    @Override
    public void onTransactionSuccess() {

        // Payment Success
        // Toast.makeText(Payment_home.this,transactionDetails.toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        // imageView.setImageResource(R.drawable.ic_success);

    }

    @Override
    public void onTransactionSubmitted() {
        // Payment Pending
        Toast.makeText(getActivity(), "Pending | Submitted", Toast.LENGTH_SHORT).show();
        // imageView.setImageResource(R.drawable.ic_success);
    }

    @Override
    public void onTransactionFailed() {
        // Payment Failed
        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
        // imageView.setImageResource(R.drawable.ic_failed);
    }

    @Override
    public void onTransactionCancelled() {
        // Payment Cancelled by User
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        // imageView.setImageResource(R.drawable.ic_failed);
    }

    @Override
    public void onAppNotFound() {
        Toast.makeText(getActivity(), "App not installed", Toast.LENGTH_SHORT).show();
    }



    private void userdataload() {



        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("registration").child(sh.getString("usernodeuid",null));
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userr = dataSnapshot.getValue(User.class);

//                    email=userr.getEmail().toString();
//                    profilepic=userr.getProfile_image().toString();
//                    name=userr.getName().toString();

                    Picasso.get().load(userr.getProfile_image()).into(payametfragmetprofileimage);
                     paymentfragmentemail.setText(userr.getEmail());
                     paymentfragmentname.setText(userr.getName());
                     paymentfragmentdate.setText(userr.getDate());
                    paymentfragmentroomnumber.setText(userr.getRoom_Number());


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}
}