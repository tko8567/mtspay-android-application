package com.greentea.mtspayandroidapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloCanceledException;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.exception.ApolloHttpException;
import com.apollographql.apollo.exception.ApolloNetworkException;
import com.apollographql.apollo.exception.ApolloParseException;
import com.greentea.mtspayandroidapplication.Launcher;
import com.greentea.mtspayandroidapplication.net.graphql.SingleApolloClient;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import apollo.LoginMutation;
import apollo.LoginMutation2Mutation;
import apollo.PersonQuery;
import fragment.PersonData;


public class Account {


    private static final String SHARED_PREFERENCES_NAME = "current_account";
    private static final String TAG = "Account";
    private SharedPreferences sharedPreferences;
    private String token = "null";
    private volatile PersonData person;
    private volatile PersonData.Wallet defaultWallet;

    private final Object lock = new Object();
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
    /*
    // FIXME: 19.12.2017 Test section
    public static Account getInstance() {
        if (instance == null) {
            instance = new Account();
            instance.loadTestAccount();
        }
        return instance;
    }
    private Account() {
    }
    */
    // FIXME: 19.12.2017 End test section

    private void resetContext(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private Account(Context context) {
        resetContext(context);
        token = sharedPreferences.getString(Launcher.PrefConstants.TOKEN, "null");
    }

    /**
     * Map key for getting ResponseStatus of login mutation
     * @see Account#login(String, String)
     * @see Account.ResponseStatus
     */
    public static final String STATUS = "status";

    /**
     * Map key for getting PersonData (if any) of login mutation
     * @see Account#login(String, String)
     * @see Account.ResponseStatus
     */
    public static final String PERSON = "person";

    /**
     * Map key for getting Exception (if any) of login mutation
     * @see Account#login(String, String)
     * @see Account.ResponseStatus
     */
    public static final String EXCEPTION = "exception";

    /**
     * Enum which represents the server response status for login mutation
     */
    public enum ResponseStatus {
        /**
         * Query/mutation has been performed successfully
         */
        SUCCESS,

        /**
         * Some failure has shut the query. Does not usually appear as it says that not every move in
         * method was predicted, but it would not affect the program terribly.
         */
        FAILURE,

        /**
         * Returned in case of network errors (timeouts, no connection etc.)
         */
        NETWORK_ERROR, HTTP_ERROR, CANCELLED_ERROR, PARSE_ERROR, UNKNOWN_FAILURE, WRONG_PAIR
    }

    /**
     * Login mutation - sends mutation to the server and returns Map with the mutation results. <br>
     * Map&lt;String, Object&gt; contains the following pair "key-value":
     * <pre>
     *     {@link Account#STATUS} - {@link Account.ResponseStatus} of the mutation
     *     {@link Account#PERSON} - {@link fragment.PersonData} if the mutation succeeded or null otherwise
     *     {@link Account#EXCEPTION} - {@link ApolloException} if {@link Account.ResponseStatus} differs
     *     from {@link ResponseStatus#SUCCESS} or null otherwise
     * </pre>
     * @param email string to send to server as account email
     * @param password string to send to server as account password
     * @return {@link Map}&lt;String, Object&gt of response
     */
    public Map<String, Object> login(final String email, final String password){

        final Map<String, Object> result = new HashMap<>();

        ApolloCall.Callback<LoginMutation2Mutation.Data> callback = new ApolloCall.Callback<LoginMutation2Mutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<LoginMutation2Mutation.Data> response) {

                person = null;
                if (response.data().login() == null) {
                    result.put(STATUS, ResponseStatus.WRONG_PAIR);
                } else {
                    person = response.data().login().person().fragments().personData();
                    result.put(STATUS, ResponseStatus.SUCCESS);
                    setToken(person.id().toString());
                }
                result.put(PERSON, person);
                result.put(EXCEPTION, null);

                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

                result.put(STATUS, ResponseStatus.FAILURE);
                result.put(PERSON, null);
                result.put(EXCEPTION, e);
                e.printStackTrace();
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onNetworkError(@Nonnull ApolloNetworkException e) {

                result.put(STATUS, ResponseStatus.NETWORK_ERROR);
                result.put(PERSON, null);
                result.put(EXCEPTION, e);
                e.printStackTrace();
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onCanceledError(@Nonnull ApolloCanceledException e) {

                result.put(STATUS, ResponseStatus.CANCELLED_ERROR);
                result.put(PERSON, null);
                result.put(EXCEPTION, e);
                e.printStackTrace();
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onHttpError(@Nonnull ApolloHttpException e) {

                result.put(STATUS, ResponseStatus.HTTP_ERROR);
                result.put(PERSON, null);
                result.put(EXCEPTION, e);
                e.printStackTrace();
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onParseError(@Nonnull ApolloParseException e) {

                result.put(STATUS, ResponseStatus.PARSE_ERROR);
                result.put(PERSON, null);
                result.put(EXCEPTION, e);
                e.printStackTrace();
                synchronized (lock) {
                    lock.notify();
                }
            }
        };

        SingleApolloClient.getApolloClient().mutate(
                LoginMutation2Mutation
                        .builder()
                        .email(email)
                        .password(password)
                        .build()
        ).enqueue(callback);

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "login, result=" + result.toString());
        return result;
    }

//    /**
//     * Sends a query to get Person from server, including all the information for account. It uses
//     * {@link com.apollographql.apollo.ApolloClient} from class {@link SingleApolloClient}, but constructed to pause the
//     * thread, so current thread will not continue working until query is performed. <br>
//     * Requires updated context.
//     * @return int - status of the result
//     * @see Account#STATUS_OK
//     * @see Account#STATUS_RECOVERED
//     * @see Account#STATUS_NO_ACCOUNT_FOUND
//     */
//    public int queryPerson() {
//
//        status = STATUS_NA;
//
//        if (person != null) return STATUS_RECOVERED;
//
//        ApolloCall.Callback<FindPersonByIdQuery.Data> callback = new ApolloCall.Callback<FindPersonByIdQuery.Data>() {
//            @Override
//            public void onResponse(@Nonnull Response<FindPersonByIdQuery.Data> response) {
//                Log.i("Account", "Apollo callback resulted in onResponse section");
//                person = response.data().person();
//                if (person == null) {
//                    status = STATUS_NO_ACCOUNT_FOUND;
//                    Log.w("Account", "onResponse: STATUS_NO_ACCOUNT_FOUND");
//                } else {
//                    // TODO: 2/16/18 Get rid of this shit.
//                    for (Wallet wallet: person.wallets()) {
//                        if (wallet.id().equals(person.defaultWallet().id())) {
//                            defaultWallet = wallet;
//                            break;
//                        }
//                    }
//                }
//                status = STATUS_OK;
//                Log.i("Account", "onResponse: STATUS_OK");
//
//                synchronized (lock) {
//                    lock.notify();
//                }
//            }
//
//            @Override
//            public void onFailure(@Nonnull ApolloException e) {
//                Log.w("Account", "Apollo callback resulted in onFailure section");
//                Log.d("Account", "e=" + e);
//                exception = e;
//                e.printStackTrace();
//                status = STATUS_APOLLO_EXCEPTION;
//                synchronized (lock) {
//                    lock.notify();
//                }
//            }
//        };
//        /*
//
//        SingleApolloClient.getApolloClient().query(
//                FindPersonByIdQuery
//                        .builder()
//                        .id((Object)token)
//                        .build()
//        ).enqueue(callback);
//        */
//
//
//
//        synchronized (lock) {
//            try {
//                lock.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        if (status == STATUS_OK) instance.saveToken();
//        return status;
//    }

    public void queryPerson(final long id) {

        SingleApolloClient
                .getApolloClient()
                .query(PersonQuery.builder()
                        .id(id)
                        .build()
                ).enqueue(new ApolloCall.Callback<PersonQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<PersonQuery.Data> response) {

            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });

    }

    /**
     * Sets new token to account
     * @param token new String for token to set
     */
    public void setToken(String token) {
        this.token = token;
        saveToken();
    }

    /**
     * Returns boolean value of whether account is logged in. Technically, whether there is token stored in account
     * instance or it is "null"
     * @return boolean value
     */
    public boolean isLoggedIn() {
        return !token.equals("null") && !token.equals(null);
    }

    /**
     * Returns {@link LoginMutation.Person} object currently stored in Account. You can get it by {@link Account#login(String, String)} method
     * @return Person
     */
    public PersonData getPerson() {
        return person;
    }

    // TODO: 18.12.2017 Delete on finishing the testing
    private void setPerson(PersonData person) {
        this.person = person;
    }

    /** Saves current token to preferences. Requires updated context.
     * @return boolean value of whether saving was successful
     */
    public boolean saveToken() {
        return sharedPreferences.edit()
                .putString(Launcher.PrefConstants.TOKEN, token)
                .commit();
    }

    /** Deletes the token from preferences. Use it for logout. Requires updated context.
     * @return boolean value of whether deleting was successful
     */
    public boolean deleteToken() {
        this.token = "null";
        return sharedPreferences.edit()
                .remove(Launcher.PrefConstants.TOKEN)
                .commit();
    }

//    /**
//     * Returns last exception occurred in query
//     * @return Exception
//     */
//    public Exception getException() {
//        return exception;
//    }

    /**
     * Returns currently stored token
     * @return String
     */
    public String getToken() {
        return token;
    }

    public PersonData.Wallet getDefaultWallet() {
        if (defaultWallet == null) {
            for (PersonData.Wallet wallet: person.wallets()) {
                if (wallet.id().equals(person.defaultWallet().id())) {
                    defaultWallet = wallet;
                    break;
                }
            }
        }
        return defaultWallet;
    }

/*
    // Delete on release
    private void loadTestAccount() {
        Account.getInstance().setPerson(new FindPersonByIdQuery.Person(
                "Person",
                "101",
                "Daniil",
                "Yurkov",
                new ArrayList<Card>() {{
                    add(new Card(
                            "Card",
                            "1001",
                            "0025 9204 2725 0759",
                            "08/19",
                            1124.80d,
                            true
                    ));
                    add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));
                    add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));
                    add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));
                    add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));
                    add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));
                    add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));
                    add(new Card(
                            "Card",
                            "1002",
                            "3538 3426 9476 0034",
                            "20/22",
                            76d,
                            false
                    ));


                }},
                new ArrayList<>()
        ));
        person.transactions().add(new Transaction(
                "Transaction",
                "9471824",
                new From(
                        "Person",
                        "102",
                        "Oleg",
                        "Pavlinskiy"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "101",
                                "Daniil",
                                "Yurkov"),
                        null
                ),
                556.30,
                "A test transaction to you, Daniil",
                TransactionStatus.SUCCESS)
        );

        person.transactions().add(new Transaction(
                "Transaction",
                "9471824",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                556.30,
                "Giving it back...",
                TransactionStatus.SUCCESS)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT )
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
        person.transactions().add(new Transaction(
                "Transaction",
                "4000",
                new From(
                        "Person",
                        "101",
                        "Daniil",
                        "Yurkov"

                ),
                new To(
                        "Person",
                        new AsPerson(
                                "Person",
                                "102",
                                "Oleg",
                                "Pavlinsky"),
                        null
                ),
                1367534d,
                "Transactions Filler",
                TransactionStatus.DRAFT)
        );
    }
    */
}
