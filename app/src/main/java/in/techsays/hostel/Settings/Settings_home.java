package in.techsays.hostel.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import in.techsays.hostel.About_Us.About;
import in.techsays.hostel.Animation.ViewAnimation;
import in.techsays.hostel.Contact_Us.Contact;
import in.techsays.hostel.Home.Home_main;
import in.techsays.hostel.Login_and_Registration.Login;
import in.techsays.hostel.Login_and_Registration.User;
import in.techsays.hostel.R;

import static in.techsays.hostel.Location.Tools.toggleArrow;

public class Settings_home extends AppCompatActivity {
    SharedPreferences sh;
    private ImageButton bt_toggle_info;
    private Button bt_hide_info;
    private View lyt_expand_info;
    private Context context = this;
    String profilepic,name,email;
    ImageView settingprofileimage;
    TextView languagechoose;
    RadioGroup rg;
    ImageView settingprofilrback;
    LinearLayout setingscontacus,settingsaboutus,settingsrating,settingsshare,settinslogout,settinsreportbug,bt_toggle_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }

        setContentView(R.layout.activity_settings_home);

        initComponent();

        settingprofileimage=findViewById(R.id.settingprofileimage);

        setingscontacus=findViewById(R.id.setingscontacus);
        settingsaboutus=findViewById(R.id.settingsaboutus);
        settingsrating=findViewById(R.id.settingsrating);
        settingsshare=findViewById(R.id.settingsshare);
        settinslogout=findViewById(R.id.settinslogout);
        settinsreportbug=findViewById(R.id.settinsreportbug);
        settingprofilrback=findViewById(R.id.settingprofilrback);
        languagechoose=findViewById(R.id.languagechoose);
        rg = (RadioGroup)findViewById(R.id.radiogroup);
        languagechoose.setText( getResources().getString(R.string.English));

        showlang();


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.senglish:

                        setLocale("en");
                        setlang("English");
                        languagechoose.setText( getResources().getString(R.string.English));
                        startActivity(new Intent(Settings_home.this,Home_main.class));
                        break;
                    case R.id.smalayalam:

                        setLocale("ml");
                        setlang("Malayalam");
                        languagechoose.setText( getResources().getString(R.string.Malayalam));
                        group.setSelected(true);
                        startActivity(new Intent(Settings_home.this,Home_main.class));
                        break;
                    case R.id.shindi:

                        setLocale("hi");
                        setlang("Hindi");
                        languagechoose.setText( getResources().getString(R.string.Hindi));
                        startActivity(new Intent(Settings_home.this,Home_main.class));
                        break;

                }
            }
        });






        settingprofilrback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        setingscontacus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings_home.this, Contact.class));

            }
        });

        settingsaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings_home.this, About.class));

            }
        });

        settingsshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),imgBitmap, String.valueOf(R.string.app_name),null);
                Uri imgBitmapUri = Uri.parse(imgBitmapPath);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
                shareIntent.setType("*/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download"+"\n" + getResources().getString(R.string.app_name)+"  Official App From Play Store:https://play.google.com/store/apps/details?id="+getPackageName());
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                startActivity(Intent.createChooser(shareIntent, "Share this"));
            }
        });

        settinsreportbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"contact@techsays.in"});
                i.putExtra(Intent.EXTRA_SUBJECT, (Bundle) null);
                i.putExtra(Intent.EXTRA_TEXT   , (Bundle) null);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Settings_home.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        settingsrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Settings_home.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.dilog_rating);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);

                ((Button) dialog.findViewById(R.id.bt_join)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=" +context.getPackageName()  );
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                        }            }
                });

                ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });

        settinslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }





    private void logout() {

        AlertDialog.Builder   builder;
        builder = new AlertDialog.Builder(this, R.style.MyCustomAlert3);
        View v = getLayoutInflater().inflate(R.layout.dailog_logout, null);
        Button ok, cancel;
        builder.setCancelable(true);
        TextView logoutdilogname,logoutdilogemail;
        ImageView logoutimagediloghome;
        ok = v.findViewById(R.id.crane_ok);
        cancel = v.findViewById(R.id.crane_cancel);
        logoutimagediloghome = v.findViewById(R.id.logoutimagediloghome);
        logoutdilogname = v.findViewById(R.id.logoutdilogname);
        logoutdilogemail = v.findViewById(R.id.logoutdilogemail);
        Picasso.get().load(profilepic).into(logoutimagediloghome);
        logoutdilogemail.setText(email);
        logoutdilogname.setText(name);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences.Editor e = sh.edit();
                e.clear();
                e.apply();
                startActivity(new Intent(Settings_home.this, Login.class));

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings_home.this, Home_main.class));
                finish();

            }
        });

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }

    private void initComponent() {
        // info item_section
        bt_toggle_info = (ImageButton) findViewById(R.id.bt_toggle_info1);
        bt_hide_info = (Button) findViewById(R.id.bt_hide_info1);
        lyt_expand_info = (View) findViewById(R.id.lyt_expand_info1);
        bt_toggle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionInfo(bt_toggle_info);
            }
        });

        bt_hide_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionInfo(bt_toggle_info);
            }
        });



    }
    private void toggleSectionInfo(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_info, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_info);
        }
    }
    public void setLocale(String hi) {
        Locale locale=new Locale(hi);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getResources().updateConfiguration(configuration,getResources().getDisplayMetrics());
        SharedPreferences sh=getSharedPreferences("Settings",MODE_PRIVATE);
        SharedPreferences.Editor editor=sh.edit();
        editor.putString("my",hi);
        // editor.putString("lang",lang);
        editor.apply();
    }
    private void loadLocale() {

        SharedPreferences sharedPreferences=getSharedPreferences("Settings",MODE_PRIVATE);
        String language=sharedPreferences.getString("my","");
        // String language1=sharedPreferences.getString("lang","");
        //  languagechoose.setText(language1);
        setLocale(language);


    }
    public void setlang(String s)
    {
        SharedPreferences sh=getSharedPreferences("Settingslang",MODE_PRIVATE);
        SharedPreferences.Editor editor=sh.edit();
        editor.putString("mylang",s);
        // editor.putString("lang",lang);
        editor.apply();
    }
    public  void showlang()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("Settingslang",MODE_PRIVATE);
        String language=sharedPreferences.getString("mylang","");
        //   Toast.makeText(context,language, Toast.LENGTH_SHORT).show();
        languagechoose.setText(language);
    }
}