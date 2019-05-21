package com.softserve.academy.Entity;

import java.sql.Date;

/**
 * Class representing Orders table in the Librarium database
 *
 * @author Volodymyr Oseredchuk
 */
public class Orders {
    private int id;
    private Date createdAt;
    private User creatorId;
    private User reader;
    private Book book;
    private Copy copy;
    private Date takeDate;
    private Date returnDate;
    private Date deadlineDate;

    public Orders() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(User creatorId) {
        this.creatorId = creatorId;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public Date getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(Date takeDate) {
        this.takeDate = takeDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Orders orders = (Orders) o;

        if (id != orders.id) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(orders.createdAt) : orders.createdAt != null) {
            return false;
        }
        if (creatorId != null ? !creatorId.equals(orders.creatorId) : orders.creatorId != null) {
            return false;
        }
        if (reader != null ? !reader.equals(orders.reader) : orders.reader != null) {
            return false;
        }
        if (book != null ? !book.equals(orders.book) : orders.book != null) {
            return false;
        }
        if (copy != null ? !copy.equals(orders.copy) : orders.copy != null) {
            return false;
        }
        if (takeDate != null ? !takeDate.equals(orders.takeDate) : orders.takeDate != null) {
            return false;
        }
        if (returnDate != null ? !returnDate.equals(orders.returnDate) : orders.returnDate != null) {
            return false;
        }
        return deadlineDate != null ? deadlineDate.equals(orders.deadlineDate) : orders.deadlineDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (reader != null ? reader.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (copy != null ? copy.hashCode() : 0);
        result = 31 * result + (takeDate != null ? takeDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (deadlineDate != null ? deadlineDate.hashCode() : 0);
        return result;
    }
}
