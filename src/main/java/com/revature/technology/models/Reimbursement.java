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
    private String reimbId;

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
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User authorUser;

    @ManyToOne
    @JoinColumn(name = "resolver_id")
    private User resolverUser;

    @ManyToOne
    @JoinColumn(name = "status_id",nullable = false)
    private ReimbursementStatus status;

    @ManyToOne
    @JoinColumn(name = "type_id" ,nullable = false)
    private ReimbursementType type;

    public String getReimbId() {
        return reimbId;
    }

    public void setReimbId(String reimbId) {
        this.reimbId = reimbId;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public User getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(User authorUser) {
        this.authorUser = authorUser;
    }

    public User getResolverUser() {
        return resolverUser;
    }

    public void setResolverUser(User resolverUser) {
        this.resolverUser = resolverUser;
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
        return Double.compare(that.getAmount(), getAmount()) == 0 && Objects.equals(getReimbId(), that.getReimbId()) && Objects.equals(getSubmitted(), that.getSubmitted()) && Objects.equals(getResolved(), that.getResolved()) && Objects.equals(getDescription(), that.getDescription()) && Arrays.equals(getReceipt(), that.getReceipt()) && Objects.equals(getPaymentId(), that.getPaymentId()) && Objects.equals(getAuthorUser(), that.getAuthorUser()) && Objects.equals(getResolverUser(), that.getResolverUser()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getReimbId(), getAmount(), getSubmitted(), getResolved(), getDescription(),
                getPaymentId(), getAuthorUser(), getResolverUser(), getStatus(), getType());
        result = 31 * result + Arrays.hashCode(getReceipt());
        return result;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId='" + reimbId + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", paymentId='" + paymentId + '\'' +
                ", authorUser=" + authorUser +
                ", resolverUser=" + resolverUser +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
