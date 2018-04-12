package com.oa_office.activiti.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ModelDTO 
{
	private String id;
	private String name;
	private String key;
	private String category;
	private Date createTime;
	private Date lastUpdateTime;
	private Integer version;
	private String metaInfo;
	private String deploymentId;
	private String tenantId;
	private boolean editorSource;
	private boolean editorSourceExtra;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getKey() {
		return key;
	}
	public String getCategory() {
		return category;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public Integer getVersion() {
		return version;
	}
	public String getMetaInfo() {
		return metaInfo;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public boolean isEditorSource() {
		return editorSource;
	}
	public boolean isEditorSourceExtra() {
		return editorSourceExtra;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public void setEditorSource(boolean editorSource) {
		this.editorSource = editorSource;
	}
	public void setEditorSourceExtra(boolean editorSourceExtra) {
		this.editorSourceExtra = editorSourceExtra;
	}
	
	
}
