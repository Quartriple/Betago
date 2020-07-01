package com.example.betago.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.betago.Adapter.PlanAdapter;
import com.example.betago.Model.Plan;
import com.example.betago.PlanActivity;
import com.example.betago.PlanDpActivity;
import com.example.betago.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btn_plan;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;

    private List<Plan> planList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_plan, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        mDatabase  = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Plan");

        btn_plan = view.findViewById(R.id.btn_plan);
        btn_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PlanActivity.class));
            }
        });

        planList = new ArrayList<>();
        mAdapter = new PlanAdapter(planList, getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() != null){
                    int position = (int)v.getTag();
                    Plan plan = ((PlanAdapter)mAdapter).getPlan(position);
                    Intent intent = new Intent(getContext(), PlanDpActivity.class);
                    intent.putExtra("title", plan.getTitle());
                    startActivity(intent);
                }
            }
        });
        displayPlan();
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public void  displayPlan(){
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Plan plan = snapshot.getValue(Plan.class);
                ((PlanAdapter)mAdapter).addPlan(plan);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Plan plan = snapshot.getValue(Plan.class);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
