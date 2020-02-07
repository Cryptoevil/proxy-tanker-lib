package pro.cryptoevil.proxy.core;

public interface IProxyValidator<T> {

    boolean validate(T proxy);
}
