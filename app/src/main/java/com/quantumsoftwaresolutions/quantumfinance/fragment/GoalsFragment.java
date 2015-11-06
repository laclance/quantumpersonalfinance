package com.quantumsoftwaresolutions.quantumfinance.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quantumsoftwaresolutions.quantumfinance.R;
import com.quantumsoftwaresolutions.quantumfinance.adapter.GoalsAdapter;
import com.quantumsoftwaresolutions.quantumfinance.adapter.IncomeAdapter;

public class GoalsFragment extends Fragment {
    private static final String ARG_PAGE = "PAGE";
    private int PAGE;

    private GoalsAdapter adapter;
    private TextView instructionsView;

    public static GoalsFragment newInstance(int page) {
        GoalsFragment fragment = new GoalsFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        fragment.setArguments(args);
        return fragment;
    }

    public GoalsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goals, container, false);
    }
}
