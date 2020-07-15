package com.EmployeeManagementSystem.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="leave_application")
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

//    @Column(name="days")
//    private int days;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "approved")
    private boolean approved;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="emp_id")
    private Employee employee;

    public LeaveApplication() {
    }

    public LeaveApplication(int id, Date startDate, Date endDate, boolean approved, Employee employee) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approved = approved;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", approved=" + approved +
                ", employee=" + employee +
                '}';
    }
}
