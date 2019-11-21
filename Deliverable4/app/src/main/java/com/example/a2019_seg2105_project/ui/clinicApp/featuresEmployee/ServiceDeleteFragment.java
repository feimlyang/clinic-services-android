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
import androidx.annotation.VisibleForTesting;
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



public class ServiceDeleteFragment extends Fragment {
    private ListView listOfServices;
    private List<Map<String, String>> servicesAttributes;
    private List<String> services;
    private Button returnButton;
    private ClinicViewModel serviceViewModel;
    private ArrayAdapter<String> adapter;
    GlobalObjectManager helper = GlobalObjectManager.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        serviceViewModel = ViewModelProviders.of(this, new ClinicViewModelFactory()).get(ClinicViewModel.class);
        View root = inflater.inflate(R.layout.employee_fragment_deleteservice, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //servicesAttributes = new ArrayList<Map<String, String>>();  //not used
        listOfServices = (ListView) getActivity().findViewById(R.id.listViewServices);
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        services = new ArrayList<>();
        serviceViewModel.getServicesOfferedListData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result == null) return;
                services.clear();
                //servicesAttributes.clear();
                if(result instanceof Result.Failure || result instanceof Result.Error)
                {
                    Toast.makeText(getContext(), "Failed to list all services.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                services = (ArrayList<String>) ((Result.Success)result).getData();

                }
                adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, services);
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
                dialogBuilder.setMessage("Would you like to delete it from your clinic?");
                dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        serviceViewModel.deleteServiceFromProfile(helper.getCurrentUsername(),serviceName);

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

        serviceViewModel.deleteServiceFromProfileData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Success)
                {
                    Integer success = ((Result.Success<Integer>)result).getData();
                    Toast.makeText(getContext(), getString(success), Toast.LENGTH_SHORT).show();
                    serviceViewModel.getServicesOfferedList(helper.getCurrentUsername());

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
                transaction.replace(R.id.employee_layout_deleteservice, new EmployeeMainFragment());
                transaction.commit();
            }
        });
        serviceViewModel.getServicesOfferedList(helper.getCurrentUsername());

    }




}
