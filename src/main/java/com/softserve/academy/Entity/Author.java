package com.softserve.academy.Entity;

import java.sql.Date;

/**
 * Class representing Author table in the Librarium database
 *
 * @author Olha Lozinska
 */
public class Author {
    private int id;
    private Date createdAt;
    private int creatorId;
    private String firstName;
    private String lastName;

    public Author() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        if (id != author.id) {
            return false;
        }
        if (creatorId != author.creatorId) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(author.createdAt) : author.createdAt != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(author.firstName) : author.firstName != null) {
            return false;
        }
        return lastName != null ? lastName.equals(author.lastName) : author.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + creatorId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}