package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.MyTeamModel;
import com.softcore.vtpsales.ViewModel.MyTeamViewModel;
import com.softcore.vtpsales.databinding.ActivityMyTeamBinding;
import com.softcore.vtpsales.Adaptors.AdapterMyTeam;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

public class MyTeamActivity extends AppCompatActivity {

    ActivityMyTeamBinding binding;
    AdapterMyTeam adapterMyTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

  //      ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("My Team");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterMyTeam = new AdapterMyTeam();
        binding.recyclerView.setAdapter(adapterMyTeam);

        String EmpNo = AppUtil.getStringData(getApplicationContext(),"EmpCode","");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        GetMyTeamList(EmpNo,DbName);

    }

    private void GetMyTeamList(String DbName,String EmpNo) {
        System.out.println(""+DbName+" "+EmpNo);
//Testing
//        String data = "[ { \"ExtEmpNo\": \"V20029\", \"name\": \"AJINKYA YADAV\", \"Role\": \"Member\" }, { \"ExtEmpNo\": \"V20029\", \"name\": \"Avinash Kumbhar\", \"Role\": \"Member\" } ]";
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            Type listType = new TypeToken<List<MyTeamModel>>() {}.getType();
//            List<MyTeamModel> myTeamModels = new Gson().fromJson(jsonArray.toString(), listType);
//            adapterMyTeam.setData(myTeamModels);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        MyTeamViewModel myTeamViewModel= new ViewModelProvider(this).get(MyTeamViewModel.class);
        myTeamViewModel.getMyTeamInfo(DbName,EmpNo).observe(this, new Observer<CommanResorce<List<MyTeamModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<MyTeamModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    adapterMyTeam.setData(listCommanResorce.data);


                }


            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}