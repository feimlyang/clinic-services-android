package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.UserRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *       AdminServiceAdapter is the adapter for RecycleView layout implemented in AdminAccountFragment.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminAccountFragment;
 */

public class AdminAccountAdapter extends RecyclerView.Adapter < AdminAccountAdapter.ViewHolder>{

   private ArrayList<String> userNameList;
   private Result<ArrayList<String>> result;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView currentUserName;

        public ViewHolder(View view)
        {
            // Assign to layout component
            super(view);
            currentUserName = view.findViewById(R.id.admin_account_item);
        }
    }

    // Get Account usernames
    public AdminAccountAdapter(ArrayList<String>userNames)
    {
        //TODO:在这里把result里的东西拆到userNameList里qwq

        UserRepository userRepo = UserRepository.getInstance();
        MutableLiveData<Result> currentUserNames = (MutableLiveData) userRepo.getUsername();
        currentUserNames.postValue(result);


    }

    @Override
    public AdminAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_accounts_list_view,parent,false);
        AdminAccountAdapter.ViewHolder adminViewHolder = new  AdminAccountAdapter.ViewHolder(view);
        return adminViewHolder;
    }

    @Override
    public void onBindViewHolder(AdminAccountAdapter.ViewHolder holder, int position){
        // Set Category
        String user = userNameList.get(position);
        holder.currentUserName.setText(user);
    }

    @Override
    public int getItemCount()
    {
        return userNameList.size();
    }
}

