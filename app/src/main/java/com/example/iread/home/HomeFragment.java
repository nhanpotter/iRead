package com.example.iread.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.books.Book;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment {
    private View rootView;
    RecommendAdapter adapter;

    @Inject
    HomeViewModel homeViewModel;

    public HomeFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MyApplication) MyApplication.applicationContext).appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        homeViewModel.getRecommendList();

        homeViewModel.recommendList.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                setUpRecyclerView(books);
            }
        });

        homeViewModel.error.observe(getViewLifecycleOwner(), new Observer<String> () {
            @Override
            public void onChanged(String s) {
                Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
            }
        });

        homeViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpRecyclerView(List<Book> recommendList) {
        RecyclerView recyclerView = getView().findViewById(R.id.recommend);
        int numberOfRow = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),
                numberOfRow, LinearLayoutManager.HORIZONTAL, false));
        RecommendAdapter.ItemClickListener listener = (((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", recommendList.get(position).getId());
            Navigation.findNavController(view).navigate(R.id.bookDetailsFragment, bundle);
        }));
        if (adapter == null)
            adapter = new RecommendAdapter(this.getContext(), recommendList, listener);
        else
            adapter.setRecommendList(recommendList);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}