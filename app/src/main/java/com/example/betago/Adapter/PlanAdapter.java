package com.example.betago.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.betago.Model.News;
import com.example.betago.Model.Plan;
import com.example.betago.Model.User;
import com.example.betago.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder>{
    private Context mContext;
    private List<Plan> mPlan;
    private static View.OnClickListener onClickListener;

    public PlanAdapter(List<Plan> planList, Context context, View.OnClickListener onClick){
        mPlan = planList;
        mContext = context;
        onClickListener = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false );
        return new PlanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plan plan = mPlan.get(position);

            holder.title.setText(plan.getTitle());
            holder.author.setText(plan.getAuthor());

            holder.rootView.setTag(position);
    };

    @Override
    public int getItemCount() {
        return mPlan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        View rootView;

        public ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            rootView = itemView;

            rootView.setClickable(true);
            rootView.setEnabled(true);
            rootView.setOnClickListener(onClickListener);
        }
    }

    public void addPlan(Plan plan){
        mPlan.add(plan);
        notifyItemInserted(mPlan.size() -1);
    }

    public Plan getPlan(int position){
        return mPlan.get(position);
    }
}
