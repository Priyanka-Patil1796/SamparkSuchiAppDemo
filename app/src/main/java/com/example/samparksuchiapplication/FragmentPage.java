package com.example.samparksuchiapplication;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.samparksuchiapplication.DataBase.DBHelper;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import com.example.samparksuchiapplication.Model.DataProccessor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentPage extends Fragment {
    private static final Object MODE_APPEND = 1;
    LinearLayout linearLayout;
    DBHelper helper;
    List<ContactDetailsModel> myList;
    ViewPager viewPager;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] colors = null;
    LinearLayout sliderDotspanel;
    private int dotscount;
    int position;
    private ImageView[] dots;
    TextView tvMonthName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();
        position = bundle.getInt("pageNumber");
        view = inflater.inflate(R.layout.page_fragment_layout, container, false);
        tvMonthName = view.findViewById(R.id.tv_monthName);
        linearLayout = view.findViewById(R.id.ll_monthly_calender);
        viewPager = view.findViewById(R.id.ViewPagerUser);
        sliderDotspanel = view.findViewById(R.id.SliderDots);
        helper = new DBHelper(getContext());
        myList = new ArrayList<>();

        if (position>0)
        {
            getMonthlyCalender();
        } else
        {
            getCuurentMonthCalender();
        }
        return view;
      }

    private void getMonthlyCalender() {
        DataProccessor dataProccessor = new DataProccessor(getContext());
        try {
           // int month = dataProccessor.getInt("month");
            Calendar cdate = Calendar.getInstance();
            System.out.println("Today date "+cdate.getTime());
            cdate.add(Calendar.MONTH, position);
            System.out.println("Next month date "+cdate.getTime());
            Log.e("NEXTMONTHDATE","Next month date "+cdate.getTime());
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = df.format(cdate.getTime());
            String date = formattedDate;
            String[] date1 = date.split("/");
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
            Log.e("ChangedDay", "" + Integer.parseInt(date1[0]));
            Log.e("ChangedMonth", "" + Integer.parseInt(date1[1]));
            String monthName = new SimpleDateFormat("MMMM").format(cdate.getTime());
            tvMonthName.setText(monthName);

            myList = helper.getMonth(Integer.parseInt(date1[1]));

            for (int i = 0; i < myList.size(); i++)
            {
                if (myList.get(i).getAnniversaryDate().equalsIgnoreCase("null"))
                {
                    if (myList.get(i).getAMonth() == Integer.parseInt(date1[1])) {
                        myList.remove(i);
                        i--;
                    } else {

                    }
                }
            }
            getList(myList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCuurentMonthCalender() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        String date = formattedDate;
        String[] date1 = date.split("/");
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
        Log.e("Day", "" + Integer.parseInt(date1[0]));
        Log.e("Month", "" + Integer.parseInt(date1[1]));
        String monthName = new SimpleDateFormat("MMMM").format(c.getTime());
        tvMonthName.setText(monthName);

        myList = helper.getMonth(Integer.parseInt(date1[1]));

        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getAnniversaryDate().equalsIgnoreCase("null")) {
                if (myList.get(i).getAMonth() == Integer.parseInt(date1[1])) {
                    myList.remove(i);
                    i--;
                } else {

                }
            }
        }
        getList(myList);
    }

    private void getList(List<ContactDetailsModel> myList) {
        LayoutInflater linflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for ( int i=0;i<myList.size();i++)
        {
            View myView = linflater.inflate(R.layout.search_people_result_list, null); //here item is the the layout you want to inflate
            TextView name = myView.findViewById(R.id.tvName);
            TextView city = myView.findViewById(R.id.tvcity);
            TextView number = myView.findViewById(R.id.tvNumber);
            TextView bDate = myView.findViewById(R.id.tvBDate);
            TextView aDate = myView.findViewById(R.id.tvADate);
            TextView occupation = myView.findViewById(R.id.tvoccupation);

            name.setText(myList.get(i).getContactName());
            bDate.setText(myList.get(i).getBirthDate());
//            number.setText(myList.get(i).getPhoneNumber());
            city.setText(myList.get(i).getCity());
            aDate.setText(myList.get(i).getAnniversaryDate());
            occupation.setText(myList.get(i).getOccupation());

            if (myList.get(i).getAnniversaryDate().equals(null) || myList.get(i).getAnniversaryDate().equalsIgnoreCase("null")){
                aDate.setText("-");
            }
            if (myList.get(i).getPhoneNumber()!=null && myList.get(i).getPhonenNumber1()!=null)
            {
                number.setText(myList.get(i).getPhoneNumber()+"\n"+myList.get(i).getPhonenNumber1());
            }
            else if (myList.get(i).getPhoneNumber()!=null && myList.get(i).getPhonenNumber1()==null)
            {
                number.setText(myList.get(i).getPhoneNumber());
            }
            else if (myList.get(i).getPhoneNumber()==null && myList.get(i).getPhonenNumber1()!=null)
            {
                number.setText(myList.get(i).getPhonenNumber1());
            }
            linearLayout.addView(myView);
        }
    }
}