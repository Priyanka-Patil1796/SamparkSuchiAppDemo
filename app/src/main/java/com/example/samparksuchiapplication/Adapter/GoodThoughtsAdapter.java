package com.example.samparksuchiapplication.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.samparksuchiapplication.Model.GoodThoughtsModel;
import com.example.samparksuchiapplication.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GoodThoughtsAdapter extends PagerAdapter {
    List<GoodThoughtsModel> list;
    Context context;
    ViewMoreCallBack viewMoreCallBack;
    LayoutInflater layoutInflater;

    public GoodThoughtsAdapter(List<GoodThoughtsModel> list, Context context, ViewMoreCallBack viewMoreCallBack)
    {
        try{
            this.list = list;
            this.context = context;
            this.viewMoreCallBack = viewMoreCallBack;
        } catch (Exception e) {
            Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
           layoutInflater = LayoutInflater.from(context);
           View view = layoutInflater.inflate(R.layout.thoughts_view_pager, container, false);

            //ImageView imageView = view.findViewById(R.id.ivNotice);
            TextView title = view.findViewById(R.id.tvNoticeTitle);
            TextView des = view.findViewById(R.id.tvNoticeDes);
            TextView tv = view.findViewById(R.id.tvViewMore);

            try{
                if (list.get(position).getViewMore().equalsIgnoreCase("View More")) {
                    tv.setVisibility(View.VISIBLE);
                    SpannableString content = new SpannableString("View More");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    tv.setText(content);
                    //imageView.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);
                    des.setVisibility(View.GONE);

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewMoreCallBack.getViewMore();
                        }
                    });
                } else {
                    title.setText(list.get(position).getTitle());
                    des.setText(list.get(position).getDescription());

                    if (list.get(position).getAttachment().equalsIgnoreCase("") ||
                            list.get(position).getAttachment().equalsIgnoreCase(null) ||
                            list.get(position).getAttachment().equals(null)) {
                       // imageView.setImageResource(R.drawable.biyani);
                    } else {
                       // Picasso.with(context).load(list.get(position).getAttachment()).into(imageView);
                    }
                }
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (list.get(position).equals(3)) {
                            Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                        } else {
                            viewMoreCallBack.getPagePosition(list.get(position).getNoticeId());
                        }
                    }
                });
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        viewMoreCallBack.getImageUrl(list.get(position).getAttachment());
//                    }
//                });
            } catch (Exception e) {
                Toast.makeText(context,""+e.toString(),Toast.LENGTH_SHORT).show();
            }
            container.addView(view,0);
            return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    public interface ViewMoreCallBack{
        void getViewMore();
        void getPagePosition(int noticeId);
        void getImageUrl(String attachment);
    }
}
