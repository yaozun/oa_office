package com.oa_office.activiti.web;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oa_office.activiti.dto.ModelDTO;
import com.oa_office.common.util.AJAXResultMessage;
import com.oa_office.common.util.ExtPageable;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 流程模型控制器
 *
 */
@Controller
@RequestMapping(value = "/model")
public class ModelController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 1.模型列表
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody Page<ModelDTO> modelList(ExtPageable pageable) {
		List<Model> list = repositoryService.createModelQuery().list();
		List<ModelDTO> dtoLists = null;
		if (list != null) {
			dtoLists = new ArrayList<ModelDTO>();
			for (Model model : list) {
				ModelDTO temp = new ModelDTO();
				temp.setId(model.getId());
				temp.setName(model.getName());
				temp.setKey(model.getKey());
				temp.setCategory(model.getCategory());
				temp.setCreateTime(model.getCreateTime());
				temp.setLastUpdateTime(model.getLastUpdateTime());
				temp.setVersion(model.getVersion());
				temp.setMetaInfo(model.getMetaInfo());
				temp.setDeploymentId(model.getDeploymentId());
				temp.setTenantId(model.getTenantId());
				temp.setEditorSource(model.hasEditorSource());
				temp.setEditorSourceExtra(model.hasEditorSourceExtra());

				dtoLists.add(temp);
			}
		}
		Page<ModelDTO> modelPage = new PageImpl<ModelDTO>(dtoLists, pageable.getPageable(),
				dtoLists != null ? dtoLists.size() : 0);
		return modelPage;
	}

	/**
	 * 2.创建模型
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody AJAXResultMessage create(@RequestParam("name") String name, @RequestParam("key") String key,
			@RequestParam("description") String description, HttpServletRequest request, HttpServletResponse response) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));

			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

			// response.sendRedirect(request.getContextPath() +
			// "/modeler.html?modelId=" + modelData.getId());
			return new AJAXResultMessage(true, request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());// 前端window.open();
		} catch (Exception e) {
			return new AJAXResultMessage(false, "创建模型失败!");
		}
	}

	/**
	 * 3.根据Model部署流程定义
	 */
	@RequestMapping(value = "/deploy/{modelId}")
	public @ResponseBody AJAXResultMessage deploy(@PathVariable("modelId") String modelId,
			RedirectAttributes redirectAttributes) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);

			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
					.addString(processName, new String(bpmnBytes)).deploy();

			return new AJAXResultMessage(true, "部署成功，部署ID=" + deployment.getId());
		} catch (Exception e) {
			logger.error("根据模型部署流程失败：modelId={}", modelId, e);
			return new AJAXResultMessage(false, "根据模型部署流程失败：modelId=" + modelId);
		}
	}

	/**
	 * 4.删除模型
	 */
	@RequestMapping(value = "/delete/{modelId}")
	public  @ResponseBody AJAXResultMessage delete(@PathVariable("modelId") String modelId) {
		try {
			repositoryService.deleteModel(modelId);
			return new AJAXResultMessage(true, "删除成功!");
		} catch (Exception e) {
			return new AJAXResultMessage(true, "删除失败!");
		}
	}
	
	/**
	 * 5.导出model对象为指定类型
	 *
	 * @param modelId 模型ID
	 * @param type 	     导出文件类型(bpmn\json)
	 * 
	 *  JSON乱码问题待解决
	 */
	@RequestMapping(value = "/export/{modelId}/{type}")
	public void export(@PathVariable("modelId") String modelId, @PathVariable("type") String type,HttpServletResponse response) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

			JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			// 处理异常
			if (bpmnModel.getMainProcess() == null) {
				response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
				response.getOutputStream().println("no main process, can't export for type: " + type);
				response.flushBuffer();
				return;
			}
			String filename = "";
			byte[] exportBytes = null;
			String mainProcessId = bpmnModel.getMainProcess().getId();
			if (type.equals("bpmn")) {
				BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
				exportBytes = xmlConverter.convertToXML(bpmnModel);
				filename = mainProcessId + ".bpmn20.xml";
			} else if (type.equals("json")) {
				exportBytes = modelEditorSource;
				filename = mainProcessId + ".json";
			}
			ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
			IOUtils.copy(in, response.getOutputStream());
			
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
		}
	}

	

}
