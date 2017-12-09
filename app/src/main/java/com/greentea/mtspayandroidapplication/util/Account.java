package com.greentea.mtspayandroidapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.greentea.mtspayandroidapplication.FindPersonByIdQuery;
import com.greentea.mtspayandroidapplication.Launcher;
import com.greentea.mtspayandroidapplication.net.graphql.SingleApolloClient;

import javax.annotation.Nonnull;

import static com.greentea.mtspayandroidapplication.FindPersonByIdQuery.Person;

public class Account {


    /** Account was found and downloaded */
    public static final int STATUS_OK = 0x00;

    /** Account is already downloaded to the application */
    public static final int STATUS_RECOVERED = 0x01;

    /** No account with given parameters found on the server */
    public static final int STATUS_NO_ACCOUNT_FOUND = 0x10;

    /** No internet connection to send query to the server */
    public static final int STATUS_NO_INTERNET_CONNECTION = 0x11;

    /** ApolloException occurred during query performance */
    public static final int STATUS_APOLLO_EXCEPTION = 0x12;

    /** Method was not finished */
    public static final int STATUS_NA = 0x1f;

    private static final String SHARED_PREFERENCES_NAME = "current_account";
    private SharedPreferences sharedPreferences;
    private String token = "null";
    private volatile Person person;

    private final Object lock = new Object();
    private volatile int status = STATUS_NA;
    private volatile Exception exception;
    private static Account instance;

    /**
     * This getInstance refreshes SharedPreferences according to given context. Methods which use SharedPreferences
     * should be accessed at least once via this method if you use another context. Also the new Account instance
     * *must* be created via {@link Account#getInstance(Context)} method to have an opportunity to get SharedPreferences
     * @param context currently used context
     * @return Account instance
     */
    public static Account getInstance(Context context) {
        if (instance == null) {
            instance = new Account(context);
        }
        instance.resetContext(context);
        return instance;
    }

    /**
     * Account instance which is not needed to update Context for. Use when you do not need the actual context
     * @return Account instance
     */
    public static Account getInstance() {
        if (instance != null) {
            return instance;
        }
        throw new IllegalStateException("Context is not defined for new Account");
    }

    private void resetContext(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private Account(Context context) {
        resetContext(context);
        token = sharedPreferences.getString(Launcher.PrefConstants.TOKEN, "null");
    }

    /**
     * Sends a query to get Person from server, including all the information for account. It uses
     * {@link com.apollographql.apollo.ApolloClient} from class {@link SingleApolloClient}, but constructed to pause the
     * thread, so current thread will not continue working until query is performed. <br>
     * Requires updated context.
     * @return int - status of the result
     * @see Account#STATUS_OK
     * @see Account#STATUS_RECOVERED
     * @see Account#STATUS_NO_ACCOUNT_FOUND
     */
    public int queryPerson() {

        status = STATUS_NA;

        if (person != null) return STATUS_RECOVERED;

        ApolloCall.Callback<FindPersonByIdQuery.Data> callback = new ApolloCall.Callback<FindPersonByIdQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<FindPersonByIdQuery.Data> response) {
                Log.i("Account", "Apollo callback resulted in onResponse section");
                person = response.data().person();
                if (person == null) {
                    status = STATUS_NO_ACCOUNT_FOUND;
                    Log.w("Account", "onResponse: STATUS_NO_ACCOUNT_FOUND");
                } else {
                    status = STATUS_OK;
                    Log.i("Account", "onResponse: STATUS_OK");
                }
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.w("Account", "Apollo callback resulted in onFailure section");
                Log.d("Account", "e=" + e);
                exception = e;
                e.printStackTrace();
                status = STATUS_APOLLO_EXCEPTION;
                synchronized (lock) {
                    lock.notify();
                }
            }
        };

        SingleApolloClient.getApolloClient().query(
            FindPersonByIdQuery
                .builder()
                .id(token)
                .build()
        ).enqueue(callback);

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (status == STATUS_OK) instance.saveToken();
        return status;
    }

    /**
     * Sets new token to account
     * @param token new String for token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returns boolean value of whether account is logged in. Technically, whether there is token stored in account
     * instance or it is "null"
     * @return boolean value
     */
    public boolean isLoggedIn() {
        return !token.equals("null");
    }

    /**
     * Returns {@link Person} object currently stored in Account. You can get it by {@link Account#queryPerson()} method
     * @return Person
     */
    public Person getPerson() {
        return person;
    }

    /** Saves current token to preferences. Requires updated context.
     * @return boolean value of whether saving was successful
     */
    public boolean saveToken() {
        return sharedPreferences.edit()
                .putString(Launcher.PrefConstants.TOKEN, token)
                .commit();
    }

    /**
     * Returns last exception occurred in query
     * @return Exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Returns currently stored token
     * @return String
     */
    public String getToken() {
        return token;
    }
}
