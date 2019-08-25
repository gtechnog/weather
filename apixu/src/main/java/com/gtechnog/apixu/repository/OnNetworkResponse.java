package com.gtechnog.apixu.repository;

public interface OnNetworkResponse<T> {
    void onSuccess(T type);
    void onFailure();
}
