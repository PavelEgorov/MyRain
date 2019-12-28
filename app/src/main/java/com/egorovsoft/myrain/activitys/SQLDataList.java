package com.egorovsoft.myrain.activitys;

import android.os.Bundle;

import com.egorovsoft.myrain.MainPresenter;
import com.egorovsoft.myrain.R;
import com.egorovsoft.myrain.recycleview.adapter.sqlRecycleViewAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SQLDataList extends AppCompatActivity {
    private sqlRecycleViewAdapter adapter;         // Адаптер для RecyclerView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqldata_list);

        MainPresenter.getInstance().openSQLConnection();
        RecyclerView recyclerView = findViewById(R.id.recycleView_sqldata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new sqlRecycleViewAdapter(MainPresenter.getInstance().getDataReader());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        MainPresenter.getInstance().closeSQLConnection();
    }
}
