package com.vyka.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.OrderStatus;

/**
 * A DTO for the PackageOrder entity.
 */
public class PackageOrderDTO implements Serializable {

    private Long id;

    private BigDecimal rate;

    private BigDecimal totalAmount;

    private Integer quantity;

    @NotNull
    private Integer profileSubjectId;

    private OrderStatus status;

    @NotNull
    private Instant createdDate;

    private Instant updatedDate;

    @NotNull
    private Integer studentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProfileSubjectId() {
        return profileSubjectId;
    }

    public void setProfileSubjectId(Integer profileSubjectId) {
        this.profileSubjectId = profileSubjectId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PackageOrderDTO packageOrderDTO = (PackageOrderDTO) o;
        if(packageOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), packageOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PackageOrderDTO{" +
            "id=" + getId() +
            ", rate='" + getRate() + "'" +
            ", totalAmount='" + getTotalAmount() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", profileSubjectId='" + getProfileSubjectId() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", studentId='" + getStudentId() + "'" +
            "}";
    }
}
