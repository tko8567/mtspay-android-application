package com.greentea.mtspayandroidapplication.net.graphql;

import com.apollographql.apollo.ApolloClient;
import com.greentea.mtspayandroidapplication.net.NetConstants;
import com.greentea.mtspayandroidapplication.util.apollo.SnowflakeTypeAdapter;

import type.CustomType;

public class SingleApolloClient {

    private final static ApolloClient apolloClient;
    private static String endpoint = NetConstants.SERVER_IP + '/' + NetConstants.GRAPHQL_SECTION;

    static {
        apolloClient = ApolloClient.builder()
                .serverUrl(endpoint)
                .addCustomTypeAdapter(CustomType.SNOWFLAKE, new SnowflakeTypeAdapter())
                .build();
    }

    public static ApolloClient getApolloClient() {
        return apolloClient;
    }


}
