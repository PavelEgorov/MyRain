package com.egorovsoft.myrain.recycleview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.egorovsoft.myrain.R;
import com.egorovsoft.myrain.customview.SunView;
import com.egorovsoft.myrain.recycleview.Weekend;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeekendAdapter extends RecyclerView.Adapter<WeekendAdapter.ViewHolder> {

    private Weekend[] weekends;

    public WeekendAdapter(Weekend[] data){
        this.weekends = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        ///Если подставляю фрагмент температуры, то вылетает ошибка не уникального id фрагмента.
        /// по этому в будущем в для CardView будет сделан фрагмент с температурой. Пока там строковое значение

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(weekends[position].getDayName());
    }

    @Override
    public int getItemCount() {
        return weekends.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView_dayName;
        private SunView sunView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textView_dayName = (TextView) itemView.findViewById(R.id.textView_DayName);
            sunView = (SunView) itemView.findViewById(R.id.sunView_day);
        }

        public TextView getTextView() {
            return textView_dayName;
        }

        @Override
        public void onClick(View v) {
            /// тут будет обработчик нажатия на элемент RecycleView
            sunView.setText("1");
            sunView.invalidate();
            Toast.makeText(v.getContext(), textView_dayName.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
