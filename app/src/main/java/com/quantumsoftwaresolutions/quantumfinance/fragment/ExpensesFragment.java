package com.quantumsoftwaresolutions.quantumfinance.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quantumsoftwaresolutions.quantumfinance.R;
import com.quantumsoftwaresolutions.quantumfinance.activity.EditItemActivity;
import com.quantumsoftwaresolutions.quantumfinance.adapter.ExpenseAdapter;
import com.quantumsoftwaresolutions.quantumfinance.adapter.RecyclerItemClickListener;
import com.quantumsoftwaresolutions.quantumfinance.helper.L;
import com.quantumsoftwaresolutions.quantumfinance.model.Expense;
import com.quantumsoftwaresolutions.quantumfinance.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ExpensesFragment extends Fragment {
    private static final String ARG_PAGE = "PAGE";
    private int PAGE;

    private ExpenseAdapter adapter;
    private TextView instructionsView;

    public static ExpensesFragment newInstance(int page) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        fragment.setArguments(args);
        return fragment;
    }

    public ExpensesFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);

        instructionsView = (TextView) rootView.findViewById(R.id.instructions);

        RecyclerView recyclerExpenses = (RecyclerView) rootView.findViewById(R.id.recyclerExpenses);
        setUpRecyclerView(recyclerExpenses);

        return rootView;
    }

    private void setUpRecyclerView(final RecyclerView recyclerExpenses){
        //List<ItemsAdapter> items = dao.getSurfSpotList();
        List<Item> items = new ArrayList<>();
        //items.add(new Expense("Epense1", "testing", 0.0, "once"));
        //items.add(new Expense("Expense2", "testing2", 0.0, "once"));
        adapter = new ExpenseAdapter(items);

        if (items.isEmpty())
            instructionsView.setVisibility(View.VISIBLE);

        recyclerExpenses.setAdapter(adapter);
        recyclerExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerExpenses.setHasFixedSize(true);

        recyclerExpenses.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerExpenses, new RecyclerItemClickListener.OnItemClickListener() {
                     public void onSingleClick(int position) {
                    }

                    @Override
                    public void onLongClick(int position) {
                        Intent intent = new Intent(getActivity(), EditItemActivity.class);
                        Item item = adapter.getItem(position);
                        intent.putExtra("ID", position + 1);
                        intent.putExtra("TYPE", item.getBasicInfo().getType());
                        intent.putExtra("DESCRIPTION", item.getBasicInfo().getDescription());
                        intent.putExtra("DATE", item.getDate());
                        intent.putExtra("AMOUNT", item.getAmount());

                        int typecount = 0;
                        String typeString = item.getBasicInfo().getType();
                        String [] typeArray = getResources().getStringArray(R.array.expense_type_array);

                        for (int i = 0; i < typeArray.length; i++){
                            if (typeArray[i].equals(typeString))
                                typecount = i + 1;
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
        Item item = new Expense(type, description, date, freq, amount);
        // dao.createSurfArea(item);
        adapter.addItem(item);

        if (adapter.getItemCount() == 1)
            instructionsView.setVisibility(View.INVISIBLE);
    }

    public void editItem(int id, String type, String description, String date, String freq, double amount){
        adapter.editItem(type, date, description, freq, amount, id);
        // dao.updateSurfArea(adapter.getItem(id - 1));
    }

    public void deleteItem(int id){
        // dao.deleteSurfArea(adapter.getItem(id - 1));
        adapter.deleteItem(id);

        if (adapter.getItemCount() == 0)
            instructionsView.setVisibility(View.VISIBLE);
    }
}
