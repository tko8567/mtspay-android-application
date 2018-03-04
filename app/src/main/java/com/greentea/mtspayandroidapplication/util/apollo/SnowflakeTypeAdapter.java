package com.greentea.mtspayandroidapplication.util.apollo;

import com.apollographql.apollo.CustomTypeAdapter;

import javax.annotation.Nonnull;

/**
 * Created by daniily on 2/22/18.
 */

public class SnowflakeTypeAdapter implements CustomTypeAdapter<Long> {


    @Nonnull
    @Override
    public Long decode(@Nonnull String value) {
        return Long.decode(value);
    }

    @Nonnull
    @Override
    public String encode(@Nonnull Long value) {
        return value.toString();
    }
}
