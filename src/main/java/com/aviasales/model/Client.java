package com.aviasales.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
@Getter @Setter
@RequiredArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientId")
    private Long id;

    @Column(name = "fio_client")
    private String fio_client;

    @Column(name = "series_passport")
    private Long pass_series;

    @Column(name = "number_pass")
    private Long pass_numb;

    @Column(name = "bonuses",columnDefinition = "integer default 0")
    private int cashBack;

    @Column(name = "locked",columnDefinition = "boolean default true")
    private Boolean isLocked  ;

    @Column(name = "total",columnDefinition = "integer default 0")
    private int totalBuy;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Ticket> tickets;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fio_client == null) ? 0 : fio_client.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Client other = (Client) obj;
        if (fio_client == null) {
            if (other.fio_client != null)
                return false;
        } else if (!fio_client.equals(other.fio_client))
            return false;
        return true;
    }
}
