package com.softcore.vtpsales;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.MyTeamMember;

import java.util.List;

public class MyTeamActivity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyTeamAdapter adapter;
    private MyTeamRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new MyTeamRepository();

        // Replace "ENVIIRO_LIVE" and "V20029" with your actual values
        LiveData<List<MyTeamMember>> myTeamLiveData = repository.getMyTeamMembers("ENVIIRO_LIVE", "V20029");
        myTeamLiveData.observe(this, new Observer<List<MyTeamMember>>() {
            @Override
            public void onChanged(List<MyTeamMember> teamMembers) {
                adapter = new MyTeamAdapter(MyTeamActivity2.this, teamMembers);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
