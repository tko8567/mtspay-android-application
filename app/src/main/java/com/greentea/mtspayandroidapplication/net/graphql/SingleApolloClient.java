package com.greentea.mtspayandroidapplication.net.graphql;

import com.apollographql.apollo.ApolloClient;
import com.greentea.mtspayandroidapplication.net.NetConstants;

public class SingleApolloClient {

    private final static ApolloClient apolloClient;
    private static String endpoint = NetConstants.SERVER_IP + '/' + NetConstants.GRAPHQL_SECTION;

    static {
        apolloClient = ApolloClient.builder()
                .serverUrl(endpoint)
                .build();
    }

    public static ApolloClient getApolloClient() {
        return apolloClient;
    }


}
