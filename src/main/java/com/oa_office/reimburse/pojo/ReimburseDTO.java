package com.oa_office.reimburse.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

public class ReimburseDTO {

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

	private String proName1;
	private Double proPay1;

	private String proName2;
	private Double proPay2;

	private String proName3;
	private Double proPay3;

	private String proName4;
	private Double proPay4;

	private String proName5;
	private Double proPay5;

	private String proName6;
	private Double proPay6;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getTotal() {
		if (reProjects != null && reProjects.size() > 0) {
			for (ReProject reProject : reProjects) {
				this.total += reProject.getPay();
			}
		}
    	return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getReimburseStaus() {
		return reimburseStaus;
	}

	public void setReimburseStaus(String reimburseStaus) {
		this.reimburseStaus = reimburseStaus;
	}

	public String getProName1() {
		return proName1;
	}

	public void setProName1(String proName1) {
		this.proName1 = proName1;
	}

	public Double getProPay1() {
		return proPay1;
	}

	public void setProPay1(Double proPay1) {
		this.proPay1 = proPay1;
	}

	public String getProName2() {
		return proName2;
	}

	public void setProName2(String proName2) {
		this.proName2 = proName2;
	}

	public Double getProPay2() {
		return proPay2;
	}

	public void setProPay2(Double proPay2) {
		this.proPay2 = proPay2;
	}

	public String getProName3() {
		return proName3;
	}

	public void setProName3(String proName3) {
		this.proName3 = proName3;
	}

	public Double getProPay3() {
		return proPay3;
	}

	public void setProPay3(Double proPay3) {
		this.proPay3 = proPay3;
	}

	public String getProName4() {
		return proName4;
	}

	public void setProName4(String proName4) {
		this.proName4 = proName4;
	}

	public Double getProPay4() {
		return proPay4;
	}

	public void setProPay4(Double proPay4) {
		this.proPay4 = proPay4;
	}

	public String getProName5() {
		return proName5;
	}

	public void setProName5(String proName5) {
		this.proName5 = proName5;
	}

	public Double getProPay5() {
		return proPay5;
	}

	public void setProPay5(Double proPay5) {
		this.proPay5 = proPay5;
	}

	public String getProName6() {
		return proName6;
	}

	public void setProName6(String proName6) {
		this.proName6 = proName6;
	}

	public Double getProPay6() {
		return proPay6;
	}

	public void setProPay6(Double proPay6) {
		this.proPay6 = proPay6;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getTaskCreateTime() {
		return taskCreateTime;
	}

	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public ReimburseDTO() {

	}

	public ReimburseDTO(String id, String userId, String deptId, Date applyTime, Date startTime, Date endTime,
			double total, String memo, String reimburseStaus, String proName1, double proPay1, String proName2,
			double proPay2, String proName3, double proPay3, String proName4, double proPay4, String proName5,
			double proPay5, String proName6, double proPay6, String taskId, String taskName, Date taskCreateTime,
			String assignee, String taskDefinitionKey, String processInstanceId, String processDefinitionId,
			boolean suspended, int version) {
		super();
		this.id = id;
		this.userId = userId;
		this.deptId = deptId;
		this.applyTime = applyTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.total = total;
		this.memo = memo;
		this.reimburseStaus = reimburseStaus;
		this.proName1 = proName1;
		this.proPay1 = proPay1;
		this.proName2 = proName2;
		this.proPay2 = proPay2;
		this.proName3 = proName3;
		this.proPay3 = proPay3;
		this.proName4 = proName4;
		this.proPay4 = proPay4;
		this.proName5 = proName5;
		this.proPay5 = proPay5;
		this.proName6 = proName6;
		this.proPay6 = proPay6;
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskCreateTime = taskCreateTime;
		this.assignee = assignee;
		this.taskDefinitionKey = taskDefinitionKey;
		this.processInstanceId = processInstanceId;
		this.processDefinitionId = processDefinitionId;
		this.suspended = suspended;
		this.version = version;
	}

	public void setReProjects(Set<ReProject> reProjects) {
		this.reProjects = reProjects;
	}

	public Set<ReProject> getReProjects() {
		if (!StringUtils.isEmpty(proName1) && proPay1 > 0) {
			ReProject reProject = new ReProject();
			reProject.setName(proName1);
			reProject.setPay(proPay1);
			reProjects.add(reProject);
		}
		if (!StringUtils.isEmpty(proName2) && proPay2 > 0) {
			ReProject reProject = new ReProject();
			reProject.setName(proName2);
			reProject.setPay(proPay2);
			reProjects.add(reProject);
		}
		if (!StringUtils.isEmpty(proName3) && proPay3 > 0) {
			ReProject reProject = new ReProject();
			reProject.setName(proName3);
			reProject.setPay(proPay3);
			reProjects.add(reProject);
		}
		if (!StringUtils.isEmpty(proName4) && proPay4 > 0) {
			ReProject reProject = new ReProject();
			reProject.setName(proName4);
			reProject.setPay(proPay4);
			reProjects.add(reProject);
		}
		if (!StringUtils.isEmpty(proName5) && proPay5 > 0) {
			ReProject reProject = new ReProject();
			reProject.setName(proName5);
			reProject.setPay(proPay5);
			reProjects.add(reProject);
		}
		if (!StringUtils.isEmpty(proName6) && proPay6 > 0) {
			ReProject reProject = new ReProject();
			reProject.setName(proName6);
			reProject.setPay(proPay6);
			reProjects.add(reProject);
		}

		return reProjects;
	}

}
