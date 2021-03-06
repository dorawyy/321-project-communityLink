package com.example.community_link;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;


public class MainActivity extends CommunityLinkActivity {
    private static final String TAG = "MainActivity";
    public static Boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFCMToken();

    }

    @Override
    protected void onStart() {
        super.onStart();
        running = true;
        if(CommunityLinkApp.userLoggedIn()) {
            setUserView();
        } else {
            setIntro();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        running = false;
    }

    public void setIntro() {
        TextView welcome = findViewById(R.id.welcome);
        TextView loginText = findViewById(R.id.loginText);
        TextView signupText = findViewById(R.id.signupText);
        Button loginButt = findViewById(R.id.loginButtIntro);
        Button signupButt = findViewById(R.id.signupButtIntro);
        LinearLayout userButts = findViewById(R.id.userButts);


        welcome.setText("Welcome to Community Link!");

        loginText.setVisibility(View.VISIBLE);
        loginButt.setVisibility(View.VISIBLE);
        signupText.setVisibility(View.VISIBLE);
        signupButt.setVisibility(View.VISIBLE);
        userButts.setVisibility(View.GONE);
    }

    public void setUserView() {
        TextView welcome = findViewById(R.id.welcome);
        TextView loginText = findViewById(R.id.loginText);
        TextView signupText = findViewById(R.id.signupText);
        Button loginButt = findViewById(R.id.loginButtIntro);
        Button signupButt = findViewById(R.id.signupButtIntro);
        LinearLayout userButts = findViewById(R.id.userButts);

        String username = CommunityLinkApp.user.getUsername();
        welcome.setText("Hello " + username + "!");

        loginText.setVisibility(View.GONE);
        loginButt.setVisibility(View.GONE);
        signupText.setVisibility(View.GONE);
        signupButt.setVisibility(View.GONE);
        userButts.setVisibility(View.VISIBLE);
    }

    private void getFCMToken() {
        System.out.println("Getting instance id");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult() + " ------------------------------------------";

                        // Log and toast
                        Log.d(TAG, token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteUser(View view) {
        Response.Listener responseCallback = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
            }
        };

        Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HTTP response didn't work");
                System.out.println(error.toString());
            }
        };

        CommunityLinkApp.requestManager.deleteUser(responseCallback, errorCallback);
    }

    public void browseService(View view){
        Intent browseService = new Intent(this,BrowseActivity.class);
        startActivity(browseService);
    }

    public void enterChat(View view){
        if(CommunityLinkApp.userLoggedIn()){
            Intent enterChat = new Intent(this, ChatActivity.class);
            startActivity(enterChat);
        }else{
            Toast.makeText(this, "Please log-in or sign-up before using chat", Toast.LENGTH_SHORT).show();
        }
    }

    public void upcomingServices(View view){
        Intent upcomingServices = new Intent(this, UpcomingActivity.class);
        startActivity(upcomingServices);
    }

}