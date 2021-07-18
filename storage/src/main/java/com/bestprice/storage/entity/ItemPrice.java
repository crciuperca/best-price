package com.bestprice.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "item_price_history")
public class ItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Float price;
    private String URL;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @Column(name = "created_at")
    private Date createdAt;
}
