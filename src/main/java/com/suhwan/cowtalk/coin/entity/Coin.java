package com.suhwan.cowtalk.coin.entity;

import com.suhwan.cowtalk.exchange.entity.Exchange;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Coin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String initial;

  @Column
  private String name;

  @ManyToOne
  @JoinColumn(name = "exchange_id")
  private Exchange exchange;

  @Column
  private LocalDateTime createDate;

  @Column
  private LocalDateTime updateDate;

  @Column
  private LocalDateTime deleteDate;
}
