package org.example.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@MappedSuperclass
public abstract class Identifiable implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    protected Integer id;

    public Identifiable() {
        //this.setId(idGenerator.getAndIncrement());
    }

    public Integer getId() {
        return id;
    }

    public void setId(int andIncrement) {
        this.id = andIncrement;
    }
}
