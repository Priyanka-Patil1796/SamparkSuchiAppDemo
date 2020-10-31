package com.example.samparksuchiapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import com.example.samparksuchiapplication.Model.DataProccessor;
import com.example.samparksuchiapplication.R;
import java.util.Calendar;
import java.util.List;

public class ContactDetailsAdapter extends RecyclerView.Adapter<ContactDetailsAdapter.ViewHolder> {
    List<ContactDetailsModel> list;
    Context context;
    NumberCallBack numberCallBack;

    public ContactDetailsAdapter(List<ContactDetailsModel> list,Context context,NumberCallBack numberCallBack){
        this.list = list;
        this.context = context;
        this.numberCallBack = numberCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_details_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ContactDetailsModel model =list.get(position);

            try {
                DataProccessor proccessor = new DataProccessor(context);

                holder.name.setText(model.getContactName());
                String[] date1 = null;
                if (model.getBirthDate().equalsIgnoreCase("null")) {

                } else {
                    String bdate = model.getBirthDate();
                    date1 = bdate.split("-");
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
                }

                String[] date2 = null;
                if (model.getAnniversaryDate().equalsIgnoreCase("null") || model.getAnniversaryDate().equals("null")) {

                } else {
                    String Adate = model.getAnniversaryDate();
                    date2 = Adate.split("-");
                    Calendar beginTime1 = Calendar.getInstance();
                    beginTime1.set(Integer.parseInt(date2[2]), Integer.parseInt(date2[1]), Integer.parseInt(date2[0]));
                }

                int bDay = proccessor.getIntDay("Day");
                int bMonth = proccessor.getIntMonth("Month");

                if (!model.getBirthDate().equals("null") && !model.getAnniversaryDate().equals("null")) {
                    try {
                        if (bDay == Integer.parseInt(date1[2]) && bMonth == Integer.parseInt(date1[1]) && bDay == Integer.parseInt(date2[2]) && bMonth == Integer.parseInt(date2[1])) {
                            holder.bdate.setText("Birth Date:" + model.getBirthDate() + "\n" + "Anniversary Date:" + model.getAnniversaryDate());
                        } else if (bDay == Integer.parseInt(date1[2]) && bMonth == Integer.parseInt(date1[1])) {
                            holder.bdate.setText("B Date:" + model.getBirthDate());
                        } else if (bDay == Integer.parseInt(date2[2]) && bMonth == Integer.parseInt(date2[1])) {
                            holder.bdate.setText("Anniversary Date:" + model.getAnniversaryDate());
                        } else {
                            // holder.number.setVisibility(View.GONE);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if (!model.getBirthDate().equals("null") && model.getAnniversaryDate().equals("null")) {
                    try {
                        if (bDay == Integer.parseInt(date1[2]) && bMonth == Integer.parseInt(date1[1])) {
                            holder.bdate.setText("B Date:" + model.getBirthDate());
                        } else {
//                      holder.linearLayout.setVisibility(View.GONE);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if (model.getBirthDate().equals("null") && !model.getAnniversaryDate().equals("null")) {
                    try {
                        if (bDay == Integer.parseInt(date2[2]) && bMonth == Integer.parseInt(date2[1])) {
                            holder.bdate.setText("Anniversary Date:" + model.getAnniversaryDate());
                        } else {
//                       holder.linearLayout.setVisibility(View.GONE);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                if (model.getPhoneNumber() != null && model.getPhonenNumber1() != null) {
                    holder.number.setText(model.getPhoneNumber() + "\n" + model.getPhonenNumber1());
                } else if (model.getPhoneNumber() != null && model.getPhonenNumber1() == null) {
                    holder.number.setText(model.getPhoneNumber());
                } else if (model.getPhoneNumber() == null && model.getPhonenNumber1() != null) {
                    holder.number.setText(model.getPhonenNumber1());
                }

                holder.number.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (model.getPhoneNumber() != null) {
                            numberCallBack.getNumber(model.getPhoneNumber());
                        } else if (model.getPhonenNumber1() != null) {
                            numberCallBack.getNumber(model.getPhonenNumber1());
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,bdate,number;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            bdate = itemView.findViewById(R.id.tvdate);
            number = itemView.findViewById(R.id.tvNumber);
            linearLayout = itemView.findViewById(R.id.ll_o);
            number.setPaintFlags(number.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//            adate = itemView.findViewById(R.id.tvADate);
//            number1 = itemView.findViewById(R.id.tvPhoneNumber2);
        }
    }

    public interface NumberCallBack{
        void getNumber(String phoneNumber);
    }
}
