package com.example.demo.services;
import java.util.List;

public interface IService<T> {
    T create(T t);
    T delete(T t);
    T findById(Integer id);
    List<T> findAll();

}
