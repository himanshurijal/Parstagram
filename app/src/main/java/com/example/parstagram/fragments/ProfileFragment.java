package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parstagram.R;
import com.example.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends PostsFragment {
   @Override
   public void queryPosts() {
       // Specify which class to query
       ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
       query.include(Post.KEY_USER);
       query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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