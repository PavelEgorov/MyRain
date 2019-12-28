package com.egorovsoft.myrain.recycleview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egorovsoft.myrain.R;
import com.egorovsoft.myrain.sql.City;
import com.egorovsoft.myrain.sql.sqlite.SQLLiteDataReader;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class sqlRecycleViewAdapter extends RecyclerView.Adapter<sqlRecycleViewAdapter.ViewHolder> {

    private SQLLiteDataReader noteDataReader;

    public sqlRecycleViewAdapter(SQLLiteDataReader noteDataReader) {
        this.noteDataReader = noteDataReader;
    }

    @NonNull
    @Override
    public sqlRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_sql_data, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull sqlRecycleViewAdapter.ViewHolder holder, int position) {
        holder.bind(noteDataReader.getPosition(position));
    }

    @Override
    public int getItemCount() {
        return noteDataReader.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textNote;
        private City note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNote = itemView.findViewById(R.id.textView_City_name_card_view);
        }

        public void bind(City note){
            this.note = note;
            textNote.setText(note.getCityName());
        }

    }
}
