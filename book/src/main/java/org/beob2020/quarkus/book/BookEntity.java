package org.beob2020.quarkus.book;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_table")
public class BookEO extends PanacheEntityBase {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @Id
    @Column(updatable = false)
    private Long bookId;
    private String field;


}
