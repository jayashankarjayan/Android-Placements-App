package com.example.jayashankarjayan.placements;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jayashankarjayan.placements.functions.Functions;

import java.util.List;

/**
 * Created by Jayashankar Jayan on 11/25/2017.
 */

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.MyViewHolder> {
    private List<NoticeRecyclerViewContent> mainList;
    int layout;

    private int lastPosition = -1;
    Context context;
    String MORE = " [ read more ]";
    //    public TextView title, jobName;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView activity_notices_text_view_notice_title, activity_notices_text_view_notice_content,
                activity_notices_text_view_adder_name,activity_notices_text_view_date_of_issue;
        public CardView notice_row_card_view;
        public RelativeLayout notice_home_row_relative_layout;

        public MyViewHolder(View view) {
            super(view);
            if(layout == R.layout.notice_row)
            {
                notice_row_card_view = (CardView)view.findViewById(R.id.notice_row_card_view);
                activity_notices_text_view_notice_title = (TextView) view.findViewById(R.id.activity_notices_text_view_notice_title);
                activity_notices_text_view_notice_content = (TextView) view.findViewById(R.id.activity_notices_text_view_notice_content);
                activity_notices_text_view_adder_name = (TextView)view.findViewById(R.id.activity_notices_text_view_adder_name);
            }
            else
            {
                notice_home_row_relative_layout= (RelativeLayout)view.findViewById(R.id.notice_home_row_relative_layout);
                activity_notices_text_view_notice_title = (TextView)view.findViewById(R.id.home_notices_text_view_notice_title);
                activity_notices_text_view_date_of_issue = (TextView)view.findViewById(R.id.activity_notices_text_view_date_of_issue);
            }
        }


        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }


    public NoticeRecyclerAdapter(List<NoticeRecyclerViewContent> mainList, Context context, int layout) {
        this.mainList = mainList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public NoticeRecyclerAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        final MyViewHolder holder = new MyViewHolder(itemView);

        final Functions functions = new Functions(parent.getContext());
        functions.applyFont(parent.getContext(),holder.activity_notices_text_view_notice_title,"bold");
        if(layout == R.layout.notice_row)
        {
            functions.applyFont(parent.getContext(),holder.activity_notices_text_view_notice_content,"bold");
            functions.applyFont(parent.getContext(),holder.activity_notices_text_view_adder_name,"bold");
        }

        return new NoticeRecyclerAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(NoticeRecyclerAdapter.MyViewHolder holder, final int position) {
        final NoticeRecyclerViewContent data = mainList.get(position);

        SpannableString underLine = new SpannableString(data.getNoticeTitle());
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 0);
        holder.activity_notices_text_view_notice_title.setText(underLine);

        String notice_content = data.getNoticeContent();
        String notice_date = data.getDate_of_issue();
        int MAX_CHAR = 50;
        int maxLength = (notice_content.length() < MAX_CHAR)?notice_content.length():MAX_CHAR;
        notice_content = notice_content.substring(0, maxLength);
        if(maxLength >= MAX_CHAR)
        {
            notice_content = notice_content+MORE;
        }

        if(layout == R.layout.notice_row)
        {
            holder.activity_notices_text_view_notice_content.setText(Html.fromHtml(notice_content));
            holder.activity_notices_text_view_adder_name.setText(data.getAdderName());

            holder.notice_row_card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToIndividualNotices(position);
                }
            });

            holder.activity_notices_text_view_notice_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToIndividualNotices(position);
                }
            });
            holder.activity_notices_text_view_notice_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToIndividualNotices(position);
                }
            });
            setAnimation(holder.itemView, position);
        }
        else
        {
            holder.activity_notices_text_view_date_of_issue.setText(notice_date);
            holder.notice_home_row_relative_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToIndividualNotices(position);
                }
            });

            holder.activity_notices_text_view_notice_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToIndividualNotices(position);
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToIndividualNotices(position);
            }
        });


    }

    private void goToIndividualNotices(int position) {
        NoticeRecyclerViewContent data = mainList.get(position);

        Intent goToIndividualNotices = new Intent(context.getApplicationContext(),IndividualNotices.class);
        goToIndividualNotices.putExtra("notice_title",data.getNoticeTitle());
        goToIndividualNotices.putExtra("notice_content",data.getNoticeContent());
        goToIndividualNotices.putExtra("notice_adder_name",data.getAdderName());
        goToIndividualNotices.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goToIndividualNotices);
    }


    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context , android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
