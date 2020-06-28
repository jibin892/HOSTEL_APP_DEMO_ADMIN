package in.techsays.hostel.Filter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.techsays.hostel.Location.Tools;
import in.techsays.hostel.R;

import static in.techsays.hostel.Location.Tools.toggleArrow;

public class Filter extends AppCompatActivity {
    Button todayfilter,tomorrowfilter,thisweekfilter,thismounthfilter,thisyearfilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }
        setContentView(R.layout.activity_fiter);
        todayfilter = findViewById(R.id.todayfilter);
        tomorrowfilter = findViewById(R.id.tomorrowfilter);

        thisweekfilter = findViewById(R.id.thisweekfilter);
        thismounthfilter = findViewById(R.id.thismounthfilter);
        thisyearfilter = findViewById(R.id.thisyearfilter);












        initToolbar();
    }

    private void initToolbar() {
        Tools.setSystemBarColor(this, R.color.blue_A700);
    }

//    public void eventClick(View view) {
//        if (view instanceof Button) {
//            if (todayfilter.isSelected()) {
//
//                todayfilter.setBackgroundColor(getResources().getColor(R.color.grey_60));
//            } else if(tomorrowfilter.isSelected()) {
//
//                tomorrowfilter.setTextColor(getResources().getColor(R.color.grey_60));
//
//             }
//            todayfilter.setSelected(!todayfilter.isSelected());
//        }

   // }
   public void eventClick(View view) {
       int id = view.getId();
       switch (id) {
           // replace with different fragments
           case R.id.todayfilter: {
          Calendar cal = Calendar.getInstance();
        cal.get(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_WEEK, cal.SUNDAY);
        String firstWkDay = String.valueOf(cal.getTime());
        //cal.set(Calendar.DAY_OF_WEEK, cal.SUNDAY);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        String lastWkDay =  String.valueOf(cal.getTime());

               Toast.makeText(Filter.this, lastWkDay, Toast.LENGTH_SHORT).show();
               todayfilter.setTextColor(getResources().getColor(R.color.green_A700));
               todayfilter.setSelected(!todayfilter.isSelected());

           }
           break;

           case R.id.tomorrowfilter: {
               Calendar cal = Calendar.getInstance();
               cal.get(Calendar.DAY_OF_WEEK);
               cal.set(Calendar.DAY_OF_WEEK, cal.SUNDAY);
               String firstWkDay = String.valueOf(cal.getTime());
              // int aa=Integer.parseInt(firstWkDay)-1;
               //cal.set(Calendar.DAY_OF_WEEK, cal.SUNDAY);
               cal.add(Calendar.DAY_OF_WEEK, 6);
               String lastWkDay =  String.valueOf(cal.getTime());


               Toast.makeText(Filter.this, String.valueOf(lastWkDay), Toast.LENGTH_SHORT).show();
               tomorrowfilter.setTextColor(getResources().getColor(R.color.green_A700));
               tomorrowfilter.setSelected(!tomorrowfilter.isSelected());

           }


           break;

           case R.id.thisweekfilter: {
               Calendar cal = Calendar.getInstance();
               cal.get(Calendar.YEAR);
               cal.set(Calendar.DAY_OF_WEEK, cal.SUNDAY);
               String firstWkDay = String.valueOf(cal.getTime());
                //cal.set(Calendar.DAY_OF_WEEK, cal.SUNDAY);
               cal.add(Calendar.DAY_OF_WEEK, 6);
               String lastWkDay =  String.valueOf(cal.getTime());

               Toast.makeText(Filter.this, firstWkDay+lastWkDay, Toast.LENGTH_SHORT).show();
               thisweekfilter.setTextColor(getResources().getColor(R.color.green_A700));
               thisweekfilter.setSelected(!thisweekfilter.isSelected());

           }


           break;

           case R.id.thismounthfilter: {

            Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = cal.getTime();

        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal

        Date lastDateOfPreviousMonth = cal.getTime();

        String st=String.valueOf(firstDateOfPreviousMonth);
        String en=String.valueOf(lastDateOfPreviousMonth);
               Toast.makeText(Filter.this, String.valueOf(firstDateOfPreviousMonth)+String.valueOf(en), Toast.LENGTH_SHORT).show();
               thismounthfilter.setTextColor(getResources().getColor(R.color.green_A700));
               thismounthfilter.setSelected(!thismounthfilter.isSelected());

           }


           break;
           case R.id.thisyearfilter: {


               Calendar calendar = Calendar.getInstance();

               calendar.add(Calendar.YEAR, 1);
               calendar.set(Calendar.DAY_OF_YEAR, 1);
               Date YearFirstDay = calendar.getTime();
               calendar.set(Calendar.MONTH, 11);
               calendar.set(Calendar.DAY_OF_MONTH, 31);
               Date YearLastDay = calendar.getTime();


               Toast.makeText(Filter.this, String.valueOf(YearFirstDay)+String.valueOf(YearLastDay), Toast.LENGTH_SHORT).show();
               thisyearfilter.setTextColor(getResources().getColor(R.color.green_A700));
               thisyearfilter.setSelected(!thisyearfilter.isSelected());

           }


           break;
       }
   }
}