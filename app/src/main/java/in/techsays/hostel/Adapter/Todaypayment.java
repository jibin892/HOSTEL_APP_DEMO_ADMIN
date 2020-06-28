package in.techsays.hostel.Adapter;


        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.net.Uri;
        import android.text.format.DateFormat;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.PointerIcon;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.PopupMenu;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.core.content.FileProvider;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.squareup.picasso.Picasso;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.lang.reflect.Field;
        import java.util.Calendar;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Locale;
        import java.util.Objects;

        import de.hdodenhof.circleimageview.CircleImageView;
        import in.techsays.hostel.R;


public class Todaypayment extends RecyclerView.Adapter<Todaypayment.MyHolder> {

    Context context;
    List<Payment_Adapter> postList;
    private int likes=0;
    FirebaseAuth firebaseAuth;

    String myUid;

     private  DatabaseReference postsRef; //for posts database node

    boolean mProcessLike = false;
    public Todaypayment(Context context, List<Payment_Adapter> postList) {
        this.context = context;
        this.postList = postList;
        firebaseAuth = FirebaseAuth.getInstance();
          postsRef = FirebaseDatabase.getInstance().getReference().child("payment");
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout row_post.xml
        View view = LayoutInflater.from(context).inflate(R.layout.payment_list, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {


        myHolder. paymentname.setText(postList.get(i).getPersonName());
        Picasso.get().load(postList.get(i).getPersonPhoto()).into( myHolder. paymentimage);
        myHolder.  paymentemail.setText(postList.get(i).getPersonName());
        myHolder. paymenttransactionid.setText(postList.get(i).getTransaction_id());
        myHolder.  paymentammount.setText(postList.get(i).getAmmount());
        myHolder. paymenttime.setText(postList.get(i).getPaymentTime());
        myHolder. paymentdate.setText(postList.get(i).getPaymentdate());
        myHolder.  roomnumberpayment.setText(postList.get(i).getRoomnumber());



        }

    @Override
    public int getItemCount() {
        return 0;
    }


    //view holder class
    class MyHolder extends RecyclerView.ViewHolder {

        //views from row_post.xml
        TextView paymentname,paymentemail,paymenttransactionid,paymentammount,paymenttime,paymentdate,roomnumberpayment,todaypaymetmethedtext;
        ImageView paymentimage;
        LinearLayout todaypaymentmethod;

        public MyHolder(@NonNull View v) {
            super(v);


            //init views
               paymentname = (TextView) v.findViewById(R.id.paymentname);
             paymentimage = v.findViewById(R.id.paymentimage);
          paymentemail = (TextView) v.findViewById(R.id.paymentemail);
      paymenttransactionid = (TextView) v.findViewById(R.id.paymenttransactionid);
            paymentammount = (TextView) v.findViewById(R.id.paymentammount);
              paymenttime = (TextView) v.findViewById(R.id.paymenttime);
             paymentdate = (TextView) v.findViewById(R.id.paymentdate);
             roomnumberpayment = (TextView) v.findViewById(R.id.roomnumberpayment);
              todaypaymetmethedtext = (TextView) v.findViewById(R.id.todaypaymetmethedtext);

           todaypaymentmethod = (LinearLayout) v.findViewById(R.id.todaypaymentmethod);
        }
    }
}
