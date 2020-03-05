package com.springboot.apirest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer id;

    @Column(nullable = false, name = "first_name")
    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @NotEmpty
    @Size(min = 2, max = 20)
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;

    @NotNull(message = "can't be empty")
    @Column(nullable = false, name = "create_at")
    @Temporal(TemporalType.DATE) //formato/tipo de fecha en la bd
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    private String photo;

    @NotNull(message = "can't be empty")
    @ManyToOne(fetch = FetchType.LAZY)  // se genera un proxy hacia el objeto region entity, este proxy va a generar otros atributos propios del framework, que debemos quitar de json
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // si no los ignoramos lanza un error, son propios del proxy
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client")  // Cascade All= si se elimina el client, tambien se eliminan las bill hijas, si guardamos un cliente con facturas, primero guarda el client y luego inserta las bill hijas
    @JsonIgnoreProperties(value = {"client", "hibernateLazyInitializer", "handler"}, allowSetters = true)   // ignoramos el client del bill, allowsetters por problema al update de client
    private List<Bill> bills = new ArrayList<>();

    private static final long serialVersionUID = 1L;

}
