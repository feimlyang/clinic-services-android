package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.model.Service;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *       AdminServiceAdapter is the adapter for RecycleView layout implemented in AdminServiceFragment.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminServiceFragment;
 */

public class AdminServiceAdapter extends RecyclerView.Adapter <AdminServiceAdapter.ViewHolder>
{
    final private ArrayList<String> categoryList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView categoryName;
        TextView subCategoryName;
        TextView serviceName;


        public ViewHolder(View view)
        {
            // Assign to layout component
            super(view);
            categoryName = (TextView)view.findViewById(R.id.admin_category_item);
            //subCategoryName = (TextView)view.findViewById(R.id.admin_sub_category_item);
        }
    }

    // Get a Service object, store all items
    public AdminServiceAdapter(ArrayList<String> categoryList )
    {
        this.categoryList = new ArrayList<String>(categoryList);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_service_list_view,parent,false);
        ViewHolder serviceViewHolder = new ViewHolder(view);
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        // Set Category
        String category = categoryList.get(position);
        holder.categoryName.setText(category);
    }

    @Override
    public int getItemCount()
    {
        return categoryList.size();
    }

}
