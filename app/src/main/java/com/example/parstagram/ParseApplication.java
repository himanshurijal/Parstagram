package com.example.parstagram;

import android.app.Application;

import com.example.parstagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models. Must be done
        // at creation of ParseApplication (here).
        // You will not be able to use your models if
        // you do not register your subclasses.
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3VlQw0lVwYT3a5mIoy98xyg2uSD8eJL9l1NInE76")
                .clientKey("Xh6QF3RI9VShzy8yaOrQuUF0xbhV4gnv9bbqoWmB")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
