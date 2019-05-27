package com.softserve.academy.Entity;

import java.sql.Date;
import java.util.List;

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
    private String imageUrl;
    private List<Author> authors;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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
        if (description != null ? !description.equals(book.description) : book.description != null) {
            return false;
        }
        if (imageUrl != null ? !imageUrl.equals(book.imageUrl) : book.imageUrl != null) {
            return false;
        }
        return authors != null ? authors.equals(book.authors) : book.authors == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + pageQuantity;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", pageQuantity=" + pageQuantity +
            '}';
    }
}
