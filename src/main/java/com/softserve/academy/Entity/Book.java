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
    private int creatorId;
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
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

        if ((obj == null) || (this.getClass() != obj.getClass())) {
            return false;
        }

        Book book = (Book) obj;
        return (id == book.getId()) &&
            (createdAt.equals(book.getCreatedAt())) &&
            (creatorId == book.getCreatorId()) &&
            (name.equals(book.getName())) &&
            (description.equals(book.getDescription())) &&
            (pageQuantity == book.getPageQuantity());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + creatorId;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + pageQuantity;
        return result;
    }
}
