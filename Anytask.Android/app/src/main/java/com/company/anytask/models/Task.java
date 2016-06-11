package com.company.anytask.models;

import java.util.Date;

public class Task {
    public int id;
    public String name;
    public String description;
    public Date deadline;
    public boolean isStrict;
    public int maxScore;
    public Course course;
}
