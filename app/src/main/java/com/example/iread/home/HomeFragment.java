package com.example.iread.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.books.Book;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View rootView;
    RecommendAdapter adapter;
    //Temp List
    private ArrayList<Book> recommendedList = new ArrayList<>();

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(this.recommendedList);
    }

    private void setUpRecyclerView(ArrayList<Book> recommendedList) {
        RecyclerView recyclerView = getView().findViewById(R.id.recommend);
        int numberOfRow = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),
                numberOfRow, LinearLayoutManager.HORIZONTAL, false));
        RecommendAdapter.ItemClickListener listener = (((view, position) -> {
            Bundle bundle = new Bundle();
/*            bundle.putInt("id", recommendedList.get(position).getId());
            Navigation.findNavController(view).navigate(R.id.bookDetailsFragment, bundle);*/
        }));

        adapter = new RecommendAdapter(this.getContext(), recommendedList, listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}