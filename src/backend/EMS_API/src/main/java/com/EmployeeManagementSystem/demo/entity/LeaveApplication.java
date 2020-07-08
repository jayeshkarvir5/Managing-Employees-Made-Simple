package com.EmployeeManagementSystem.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="leave_application")
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="days")
    private int days;

    @Column(name = "approved")
    private boolean approved;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="emp_id")
    private Employee employee;

    public LeaveApplication() {
    }

    public LeaveApplication(int id, int days, boolean approved) {
        this.id = id;
        this.days = days;
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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

//    @Override
//    public String toString() {
//        return "LeaveApplication{" +
//                "id=" + id +
//                ", days=" + days +
//                '}';
//    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
                "id=" + id +
                ", days=" + days +
                ", approved=" + approved +
                ", employee=" + employee +
                '}';
    }
}
