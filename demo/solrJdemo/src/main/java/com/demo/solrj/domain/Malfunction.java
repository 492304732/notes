package com.demo.solrj.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class Malfunction {

    @Field
    private String id;

    @Field
    private String title;

    @Field
    private String description;

    @Field
    private String module;

    @Field
    private Integer type;

    @Field
    private String reportBy;

    @Field
    private String followBy;

    @Field
    private Date solveStartTime;

    @Field
    private Date solveEndTime;

    @Field
    private Integer solveState;

    @Field
    private String solution;

    @Field
    private Integer system;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReportBy() {
        return reportBy;
    }

    public void setReportBy(String reportBy) {
        this.reportBy = reportBy == null ? null : reportBy.trim();
    }

    public String getFollowBy() {
        return followBy;
    }

    public void setFollowBy(String followBy) {
        this.followBy = followBy == null ? null : followBy.trim();
    }

    public Date getSolveStartTime() {
        return solveStartTime;
    }

    public void setSolveStartTime(Date solveStartTime) {
        this.solveStartTime = solveStartTime;
    }

    public Date getSolveEndTime() {
        return solveEndTime;
    }

    public void setSolveEndTime(Date solveEndTime) {
        this.solveEndTime = solveEndTime;
    }

    public Integer getSolveState() {
        return solveState;
    }

    public void setSolveState(Integer solveState) {
        this.solveState = solveState;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution == null ? null : solution.trim();
    }

    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "Malfunction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", module='" + module + '\'' +
                ", type=" + type +
                ", reportBy='" + reportBy + '\'' +
                ", followBy='" + followBy + '\'' +
                ", solveStartTime=" + solveStartTime +
                ", solveEndTime=" + solveEndTime +
                ", solveState=" + solveState +
                ", solution='" + solution + '\'' +
                ", system=" + system +
                '}';
    }
}