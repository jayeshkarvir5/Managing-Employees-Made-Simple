package com.EmployeeManagementSystem.demo.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="emp_id")
    private Employee employee;

    public LeaveApplication() {
    }

    public LeaveApplication(int id, int days) {
        this.id = id;
        this.days = days;
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
                ", days=" + days +
                '}';
    }
}
