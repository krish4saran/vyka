package com.vyka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.vyka.domain.enumeration.OrderStatus;

/**
 * A PackageOrder.
 */
@Entity
@Table(name = "package_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "packageorder")
public class PackageOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate", precision=10, scale=2)
    private BigDecimal rate;

    @Column(name = "total_amount", precision=10, scale=2)
    private BigDecimal totalAmount;

    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Column(name = "profile_subject_id", nullable = false)
    private Integer profileSubjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @OneToMany(mappedBy = "packageOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "packageOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderActivity> orderActivities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public PackageOrder rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public PackageOrder totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PackageOrder quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProfileSubjectId() {
        return profileSubjectId;
    }

    public PackageOrder profileSubjectId(Integer profileSubjectId) {
        this.profileSubjectId = profileSubjectId;
        return this;
    }

    public void setProfileSubjectId(Integer profileSubjectId) {
        this.profileSubjectId = profileSubjectId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PackageOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PackageOrder createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public PackageOrder updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public PackageOrder studentId(Integer studentId) {
        this.studentId = studentId;
        return this;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public PackageOrder schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public PackageOrder addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setPackageOrder(this);
        return this;
    }

    public PackageOrder removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setPackageOrder(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<OrderActivity> getOrderActivities() {
        return orderActivities;
    }

    public PackageOrder orderActivities(Set<OrderActivity> orderActivities) {
        this.orderActivities = orderActivities;
        return this;
    }

    public PackageOrder addOrderActivity(OrderActivity orderActivity) {
        this.orderActivities.add(orderActivity);
        orderActivity.setPackageOrder(this);
        return this;
    }

    public PackageOrder removeOrderActivity(OrderActivity orderActivity) {
        this.orderActivities.remove(orderActivity);
        orderActivity.setPackageOrder(null);
        return this;
    }

    public void setOrderActivities(Set<OrderActivity> orderActivities) {
        this.orderActivities = orderActivities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PackageOrder packageOrder = (PackageOrder) o;
        if (packageOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), packageOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PackageOrder{" +
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
