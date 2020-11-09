package com.example.samparksuchiapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import com.example.samparksuchiapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllRecordsAdapter extends RecyclerView.Adapter<AllRecordsAdapter.ViewHolder> {
    List<ContactDetailsModel> list;
    Context context;
    ItemviewCallBack itemviewCallBack;

    public AllRecordsAdapter(List<ContactDetailsModel> list, Context context,ItemviewCallBack itemviewCallBack){
        this.list = list;
        this.context = context;
        this.itemviewCallBack = itemviewCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_people_result_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllRecordsAdapter.ViewHolder holder, int position) {
        try {
            ContactDetailsModel model = list.get(position);

            holder.name.setText(model.getContactName());
            holder.city.setText(model.getCity());
            holder.number.setText(model.getPhoneNumber());
            holder.occupation.setText(model.getOccupation());

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String inputDateStr = model.getBirthDate();
            Date date = inputFormat.parse(inputDateStr);
            String BirthdayDate = outputFormat.format(date);
            holder.bDate.setText(BirthdayDate);

            DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            String inputDateStr1 = model.getAnniversaryDate();
            Date dateA = inputFormat1.parse(inputDateStr1);
            String AnniversaryDate = outputFormat1.format(dateA);
            holder.aDate.setText(AnniversaryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,city,number,bDate,aDate,occupation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             name = itemView.findViewById(R.id.tvName);
             city = itemView.findViewById(R.id.tvcity);
             number = itemView.findViewById(R.id.tvNumber);
             bDate = itemView.findViewById(R.id.tvBDate);
             aDate = itemView.findViewById(R.id.tvADate);
             occupation = itemView.findViewById(R.id.tvoccupation);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            itemviewCallBack.getCallBack(list.get(position).getContactName(),list.get(position).getOccupation(),list.get(position).getCity(),
                    list.get(position).getAddress(),list.get(position).getEmailId(),
                    list.get(position).getPhoneNumber(),list.get(position).getPhonenNumber1(),
                    list.get(position).getBirthDate(),list.get(position).getAnniversaryDate(),
                    list.get(position).getRecordId(),list.get(position).getMemberCode(),list.get(position).getPhotoUrl());
        }
    }

    public interface ItemviewCallBack {
        void getCallBack(String contactName, String occupation, String city, String address, String emailId, String phoneNumber, String phonenNumber1, String birthDate, String anniversaryDate, int recordId, String memberCode, String photoUrl);
    }
}
