package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();
    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeByAuthor(String queryAuthor);

    boolean removeByTitle (String queryTitle);
    boolean removeBySize (Integer querySize);
}
