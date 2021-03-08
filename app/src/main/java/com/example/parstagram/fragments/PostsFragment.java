package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.parstagram.R;
import com.example.parstagram.adapters.PostsAdapter;
import com.example.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {
    private static final String TAG = "PostsFragment";

    // MODEL
    List<Post> posts;

    // VIEW
    SwipeRefreshLayout srlPosts;
    RecyclerView rvPosts;

    // ADAPTER
    PostsAdapter postsAdapter;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_posts, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // Model
        posts = new ArrayList<>();

        // View
        srlPosts = view.findViewById(R.id.srlPosts);
        rvPosts = view.findViewById(R.id.rvPosts);

        // Adapter
        postsAdapter = new PostsAdapter(posts, getContext());
        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        // When the user slides downwards to refresh the app,
        // clear current posts and make a new request
        // to Parse to get a list of new posts.
        srlPosts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });

        // Configure the refreshing colors
        srlPosts.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
    }

    public void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);

        // Use this method if you need to get multiple
        // objects in your query to the Parse server
        // at once.
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null) {
                    postsAdapter.clear();
                    postsAdapter.addAll(objects);

                    // Signal that refreshing has finished
                    srlPosts.setRefreshing(false);
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }
}