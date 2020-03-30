package com.example.iread.books;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

public class BooksFragment extends Fragment {
    private View rootView;
    BookListAdapter adapter;
    Menu menu;
    @Inject
    BooksViewModel booksViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MyApplication) MyApplication.applicationContext).appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_books, container, false);
        setHasOptionsMenu(true);
        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        booksViewModel.getBooksList();

        booksViewModel.booksList.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {

            @Override
            public void onChanged(List<Book> books) {
                setUpRecycleView(books);
            }
        });

        booksViewModel.error.observe(getViewLifecycleOwner(), new Observer<String> () {
            @Override
            public void onChanged(String s) {
                Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
            }
        });

        booksViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Data to populate the RecyclerView with
        super.onViewCreated(view, savedInstanceState);
        //SearchView
        setUpSearchView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("Nice", "Hello");
        inflater.inflate(R.menu.books_menu, menu);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ((item == menu.findItem(R.id.sorting_menu)) || (item == menu.findItem(R.id.genre_menu))
            || (item == menu.findItem(R.id.rating_menu))) {
            return super.onOptionsItemSelected(item);
        }
        item.setChecked(true);
        SortingMenuOptions sortingMenuOptions = null;
        GenreMenuOptions genreMenuOptions = null;
        RatingMenuOptions ratingMenuOptions = null;

        if (menu.findItem(R.id.name_ascending).isChecked()) {
            sortingMenuOptions = SortingMenuOptions.NAME_ASCENDING;
        } else if (menu.findItem(R.id.name_descending).isChecked()) {
            sortingMenuOptions = SortingMenuOptions.NAME_DESCENDING;
        } else if (menu.findItem(R.id.time_ascending).isChecked()) {
            sortingMenuOptions = SortingMenuOptions.TIME_ASCENDING;
        } else if (menu.findItem(R.id.time_descending).isChecked()) {
            sortingMenuOptions = SortingMenuOptions.TIME_DESCENDING;
        }

        if (menu.findItem(R.id.fantasy_menu).isChecked()) {
            genreMenuOptions = GenreMenuOptions.FANTASY;
        } else if (menu.findItem(R.id.adventure_menu).isChecked()) {
            genreMenuOptions = GenreMenuOptions.ADVENTURE;
        } else if (menu.findItem(R.id.romance_menu).isChecked()) {
            genreMenuOptions = GenreMenuOptions.ROMANCE;
        } else if (menu.findItem(R.id.mystery_menu).isChecked()) {
            genreMenuOptions = GenreMenuOptions.MYSTERY;
        } else if (menu.findItem(R.id.horror_menu).isChecked()) {
            genreMenuOptions = GenreMenuOptions.HORROR;
        } else if (menu.findItem(R.id.fiction_menu).isChecked()) {
            genreMenuOptions = GenreMenuOptions.FICTION;
        } else if (menu.findItem(R.id.humor_menu).isChecked()) {
            genreMenuOptions = GenreMenuOptions.HUMOR;
        }

        if (menu.findItem(R.id.star_0_1).isChecked()) {
            ratingMenuOptions = RatingMenuOptions.STAR_0_1;
        } else if (menu.findItem(R.id.star_1_2).isChecked()) {
            ratingMenuOptions = RatingMenuOptions.STAR_1_2;
        } else if (menu.findItem(R.id.star_2_3).isChecked()) {
            ratingMenuOptions = RatingMenuOptions.STAR_2_3;
        } else if (menu.findItem(R.id.star_3_4).isChecked()) {
            ratingMenuOptions = RatingMenuOptions.STAR_3_4;
        } else if (menu.findItem(R.id.star_4_5).isChecked()) {
            ratingMenuOptions = RatingMenuOptions.STAR_4_5;
        }

        booksViewModel.manipulateBooksList(sortingMenuOptions, genreMenuOptions, ratingMenuOptions);
        return true;
    }

    private void setUpRecycleView(List<Book> booksList) {
        RecyclerView recyclerView = getView().findViewById(R.id.bookList);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));

        BookListAdapter.ItemClickListener listener = ((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", booksList.get(position).getId());
//            bundle.putString("title", booksList.get(position).getBookTitle());
//            bundle.putString("author", booksList.get(position).getAuthorName());
//            bundle.putString("summary", booksList.get(position).getSummary());
//            Toast.makeText(getContext(), "Book ID " + booksList.get(position).getId(), Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.bookDetailsFragment, bundle);
        });

        if (adapter == null)
            adapter = new BookListAdapter(this.getContext(), booksList, listener);
        else
            adapter.setBooksList(booksList);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setUpSearchView() {
        SearchView searchView = getView().findViewById(R.id.searchBook);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
