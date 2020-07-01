package com.example.betago;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.betago.Model.Plan;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PlanDpActivity extends AppCompatActivity {

    private TextView title, author, body;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_dp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String plan_id = intent.getStringExtra("title");
        title = findViewById(R.id.display_title);
        author = findViewById(R.id.display_author);
        body = findViewById(R.id.display_body);

        mRef = FirebaseDatabase.getInstance().getReference("Plan").child(plan_id);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Plan plan = snapshot.getValue(Plan.class);
                title.setText(plan.getTitle());
                body.setText(plan.getBody());
                author.setText(plan.getAuthor());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
