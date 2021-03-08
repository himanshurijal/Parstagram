package com.example.parstagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.R;
import com.example.parstagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    List<Post> posts;
    Context context;

    public PostsAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView ivImage;
        TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind (Post post) {
            tvUsername.setText(post.getUser().getUsername());
            ParseFile imageFile = post.getImage();
            if(imageFile != null) {
                Glide.with(context).load(imageFile.getUrl()).into(ivImage);
            }
            tvDescription.setText(post.getDescription());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> posts) {
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }
}
