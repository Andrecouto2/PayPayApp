package br.com.andrecouto.paypay.api;

public interface CallBack<T> {
    void onSuccess(T response);
    void onError(String header, String message);
}
