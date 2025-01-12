package com.nnk.springboot.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bid_list_id")
    private Integer bidListId;

    @Column(name="account")
    private String account;

    @Column(name="type")
    private String type;

    @NumberFormat
    @Column(name="bid_quantity")
    private Double bidQuantity;

    @NumberFormat
    @Column(name="ask_quantity")
    private Double askQuantity;

    @NumberFormat
    @Column(name="bid")
    private Double bid;

    @NumberFormat
    @Column(name="ask")
    private Double ask;

    @Column(name="benchmark")
    private String benchmark;

    @Column(name="bid_list_date")
    private Timestamp bidListDate;

    @Column(name="commentary")
    private String commentary;

    @Column(name="security")
    private String security;

    @Column(name="status")
    private String status;

    @Column(name="trader")
    private String trader;

    @Column(name="book")
    private String book;

    @Column(name="creation_name")
    private String creationName;

    @Column(name="creation_date")
    private Timestamp creationDate;

    @Column(name="revision_name")
    private String revisionName;

    @Column(name="revision_date")
    private Timestamp revisionDate;

    @Column(name="deal_name")
    private String dealName;

    @Column(name="deal_type")
    private String dealType;

    @Column(name="source_list_id")
    private String sourceListId;

    @Column(name="side")
    private String side;

    public Integer getBidListId() {
        return bidListId;
    }

    public void setBidListId(Integer bidListId) {
        this.bidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public Double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
