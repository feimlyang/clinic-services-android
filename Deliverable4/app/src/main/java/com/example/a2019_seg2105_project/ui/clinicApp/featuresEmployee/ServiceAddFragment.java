package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ServiceAddFragment extends Fragment {
    private ListView listOfServices;
    private List<Map<String, String>> servicesAttributes;
    private List<String> services;
    private Button returnButton;
    private ClinicViewModel serviceViewModel;
    GlobalObjectManager helper = GlobalObjectManager.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        serviceViewModel = ViewModelProviders.of(this, new ClinicViewModelFactory()).get(ClinicViewModel.class);
        View root = inflater.inflate(R.layout.employee_fragment_addservice, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        services = new ArrayList<String>();
        servicesAttributes = new ArrayList<Map<String, String>>();
        listOfServices = (ListView) getActivity().findViewById(R.id.listViewServices);
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        serviceViewModel.getAvailableServicesData.observe(this, new Observer<Result>() {
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
                final String serviceName = services.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setTitle(serviceName);
                dialogBuilder.setMessage("Would you like to add it to your clinic?");
                dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        serviceViewModel.addServiceToProfile(helper.getCurrentUsername(),serviceName);
                    }
                } );
                dialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        Toast.makeText(getContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogBuilder.show();
            }
        });

        serviceViewModel.addServiceToProfileData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Success)
                {
                    Integer success = ((Result.Success<Integer>)result).getData();
                    Toast.makeText(getContext(), getString(success), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Integer failure = ((Result.Failure<Integer>)result).getData();
                    Toast.makeText(getContext(), getString(failure), Toast.LENGTH_SHORT).show();
                }
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.employee_layout_addservice, new EmployeeMainFragment());
                transaction.commit();
            }
        });
        serviceViewModel.listAvailableServices();
    }




}
