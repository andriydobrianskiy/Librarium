package com.softserve.academy.Entity;

import java.sql.Date;

/**
 * Class representing Book table in the Librarium database
 *
 * @author Volodymyr Oseredchuk
 */
public class Book {
    private int id;
    private Date createdAt;
    private User creatorId;
    private String name;
    private String description;
    private int pageQuantity;

    public Book() {
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageQuantity() {
        return pageQuantity;
    }

    public void setPageQuantity(int pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Book book = (Book) obj;

        if (id != book.id) {
            return false;
        }
        if (pageQuantity != book.pageQuantity) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(book.createdAt) : book.createdAt != null) {
            return false;
        }
        if (creatorId != null ? !creatorId.equals(book.creatorId) : book.creatorId != null) {
            return false;
        }
        if (name != null ? !name.equals(book.name) : book.name != null) {
            return false;
        }
        return description != null ? description.equals(book.description) : book.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + pageQuantity;
        return result;
    }
}
