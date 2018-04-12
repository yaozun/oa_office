package com.oa_office.leave.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LeaveDTO {
	/**------------业务数据--------------**/
	/*请假*/
    private Long id;
    private String userId;
    private Date applyTime;
    private Date startTime;
    private Date endTime;
    private String leaveType;
    private String reason;
    /*销假*/
    private Date realityStartTime;
    private Date realityEndTime;
    /**------------流程数据--------------**/
    /*任务*/
    private String taskId;
    private String taskName;
    private Date   taskCreateTime;
    private String assignee;
    private String taskDefinitionKey;
    /*流程实例*/
    private String processInstanceId;
    /*流程图定义*/
    private String processDefinitionId;
    private boolean suspended;
    private int version;
    
    
	public Long getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date getApplyTime() {
		return applyTime;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date getStartTime() {
		return startTime;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date getEndTime() {
		return endTime;
	}
	
	public String getLeaveType() {
		return leaveType;
	}
	public String getReason() {
		return reason;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date getRealityStartTime() {
		return realityStartTime;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date getRealityEndTime() {
		return realityEndTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date  getTaskCreateTime() {
		return taskCreateTime;
	}
	public String getAssignee() {
		return assignee;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public boolean getSuspended() {
		return suspended;
	}
	public int getVersion() {
		return version;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setRealityStartTime(Date realityStartTime) {
		this.realityStartTime = realityStartTime;
	}
	public void setRealityEndTime(Date realityEndTime) {
		this.realityEndTime = realityEndTime;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setTaskCreateTime(Date  taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public void setVersion(int version) {
		this.version = version;
	}
    
    
}
