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
import com.example.betago.Model.User;
import com.example.betago.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private Context mContext;
    private List<News> mNews;
    private static View.OnClickListener onClickListener;

    public NewsAdapter(Context context, List<News> newsList, View.OnClickListener  OnClickListener){

        mNews = newsList;
        Fresco.initialize(context);
        onClickListener = OnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false );
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = mNews.get(position);
        holder.TextView_title.setText(news.getTitle());
        holder.TextView_description.setText(news.getContent());

        Uri uri = Uri.parse(news.getUrlToImage());

        holder.ImageView_title.setImageURI(uri);
        holder.rootView.setTag(position);
    };

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView ImageView_title;
        public TextView TextView_title;
        public TextView TextView_description;
        public View rootView;


        public ViewHolder(View itemView){
            super(itemView);
            ImageView_title = itemView.findViewById(R.id.ImageView_title);
            TextView_title = itemView.findViewById(R.id.TextView_title);
            TextView_description = itemView.findViewById(R.id.TextView_description);
            rootView = itemView;

            rootView.setClickable(true);
            rootView.setEnabled(true);
            rootView.setOnClickListener(onClickListener);
        }
    }

    public News getNews(int position){
        return mNews != null ? mNews.get(position) :  null;
    }
}
