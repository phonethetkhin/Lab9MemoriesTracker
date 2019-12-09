package com.example.lab9memoriestracker.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab9memoriestracker.MemoryDetail;
import com.example.lab9memoriestracker.Model.MemoryModel;
import com.example.lab9memoriestracker.R;

import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder> {
    List<MemoryModel> memoryModelList;

    public MemoryAdapter(List<MemoryModel> memoryModelList) {
        this.memoryModelList = memoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.memorylistitem,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.tvTitle.setText(memoryModelList.get(position).getTitle());
holder.tvDate.setText(memoryModelList.get(position).getMemoryDate());
    }

    @Override
    public int getItemCount() {
        return memoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvDate;
        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(v.getContext(), MemoryDetail.class);
                    Bundle b=new Bundle();
                    b.putParcelable("OldValue",memoryModelList.get(getAdapterPosition()));
                    i.putExtras(b);
                    v.getContext().startActivity(i);
                }
            });
            tvTitle=v.findViewById(R.id.tvTitle);
            tvDate=v.findViewById(R.id.tvDate);
        }
    }
}
