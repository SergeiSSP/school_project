package com.foxminded.senkiv.school_project.database;

import java.util.List;
import java.util.Optional;

interface DAO<T> {

    Optional<T> get(int id);
    List<T> getAll();
    void create(T t);
    void update(T t);
    void delete(int id);
	void batchCreate(List<T> list);
}
