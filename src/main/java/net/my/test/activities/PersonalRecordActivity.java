package net.my.test.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.my.test.R;
import net.my.test.adapter.UserScoresAdapter;

public class PersonalRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_record);

        RecyclerView recordsRV = findViewById(R.id.rv_records);
        recordsRV.setLayoutManager(new LinearLayoutManager(this));
        recordsRV.setAdapter(new UserScoresAdapter(this));
    }
}
