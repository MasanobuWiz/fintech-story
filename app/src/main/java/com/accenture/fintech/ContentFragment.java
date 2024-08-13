package com.accenture.fintech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.accenture.fintech.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {

    private static final String TAG = "ContentFragment";
    private RecyclerView recyclerView;
    private ArrayList<Comic> comicsList;
    private ComicAdapter comicAdapter;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_layout, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        viewPager = view.findViewById(R.id.viewPager);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        comicsList = new ArrayList<>();
        comicAdapter = new ComicAdapter(comicsList);
        recyclerView.setAdapter(comicAdapter);

        viewPagerAdapter = new ViewPagerAdapter(getContext(), comicsList);
        viewPager.setAdapter(viewPagerAdapter);

        // 初回ロード
        loadData();

        // 引っ張って更新の際にデータを再ロード
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh called from SwipeRefreshLayout");
            loadData();
        });

        return view;
    }

    private void loadData() {
        Log.d(TAG, "loadData: Loading data from Firebase Realtime Database");
        swipeRefreshLayout.setRefreshing(true); // インジケータを表示

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference comicsRef = database.getReference("comics");

        comicsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comicsList.clear();
                for (DataSnapshot comicSnapshot : dataSnapshot.getChildren()) {
                    String title = comicSnapshot.child("title").getValue(String.class);
                    String url = comicSnapshot.child("url").getValue(String.class);

                    if (title != null && url != null) {
                        Comic comic = new Comic(title, url);
                        comicsList.add(comic);
                    } else {
                        Log.e(TAG, "Error: Title or URL is null.");
                    }
                }
                comicAdapter.notifyDataSetChanged();
                viewPagerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error loading data from Realtime Database", databaseError.toException());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private static class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

        private final List<Comic> comicsList;

        public ComicAdapter(List<Comic> comicsList) {
            this.comicsList = comicsList;
        }

        @NonNull
        @Override
        public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);
            return new ComicViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
            Comic comic = comicsList.get(position);

            Glide.with(holder.coverImageView.getContext()).load(comic.getCoverImageUrl()).into(holder.coverImageView);
            holder.titleTextView.setText(comic.getTitle());
        }

        @Override
        public int getItemCount() {
            return comicsList.size();
        }

        public static class ComicViewHolder extends RecyclerView.ViewHolder {

            ImageView coverImageView;
            TextView titleTextView;

            public ComicViewHolder(@NonNull View itemView) {
                super(itemView);
                coverImageView = itemView.findViewById(R.id.coverImageView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
            }
        }
    }

    private static class ViewPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
        private final Context context;
        private final List<Comic> comicsList;

        public ViewPagerAdapter(Context context, List<Comic> comicsList) {
            this.context = context;
            this.comicsList = comicsList;
        }

        @Override
        public int getCount() {
            return comicsList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_pager, container, false);
            ImageView imageView = view.findViewById(R.id.imageView);
            Glide.with(context).load(comicsList.get(position).getCoverImageUrl()).into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return comicsList.get(position).getTitle();
        }
    }
}