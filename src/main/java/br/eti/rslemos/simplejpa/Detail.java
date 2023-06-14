package br.eti.rslemos.simplejpa;

import org.eclipse.persistence.annotations.BatchFetch;
import org.eclipse.persistence.annotations.BatchFetchType;

import javax.persistence.*;

@Entity
@Table(name = "DETAIL")
public class Detail {
    @Id
    @Column(name = "ID")
    public Long id;

    @Column(name = "EXTERNAL_ID", insertable = false, updatable = false)
    public String extId;

    @BatchFetch(BatchFetchType.IN)
    @JoinColumn(name = "ID_MASTER", insertable = false, updatable = false)
    public Master master;
}
