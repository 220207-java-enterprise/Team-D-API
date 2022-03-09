package com.revature.technology.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;


// JPA Annotations:
//   - @Entity: declares that the annotated class maps to a table
//   - @Table: implicit, but if provided allows you to specify a table name and some constraints
//   - @Id: declares the annotated field as a primary key
//   - @GeneratedValue: allows for you to specify how the primary keys are generated
//   - @Column: implicit, but if provided allows you to specify a column name and some constraints
//   - @JoinColumn: declares that the annotated field is a foreign key
//   - @OneToMany: declares the multiplicity relationship between the entity class and core field type
//   - @Embeddable: declares that the annotated class can be embedded within an entity class
//   - @Embedded: simply allows for you to embed other columns into this entity class's table

@Entity
@Table(name = "ers_reimbursements")
public class Reimbursement {

    @Id
    @Column(name = "reimb_id")
    private String reimb_id;

    @Column(name = "amount", nullable = false, columnDefinition = "numeric(6,2)")
    private double amount;

    @Column(name = "submitted", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime submitted;

    @Column(name = "resolved")
    private LocalDateTime resolved;

    @Column(name = "description", nullable=false)
    private String description;

    @Column(name = "receipt", columnDefinition = "BLOB")
    private byte[] receipt;

    @Column(name = "payment_id")
    private String payment_id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author_user;

    @ManyToOne
    @JoinColumn(name = "resolver_id")
    private User resolver_user;

    @ManyToOne
    @JoinColumn(name = "status_id",nullable = false)
    private ReimbursementStatus status;

    @ManyToOne
    @JoinColumn(name = "type_id" ,nullable = false)
    private ReimbursementType type;

    public Reimbursement() {
        super();
    }

    public Reimbursement(String reimb_id, double amount, LocalDateTime submitted, LocalDateTime resolved, String description, byte[] receipt, String payment_id, User author_user, User resolver_user, ReimbursementStatus status, ReimbursementType type) {
        this.reimb_id = reimb_id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.author_user = author_user;
        this.resolver_user = resolver_user;
        this.status = status;
        this.type = type;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getResolved() {
        return resolved;
    }

    public void setResolved(LocalDateTime resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public User getAuthor_user() {
        return author_user;
    }

    public void setAuthor_user(User author_user) {
        this.author_user = author_user;
    }

    public User getResolver_user() {
        return resolver_user;
    }

    public void setResolver_user(User resolver_user) {
        this.resolver_user = resolver_user;
    }

    public ReimbursementStatus getStatus() {
        return status;
    }

    public void setStatus(ReimbursementStatus status) {
        this.status = status;
    }

    public ReimbursementType getType() {
        return type;
    }

    public void setType(ReimbursementType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reimbursement)) return false;
        Reimbursement that = (Reimbursement) o;
        return Double.compare(that.getAmount(), getAmount()) == 0 && Objects.equals(getReimb_id(), that.getReimb_id()) && Objects.equals(getSubmitted(), that.getSubmitted()) && Objects.equals(getResolved(), that.getResolved()) && Objects.equals(getDescription(), that.getDescription()) && Arrays.equals(getReceipt(), that.getReceipt()) && Objects.equals(getPayment_id(), that.getPayment_id()) && Objects.equals(getAuthor_user(), that.getAuthor_user()) && Objects.equals(getResolver_user(), that.getResolver_user()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getReimb_id(), getAmount(), getSubmitted(), getResolved(), getDescription(), getPayment_id(), getAuthor_user(), getResolver_user(), getStatus(), getType());
        result = 31 * result + Arrays.hashCode(getReceipt());
        return result;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimb_id='" + reimb_id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", payment_id='" + payment_id + '\'' +
                ", author_user=" + author_user +
                ", resolver_user=" + resolver_user +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
