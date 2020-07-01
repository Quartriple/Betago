package com.example.betago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betago.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.util.HashMap;

public class PlanActivity extends AppCompatActivity {

    private MaterialEditText pTitle;
    private String pAuthor;
    private MaterialEditText pDescription;
    private Button btn_submit;
    private Button btn_file;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("기획하기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        pTitle = findViewById(R.id.plan_title);
        pDescription = findViewById(R.id.plan_description);

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_title = pTitle.getText().toString();

                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        pAuthor = user.getUsername();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                String txt_description = pDescription.getText().toString();

                if(TextUtils.isEmpty(txt_title) || TextUtils.isEmpty(pAuthor) || TextUtils.isEmpty(txt_description)){
                    Toast.makeText(PlanActivity.this, "빈칸을 모두 채.우.시.오.", Toast.LENGTH_SHORT).show();
                } else{
                    submitPlan(txt_title, pAuthor, txt_description);

                }
            }
        });
        btn_file = findViewById(R.id.btn_file);
        btn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlanActivity.this, "업데이트를 기dari three.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void submitPlan(String title, String author, String description){

        mRef = FirebaseDatabase.getInstance().getReference("Plan");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("author", author);
        hashMap.put("body", description);

        mRef.child(title).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PlanActivity.this, "무언가 잘못되고 있습니다. 그러나 저도 모릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
