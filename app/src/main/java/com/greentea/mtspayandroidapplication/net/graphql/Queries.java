package com.greentea.mtspayandroidapplication.net.graphql;

import android.telecom.Call;
import android.util.Log;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloCanceledException;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.exception.ApolloHttpException;
import com.apollographql.apollo.exception.ApolloNetworkException;
import com.greentea.mtspayandroidapplication.FindPersonByIdQuery;
import com.greentea.mtspayandroidapplication.net.NetConstants;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

import static com.greentea.mtspayandroidapplication.net.graphql.Queries.Constants.EXCEPTION;
import static com.greentea.mtspayandroidapplication.net.graphql.Queries.Constants.PERSON;
import static com.greentea.mtspayandroidapplication.FindPersonByIdQuery.Person;
import static com.greentea.mtspayandroidapplication.FindPersonByIdQuery.Data;

public class Queries {

    private static volatile boolean lastQueryPerformed = false;
    private static volatile Map<String, Object> map = new HashMap<>();
    private static ApolloClient apolloClient;
    private static String endpoint = NetConstants.SERVER_IP + '/' + NetConstants.GRAPHQL_SECTION;

    static {

        apolloClient = ApolloClient
                .builder()
                /*.okHttpClient(new OkHttpClient.Builder().addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                return null;
                            }
                        }
                ).build())*/
                .serverUrl(endpoint)
                .build();
    }

    public static void loginQuery(String id, ApolloCall.Callback<Data> callback) {
        lastQueryPerformed = false;

        //map = new HashMap<>();
        /*
        ApolloCall.Callback<FindPersonByIdQuery.Data> callback = new ApolloCall.Callback<FindPersonByIdQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<FindPersonByIdQuery.Data> response) {
                map.put(PERSON, response.data().person());
                listener.onFinish(
                        (response.data().person() != null),
                        map
                );
                lastQueryPerformed = true;
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                map.put(EXCEPTION, e);
                listener.onFinish(
                        false,
                        map
                );
                lastQueryPerformed = true;
            }

            @Override
            public void onHttpError(@Nonnull ApolloHttpException e) {
                super.onHttpError(e);
                Log.e("ApolloClient", e.toString());
                e.printStackTrace();
                lastQueryPerformed = true;
            }

            @Override
            public void onNetworkError(@Nonnull ApolloNetworkException e) {
                super.onNetworkError(e);
                Log.e("ApolloClient", e.toString());
                e.printStackTrace();
                lastQueryPerformed = true;
            }

            @Override
            public void onCanceledError(@Nonnull ApolloCanceledException e) {
                super.onCanceledError(e);
                Log.e("ApolloClient", e.toString());
                e.printStackTrace();
                lastQueryPerformed = true;
            }
        };*/

        apolloClient.query(
                FindPersonByIdQuery
                        .builder()
                        .id(id)
                        .build()
        ).enqueue(callback);
    }

    public static boolean isLastQueryPerformed() {
        return lastQueryPerformed;
    }

    public interface OnQueryPerformedListener {
        void onFinish(boolean succeeded, Map<String, Object> data);
    }

    public static class Constants {
        public static final String PERSON = "person";
        public static final String EXCEPTION = "exception";
    }
}


