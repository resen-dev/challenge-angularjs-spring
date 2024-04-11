package com.netdeal.teste.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBLUSERS")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idsuperior")
    private UserModel superior = null;

    private String name;

    private Integer passwordStrength;

    private BigInteger qtdSubordinado = BigInteger.ZERO;

    public void addQtdSubordinate(BigInteger qtd){
        this.qtdSubordinado = this.getQtdSubordinado().add(qtd);
    }

    public void subtractQtdSubordinate(BigInteger qtd){
        this.qtdSubordinado = this.getQtdSubordinado().subtract(qtd);
    }
}
