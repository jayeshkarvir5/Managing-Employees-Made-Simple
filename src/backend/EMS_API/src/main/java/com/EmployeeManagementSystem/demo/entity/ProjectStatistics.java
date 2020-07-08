package com.EmployeeManagementSystem.demo.entity;

import java.io.Serializable;

public class ProjectStatistics implements Serializable {
    String projectName;
    int duration;

    public ProjectStatistics(String projectName, int duration) {
        this.projectName = projectName;
        this.duration = duration;
    }

    public ProjectStatistics() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
