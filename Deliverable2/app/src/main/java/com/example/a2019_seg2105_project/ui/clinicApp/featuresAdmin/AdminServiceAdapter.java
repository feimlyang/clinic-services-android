package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;

import java.util.ArrayList;

/**
 *       AdminServiceAdapter is the adapter for RecycleView layout implemented in AdminServiceFragment.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminServiceFragment;
 */

public class AdminServiceAdapter extends RecyclerView.Adapter <AdminServiceAdapter.ViewHolder>
{
    private ArrayList<String> serviceList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView serviceName;
        public ViewHolder(View view)
        {
            // Assign to layout component
            super(view);
            serviceName = (TextView)view.findViewById(R.id.admin_category_item);
        }
    }

    // Get a Service object, store all items
    public AdminServiceAdapter(LiveData<Result> liveServiceData )
    {
//TODO:在这里把result里的东西拆到serviceList里qwq
        this.serviceList = new ArrayList<String>();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_service_list_view, parent,false);
        ViewHolder serviceViewHolder = new ViewHolder(view);
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        // Set Category
        String category = serviceList.get(position);
        holder.serviceName.setText(category);
    }

    @Override
    public int getItemCount()
    {
        return serviceList.size();
    }

}
