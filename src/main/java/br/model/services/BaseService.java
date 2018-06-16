package br.model.services;

import java.util.List;

public interface BaseService<T> {

    void save(T t);

    void delete(T t);

    List<T> findAll();
}
