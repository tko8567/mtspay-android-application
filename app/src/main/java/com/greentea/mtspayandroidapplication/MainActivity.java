package com.greentea.mtspayandroidapplication;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("APL", "Starting querying");
        ApolloClient client =
                ApolloClient
                        .builder()
                        .serverUrl("http://92.63.100.195/graphql")
                        .build();
        client.query(
                FindPersonByIdQuery.builder()
                        .id("108768600524849152")
                        .build()
        ).enqueue(new ApolloCall.Callback<FindPersonByIdQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<FindPersonByIdQuery.Data> response) {
                Log.e("APL", response.data().toString());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("APL", e.toString());
            }
        });

    }
}
