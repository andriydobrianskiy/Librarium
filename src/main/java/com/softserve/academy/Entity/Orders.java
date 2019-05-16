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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Orders orders = (Orders) obj;

        if (id != orders.id) {
            return false;
        }
        if (creatorId != orders.creatorId) {
            return false;
        }
        if (readerId != orders.readerId) {
            return false;
        }
        if (bookId != orders.bookId) {
            return false;
        }
        if (copyId != orders.copyId) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(orders.createdAt) : orders.createdAt != null) {
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
        result = 31 * result + creatorId;
        result = 31 * result + readerId;
        result = 31 * result + bookId;
        result = 31 * result + copyId;
        result = 31 * result + (takeDate != null ? takeDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (deadlineDate != null ? deadlineDate.hashCode() : 0);
        return result;
    }
}
