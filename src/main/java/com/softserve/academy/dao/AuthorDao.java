package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;

import java.util.List;

public interface AuthorDao {
    boolean insertAuthor(Author author);

    List<Author> getAuthorsByBookId(int bookId);
}
