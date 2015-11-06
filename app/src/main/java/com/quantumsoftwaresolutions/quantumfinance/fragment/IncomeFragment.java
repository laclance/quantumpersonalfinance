package com.quantumsoftwaresolutions.quantumfinance.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.quantumsoftwaresolutions.quantumfinance.R;
import com.quantumsoftwaresolutions.quantumfinance.activity.EditItemActivity;
import com.quantumsoftwaresolutions.quantumfinance.adapter.IncomeAdapter;
import com.quantumsoftwaresolutions.quantumfinance.adapter.RecyclerItemClickListener;
import com.quantumsoftwaresolutions.quantumfinance.helper.L;
import com.quantumsoftwaresolutions.quantumfinance.model.Income;
import com.quantumsoftwaresolutions.quantumfinance.model.Item;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends Fragment {
    private static final String ARG_PAGE = "PAGE";
    private int PAGE;

    private IncomeAdapter adapter;
    private TextView instructionsView;

    public static IncomeFragment newInstance(int page) {
        IncomeFragment fragment = new IncomeFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        fragment.setArguments(args);
        return fragment;
    }

    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            PAGE = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_income, container, false);

        instructionsView = (TextView) rootView.findViewById(R.id.instructions);

        RecyclerView recyclerIncome = (RecyclerView) rootView.findViewById(R.id.recyclerIncome);
        setUpRecyclerView(recyclerIncome);

        return rootView;
    }

    private void setUpRecyclerView(final RecyclerView recyclerIncome){
        List<Item> items = new ArrayList<>();

        items.add(new Income("InItem1", "testing", "05/05/05", "once", 0.0));
        items.add(new Income("InItem2", "testing2", "05/05/05", "once", 0.0));
        adapter = new IncomeAdapter(items);

        if (items.isEmpty())
            instructionsView.setVisibility(View.VISIBLE);

        recyclerIncome.setAdapter(adapter);
        recyclerIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerIncome.setHasFixedSize(true);

        recyclerIncome.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerIncome, new RecyclerItemClickListener.OnItemClickListener() {
                    public void onSingleClick(int position) {
                    }

                    @Override
                    public void onLongClick(int position) {
                        Intent intent = new Intent(getActivity(), EditItemActivity.class);
                        Item item = adapter.getItem(position);
                        intent.putExtra("ID", position + 1);
                        intent.putExtra("DESCRIPTION", item.getBasicInfo().getDescription());
                        intent.putExtra("DATE", item.getDate());
                        intent.putExtra("AMOUNT", item.getAmount());

                        int typecount = 0;
                        switch (item.getBasicInfo().getType()){
                            case "Salary":
                                typecount = 1;
                                break;
                            case "Invesment":
                                typecount = 2;
                                break;
                            case "Other":
                                typecount = 3;
                                break;
                        }
                        intent.putExtra("TYPE", typecount);

                        int freq = 0;
                        switch (item.getFrequency()){
                            case "Daily":
                                freq = 1;
                                break;
                            case "Weekly":
                                freq = 2;
                                break;
                            case "Monthly":
                                freq = 3;
                                break;
                        }
                        intent.putExtra("FREQUENCY", freq);
                        startActivityForResult(intent, 2);
                    }

                    @Override
                    public void onFling(int position) {
                        deleteItem(position + 1);
                    }
                })
        );
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == -1)
            editItem(data.getIntExtra("ID", 0), data.getStringExtra("TYPE"), data.getStringExtra("DESCRIPTION"), data.getStringExtra("DATE"), data.getStringExtra("FREQUENCY"), data.getDoubleExtra("AMOUNT", 0.0));
    }

    public void addItem(String type, String description, String date, String freq, double amount){
        Item item = new Income(type, description, date, freq, amount);
       // dao.createSurfArea(item);
        adapter.addItem(item);

        if (adapter.getItemCount() == 1)
            instructionsView.setVisibility(View.INVISIBLE);
    }

    public void editItem(int id, String type, String description, String date, String freq, double amount){
        adapter.editItem(type, description, date, freq, amount, id);
       // dao.updateSurfArea(adapter.getItem(id - 1));
    }

    public void deleteItem(int id){
       // dao.deleteSurfArea(adapter.getItem(id - 1));
        adapter.deleteItem(id);

        if (adapter.getItemCount() == 0)
            instructionsView.setVisibility(View.VISIBLE);
    }
}
