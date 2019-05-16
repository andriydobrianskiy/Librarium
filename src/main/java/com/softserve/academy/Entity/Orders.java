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
    private int creatorId;
    private int readerId;
    private int bookId;
    private int copyId;
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (this.getClass() != obj.getClass())) {
            return false;
        }

        Orders orders = (Orders) obj;
        return (id == orders.getId()) &&
            (createdAt.equals(orders.getCreatedAt())) &&
            (creatorId == orders.getCreatorId()) &&
            (readerId == orders.getReaderId()) &&
            (bookId == orders.getBookId()) &&
            (copyId == orders.getCopyId()) &&
            (takeDate.equals(orders.getTakeDate())) &&
            (returnDate.equals(orders.getReturnDate())) &&
            (deadlineDate.equals(orders.getDeadlineDate()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + creatorId;
        result = 31 * result + readerId;
        result = 31 * result + bookId;
        result = 31 * result + copyId;
        result = 31 * result + takeDate.hashCode();
        result = 31 * result + returnDate.hashCode();
        result = 31 * result + deadlineDate.hashCode();
        return result;
    }
}
