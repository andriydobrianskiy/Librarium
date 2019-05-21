package com.softserve.academy.Entity;

import java.awt.print.Book;

public class BookAuthor {
    private Book book_id;
    private Author author_id;

    public Book getBook_id() {
        return book_id;
    }

    public void setBook_id(Book book_id) {
        this.book_id = book_id;
    }

    public Author getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Author author_id) {
        this.author_id = author_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAuthor)) return false;

        BookAuthor that = (BookAuthor) o;

        if (book_id != null ? !book_id.equals(that.book_id) : that.book_id != null) return false;
        return author_id != null ? author_id.equals(that.author_id) : that.author_id == null;

    }

    @Override
    public int hashCode() {
        int result = book_id != null ? book_id.hashCode() : 0;
        result = 31 * result + (author_id != null ? author_id.hashCode() : 0);
        return result;
    }
}
