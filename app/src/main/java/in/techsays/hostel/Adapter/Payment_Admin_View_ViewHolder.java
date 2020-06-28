package in.techsays.hostel.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import in.techsays.hostel.R;

public class Payment_Admin_View_ViewHolder extends RecyclerView.ViewHolder {

    View v;

    public Payment_Admin_View_ViewHolder(View itemView) {
        super(itemView);

        v = itemView;

        //item click

    }

    //set details to recycler view row
    public void setDetails(final Context ctx, String  Name, String  Ammount, String  PersonPhoto, String  roomno, String  paymethod, String  tid, String  email, String  time, String  date){
        //Views
        final TextView paymentname = (TextView) v.findViewById(R.id.paymentnameadminview);
        final ImageView paymentimage = v.findViewById(R.id.paymentimageadminview);
        final TextView paymentemail = (TextView) v.findViewById(R.id.paymentemailadminview);
        final TextView paymenttransactionid = (TextView) v.findViewById(R.id.paymenttransactionidadminview);
        final TextView paymentammount = (TextView) v.findViewById(R.id.paymentammountadminview);
        final TextView paymenttime = (TextView) v.findViewById(R.id.paymenttimeadminview);
        final TextView paymentdate = (TextView) v.findViewById(R.id.paymentdateadminview);
        final TextView roomnumberpayment = (TextView) v.findViewById(R.id.roomnumberpaymentadminview);

        final TextView todaypaymetmethedtext = (TextView) v.findViewById(R.id.adminviespaymenton);

        final LinearLayout todaypaymentmethod = (LinearLayout) v.findViewById(R.id.adminviewpaymetsl);

        paymentname.setText(Name);
        Picasso.get().load(PersonPhoto).into(paymentimage);
         paymentemail.setText(email);
         paymenttransactionid.setText(tid);
        paymentammount.setText(Ammount);
        paymenttime.setText(time );
        paymentdate.setText(date);
       roomnumberpayment.setText(roomno);
      if(paymethod.contains("Cash Payment")){
          todaypaymentmethod.setBackgroundColor(ctx.getResources().getColor(R.color.red_A700));
           todaypaymetmethedtext.setText(paymethod);

       }
       else if(paymethod.contains("Online Payment")) {
          todaypaymentmethod.setBackgroundColor(ctx.getResources().getColor(R.color.green_A700));
           todaypaymetmethedtext.setText(paymethod);
       }






    }




}