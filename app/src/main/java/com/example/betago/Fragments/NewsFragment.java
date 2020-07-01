package com.example.betago.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.betago.Adapter.NewsAdapter;
import com.example.betago.Model.News;
import com.example.betago.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        queue = Volley.newRequestQueue(getContext());
        getNews();
        return view;
    }

    public void getNews(){
        // Instantiate the RequestQueue.

        String url = "https://newsapi.org/v2/top-headlines?country=kr&category=technology&apiKey=e7efa750d31945a9b79d492256555457";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            List<News> news = new ArrayList<>();

                            for(int i = 0; i < arrayArticles.length(); i++ ){
                                JSONObject obj = arrayArticles.getJSONObject(i);



                                News newsData = new News();
                                newsData.setContent( obj.getString("description"));
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setUrl(obj.getString("url"));
                                news.add(newsData);
                            }

                            mAdapter = new NewsAdapter(getContext(),news, new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    if(v.getTag() != null){
                                        int position = (int)v.getTag();
                                        ((NewsAdapter)mAdapter).getNews(position);


                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(((NewsAdapter)mAdapter).getNews(position).getUrl()));
                                        startActivity(intent);
                                    }
                                }
                            });
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kichan", String.valueOf(error));
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
