package com.example.spring2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
class BookEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;

    public BookEntity() {
    }

    BookEntity(String title, String author) {

        this.title = title;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof BookEntity))
            return false;
        BookEntity bookEntity = (BookEntity) o;
        return Objects.equals(this.id, bookEntity.id) && Objects.equals(this.title, bookEntity.author)
                && Objects.equals(this.author, bookEntity.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.author);
    }
}