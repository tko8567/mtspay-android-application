package com.greentea.mtspayandroidapplication.util.apollo;

import com.apollographql.apollo.CustomTypeAdapter;

import javax.annotation.Nonnull;

/**
 * Created by daniily on 2/22/18.
 */

public class DateTimeTypeAdapter implements CustomTypeAdapter<Double> {
    @Nonnull
    @Override
    public Double decode(@Nonnull String value) {
        return null;
    }

    @Nonnull
    @Override
    public String encode(@Nonnull Double value) {
        return null;
    }
}
