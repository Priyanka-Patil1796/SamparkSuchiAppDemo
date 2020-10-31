package com.example.samparksuchiapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import com.example.samparksuchiapplication.R;
import java.util.List;

public class CalenderAdapter extends PagerAdapter {
    List<ContactDetailsModel> myList;
    LayoutInflater layoutInflater;
    Context context;

    public CalenderAdapter(List<ContactDetailsModel> list,Context context)
    {
        this.myList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        final View myView = layoutInflater.inflate(R.layout.search_people_result_list,container,false);

        TextView name = myView.findViewById(R.id.tvName);
        TextView city = myView.findViewById(R.id.tvcity);
        TextView number = myView.findViewById(R.id.tvNumber);
        TextView bDate = myView.findViewById(R.id.tvBDate);
        TextView aDate = myView.findViewById(R.id.tvADate);
        TextView occupation = myView.findViewById(R.id.tvoccupation);

        name.setText(myList.get(position).getContactName());
        bDate.setText(myList.get(position).getBirthDate());
        city.setText(myList.get(position).getCity());
        aDate.setText(myList.get(position).getAnniversaryDate());
        occupation.setText(myList.get(position).getOccupation());

        if (myList.get(position).getAnniversaryDate().equals(null) || myList.get(position).getAnniversaryDate().equalsIgnoreCase("null")){
            aDate.setText("-");
        }
        if (myList.get(position).getPhoneNumber()!=null && myList.get(position).getPhonenNumber1()!=null)
        {
            number.setText(myList.get(position).getPhoneNumber()+"\n"+myList.get(position).getPhonenNumber1());
        }
        else if (myList.get(position).getPhoneNumber()!=null && myList.get(position).getPhonenNumber1()==null)
        {
            number.setText(myList.get(position).getPhoneNumber());
        }
        else if (myList.get(position).getPhoneNumber()==null && myList.get(position).getPhonenNumber1()!=null)
        {
            number.setText(myList.get(position).getPhonenNumber1());
        }

        container.addView(myView,0);
        return myView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
