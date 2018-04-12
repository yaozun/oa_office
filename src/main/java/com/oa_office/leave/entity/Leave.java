package com.oa_office.leave.entity;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Entity: Leave
 *
 * @author HenryYan
 */
@Entity
@Table(name = "OA_LEAVE")
public class Leave  implements Serializable {

    private static final long serialVersionUID = 1L;
    //业务数据字段
    private Long id;
    private String userId;
    private Date startTime;
    private Date endTime;
    private Date realityStartTime;
    private Date realityEndTime;
    private Date applyTime;
    private String leaveType;
    private String reason;
    private String leaveStaus;

    //工作流程数据字段
    
    //流程实例Id：用于关联下列临时数据
    private String processInstanceId;	//没有启动流程之前为""
    
    //-- 临时属性 @Transient 不会被保存到当前实体的OA_LEAVE表，用于临时存储流程数据，目的是让工作流数据与业务数据结合--//
    
    private Task task;// 流程任务
    
    private Map<String, Object> variables;// 流程变量

    // 运行中的流程实例
    private ProcessInstance processInstance;

    // 历史的流程实例
    private HistoricProcessInstance historicProcessInstance;

    // 流程定义
    private ProcessDefinition processDefinition;

    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_TIME")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    //@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    //@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    //@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Column
    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    @Column
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REALITY_START_TIME")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    //@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getRealityStartTime() {
        return realityStartTime;
    }

    public void setRealityStartTime(Date realityStartTime) {
        this.realityStartTime = realityStartTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REALITY_END_TIME")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    //@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getRealityEndTime() {
        return realityEndTime;
    }

    public void setRealityEndTime(Date realityEndTime) {
        this.realityEndTime = realityEndTime;
    }

    @Transient
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Transient
    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Transient
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    @Transient
    public HistoricProcessInstance getHistoricProcessInstance() {
        return historicProcessInstance;
    }

    public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
        this.historicProcessInstance = historicProcessInstance;
    }

    @Transient
    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

	public String getLeaveStaus() {
		return leaveStaus;
	}

	public void setLeaveStaus(String leaveStaus) {
		this.leaveStaus = leaveStaus;
	}
    
    

}
