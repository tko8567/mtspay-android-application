package com.greentea.mtspayandroidapplication.net.graphql;

import android.util.Log;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloCanceledException;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.exception.ApolloHttpException;
import com.apollographql.apollo.exception.ApolloNetworkException;
import com.greentea.mtspayandroidapplication.FindPersonByIdQuery;
import com.greentea.mtspayandroidapplication.Launcher;
import com.greentea.mtspayandroidapplication.net.NetConstants;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Queries {

    private static volatile Map<String, Object> map = new HashMap<>();
    private static ApolloClient apolloClient;
    private static String endpoint =
            NetConstants.SERVER_IP + '/' + NetConstants.GRAPHQL_SECTION;

    static {
        apolloClient = ApolloClient
                .builder()
                .serverUrl(endpoint)
                .build();
    }

    public static void loginQuery(String id, final Object lock, final OnQueryPerformedListener listener) {

        ApolloCall.Callback<FindPersonByIdQuery.Data> callback = new ApolloCall.Callback<FindPersonByIdQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<FindPersonByIdQuery.Data> response) {
                if (response.data().person() != null) {
                    String firstName = response.data().person().firstName();
                    String lastName = response.data().person().lastName();
                    map.put(Launcher.PrefConstants.FIRST_NAME, firstName);
                    map.put(Launcher.PrefConstants.LAST_NAME, lastName);
                    listener.onFinish(
                            true,
                            map
                    );
                } else {
                    map.put(Launcher.PrefConstants.FIRST_NAME, "null");
                    map.put(Launcher.PrefConstants.LAST_NAME, "null");
                    listener.onFinish(
                            false,
                            map
                    );
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                map.put("exception", e);
                listener.onFinish(
                        false,
                        map
                );
            }

            @Override
            public void onHttpError(@Nonnull ApolloHttpException e) {
                super.onHttpError(e);
                Log.e("ApolloClient", e.toString());
                e.printStackTrace();
            }

            @Override
            public void onNetworkError(@Nonnull ApolloNetworkException e) {
                super.onNetworkError(e);
                Log.e("ApolloClient", e.toString());
                e.printStackTrace();
            }

            @Override
            public void onCanceledError(@Nonnull ApolloCanceledException e) {
                super.onCanceledError(e);
                Log.e("ApolloClient", e.toString());
                e.printStackTrace();
            }
        };



        apolloClient.query(
                FindPersonByIdQuery
                        .builder()
                        .id(id)
                        .build()
        ).enqueue(callback);
    }

    public interface OnQueryPerformedListener {
        void onFinish(boolean succeeded, Map<String, Object> data);
    }

}


