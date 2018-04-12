package com.oa_office.reimburse.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reimburse")
public class Reimburse {

	// 报销单id
	private String id;
	// 报销人
	private String userId;
	// 报销部门
	private String deptId;
	// 报销申请时间
	private Date applyTime;
	// 报销开始时间
	private Date startTime;
	// 报销结束时间
	private Date endTime;
	// 报销总金额
	private double total = 0;
	// 报销备注
	private String memo;
	// 报销状态
	private String reimburseStaus;
	// 报销单对应的具体报销项目
	private Set<ReProject> reProjects = new HashSet<ReProject>();

	/** ------------流程数据-------------- **/
	/* 任务 */
	private String taskId;
	private String taskName;
	private Date taskCreateTime;
	private String assignee;
	private String taskDefinitionKey;
	/* 流程实例 */
	private String processInstanceId;
	/* 流程图定义 */
	private String processDefinitionId;
	private boolean suspended;
	private int version;

	@Id
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(length = 35)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 35, name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(length = 35, name = "dept_id")
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
	public Date getApplyTime() {
		return applyTime;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
	public Date getStartTime() {
		return startTime;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd HH")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
	public Date getEndTime() {
		return endTime;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd HH")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "total")
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Column(columnDefinition = "TEXT")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "reimburse_id") // 在多表的一方外键名称为"reimburse_id"
	public Set<ReProject> getReProjects() {
		return reProjects;
	}

	public void setReProjects(Set<ReProject> reProjects) {
		this.reProjects = reProjects;
	}

	@Column(length = 20, name = "reimburse_status")
	public String getReimburseStaus() {
		return reimburseStaus;
	}

	public void setReimburseStaus(String reimburseStaus) {
		this.reimburseStaus = reimburseStaus;
	}

	public Reimburse(String id, String userId, String deptId, Date applyTime, Date startTime, Date endTime,
			double total, String memo, Set<ReProject> reProjects) {
		this.id = id;
		this.userId = userId;
		this.deptId = deptId;
		this.applyTime = applyTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.total = total;
		this.memo = memo;
		this.reProjects = reProjects;
	}

	public Reimburse() {
	}

	/**
	 * -----------------------------------流程数据get和set方法------------------------------------
	 **/
	@Column(length = 35)
	public String getTaskId() {
		return taskId;
	}

	@Column(length = 35)
	public String getTaskName() {
		return taskName;
	}

	public Date getTaskCreateTime() {
		return taskCreateTime;
	}

	@Column(length = 35)
	public String getAssignee() {
		return assignee;
	}

	@Column(length = 35)
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	@Column(length = 35)
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	@Column(length = 35)
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public int getVersion() {
		return version;
	}

	// -----------------------------------------setter方法-------------------
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskCreateTime(Date taskCreateTime) {
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
