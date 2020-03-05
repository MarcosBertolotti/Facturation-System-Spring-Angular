package com.springboot.apirest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bills")
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Integer id;

    @Column(nullable = false)
    @NotEmpty
    private String description;

    private String observation;

    @Column(name = "create_at")
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @JsonIgnoreProperties(value={"bills", "hibernateLazyInitializer", "handler"}, allowSetters = true)   // ignoramos las bills del client y los atributos del proxy, allowsetters por problema al update de client
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)       // si elimianmos el bill, tambien se elimina las lineas, si guardamos el bill luego se guardan las lineas
    @JoinColumn(name = "bill_id")   // este fk se crea en la tabla items_bills
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // si no los ignoramos lanza un error, son propios del proxy
    private List<ItemBill> items = new ArrayList<>();

    public Double getTotal() {

        return items.stream()
                .mapToDouble(ItemBill::getAmount)
                .sum();
    }

    @PrePersist
    public void prePersist() {

        if(this.createAt == null) {
            this.createAt = new Date();
        }
    }

    private static final long serialVersionUID = 1L;
}
