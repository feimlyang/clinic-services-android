package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.ui.clinicApp.navigation.AdminMainFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class AdminListServicesFragment extends Fragment {

    private ListView listOfServices;
    private List<Map<String, String>> servicesAttributes;
    private List<String> services;
    private Button returnButton;
    private AdminServiceViewModel serviceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        serviceViewModel = ViewModelProviders.of(this, new AdminServiceViewModelFactory()).get(AdminServiceViewModel.class);
        View root = inflater.inflate(R.layout.admin_service_list_view, container, false);
        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        services = new ArrayList<String>();
        servicesAttributes = new ArrayList<Map<String, String>>();
        listOfServices = (ListView) getActivity().findViewById(R.id.listViewServices);
        returnButton = (Button) getActivity().findViewById(R.id.serviceListReturnButton);
        serviceViewModel.getServicesListLiveData().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result == null) return;
                services.clear();
                servicesAttributes.clear();
                if(result instanceof Result.Failure || result instanceof Result.Error)
                {
                    Toast.makeText(getContext(), "Failed to list all services.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Result.Success<Map<String, Map<String, String>>> services_map;
                    try{
                        services_map  = (Result.Success<Map<String, Map<String, String>>>)(result);

                    }catch (Exception e)
                    {
                        Map<String, Map<String, String>> emptyMap = new HashMap<>();
                        services_map = new Result.Success<Map<String, Map<String, String>>>(emptyMap);
                    }
                    Map<String, Map<String, String>> resultMap = services_map.getData();
                    for(String key : resultMap.keySet())
                    {
                        services.add(key);
                        servicesAttributes.add(resultMap.get(key));
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(), android.R.layout.simple_list_item_1, services);
                listOfServices.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        listOfServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String serviceName = services.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setTitle(serviceName);
                dialogBuilder.setMessage("Category: " + servicesAttributes.get(position).get("category") + "\nSubCategory: " +
                        servicesAttributes.get(position).get("subcategory") + "\nRolePerforming: " +
                        servicesAttributes.get(position).get("rolePerforming"));
                dialogBuilder.setPositiveButton("OK", null);
                dialogBuilder.show();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.layout_admin_service, new AdminMainFragment());
                transaction.commit();
            }
        });
        serviceViewModel.listService();
    }
}
