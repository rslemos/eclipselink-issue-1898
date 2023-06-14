package br.eti.rslemos.simplejpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER")
public class Master {
    @Id
    @Column(name = "ID")
    public Long id;
}
