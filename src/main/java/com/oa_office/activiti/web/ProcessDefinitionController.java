package com.oa_office.activiti.web;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oa_office.activiti.dto.ProcessDefinitionDTO;
import com.oa_office.common.util.AJAXResultMessage;
import com.oa_office.common.util.ExtPageable;


@Controller
@RequestMapping(value = "/processdefinition")
public class ProcessDefinitionController {

	@Autowired
    private RepositoryService repositoryService;
	
    /**
     * 1.流程定义列表
     * 
     * @param pageable Extjs分页的封装类
     * @return Page<ProcessDefinitionDTO> 分页对象
     */
    @RequestMapping(value = "/list")
    public @ResponseBody Page<ProcessDefinitionDTO> processAjaxList(ExtPageable pageable) {
       //1.查询所有已部署的流程定义
       List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
       List<ProcessDefinitionDTO> dtoLists = null;
       
       if(processDefinitionList!=null){
    	   dtoLists = new ArrayList<ProcessDefinitionDTO>();
    	   for (ProcessDefinition pd : processDefinitionList) {
    		    ProcessDefinitionDTO dtoTemp = new ProcessDefinitionDTO();

    			dtoTemp.setId(pd.getId());
    			dtoTemp.setCategory(pd.getCategory());
    			dtoTemp.setName(pd.getName());
    			dtoTemp.setKey(pd.getKey());
    			dtoTemp.setDescription(pd.getDescription());
    			dtoTemp.setVersion(pd.getVersion());
    			dtoTemp.setResourceName(pd.getResourceName());
    			dtoTemp.setDeploymentId(pd.getDeploymentId());
    			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(pd.getDeploymentId()).singleResult();
    			if(deployment!=null){
    				dtoTemp.setDeploymentTime(deployment.getDeploymentTime());
    			}
    			
    			dtoTemp.setDiagramResourceName(pd.getDiagramResourceName());
    			dtoTemp.setTenantId(pd.getTenantId());
    			dtoTemp.setStartFormKey(pd.hasStartFormKey());
    			dtoTemp.setGraphicalNotation(pd.hasGraphicalNotation());
    			dtoTemp.setSuspended(pd.isSuspended());
    			
    			dtoLists.add(dtoTemp);
    	   }
    	   
       }

       //2.把流程列表集合封装为Spring分页对象
       Page<ProcessDefinitionDTO> processDefinitionPage = new PageImpl<ProcessDefinitionDTO>(dtoLists, pageable.getPageable(), dtoLists!=null?dtoLists.size():0);
       
       return processDefinitionPage;
    }
    
    /**
     * 2.上传并部署流程资源
     * 
     * @param file 上传部署流程文件(路径)
     * 文件格式: zip、bar、bpmn、bpmn20.xml
     * 
     * @return AJAXResultMessage  ajax的响应内容封装对象
     */
    @RequestMapping(value = "/deploy")
    public @ResponseBody AJAXResultMessage deploy(@RequestParam(value = "file", required = true) MultipartFile file) {
        // 获取上传的文件名
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
        try {
            // 得到输入流（字节流）对象
            InputStream fileInputStream = file.getInputStream();
            // 文件的扩展名
            String extension = FilenameUtils.getExtension(fileName);
            // zip或者bar类型的文件用ZipInputStream方式部署
            DeploymentBuilder deployment = repositoryService.createDeployment();
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment.addZipInputStream(zip);
            } else {
                // 其他类型的文件直接部署  bpmn
                deployment.addInputStream(fileName, fileInputStream);
            }
            deployment.deploy();
            return new AJAXResultMessage(true,"部署成功!");
        } catch (Exception e) {
            
            return new AJAXResultMessage(false,"部署失败!");
        }
    }
    
    /**
     * 3.读取流程资源
     *
     * @param processDefinitionId 流程定义ID
     * @param resourceName        资源名称
     */
    @RequestMapping(value = "/resource")
    public void readResource(@RequestParam("pdid") String processDefinitionId, @RequestParam("resourceName") String resourceName, HttpServletResponse response)
            throws Exception {
        ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition pd = pdq.processDefinitionId(processDefinitionId).singleResult();

        // 通过接口读取
        InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), resourceName);
        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    
    /**
     * 4.删除流程
     *
     * @param deploymentId 部署ID
     * @return AJAXResultMessage  ajax的响应内容封装对象
     */
    @RequestMapping(value = "/delete")
    public  @ResponseBody AJAXResultMessage deleteProcessDefinition(@RequestParam("deploymentId") String deploymentId) {
    	try {
    		repositoryService.deleteDeployment(deploymentId, true);
	    } catch (Exception e) {
	       
	        return new AJAXResultMessage(false,"删除失败!");
	    }
	    return new AJAXResultMessage(true,"删除成功!");
    }

    /**
     * 5.挂起、激活流程定义
     */
    @RequestMapping(value = "/update/{state}/{processDefinitionId}")
    public @ResponseBody AJAXResultMessage updateState(@PathVariable("state") String state, @PathVariable("processDefinitionId") String processDefinitionId) {
    	try {
	    	if (state.equals("active")) {
	            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
	        } else if (state.equals("suspend")) {
	            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
	        }
	    	return new AJAXResultMessage(true,"已"+(state.equals("active")?"激活":"挂起")+"ID为[" + processDefinitionId + "]的流程定义。");
    	} catch (Exception e) {
	        return new AJAXResultMessage(false,"操作失败!");
	    }
    }
    
    /**
     * 6.流程定义转换为model（在线编辑和部署）
     */
    @RequestMapping(value = "/convert-to-model/{processDefinitionId}")
    public @ResponseBody AJAXResultMessage convertToModel(@PathVariable("processDefinitionId") String processDefinitionId){
    	try {
    		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),processDefinition.getResourceName());
	        XMLInputFactory xif = XMLInputFactory.newInstance();
	        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
	        XMLStreamReader xtr = xif.createXMLStreamReader(in);
	        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
	        BpmnJsonConverter converter = new BpmnJsonConverter();
	        com.fasterxml.jackson.databind.node.ObjectNode modelNode = converter.convertToJson(bpmnModel);
	        Model modelData = repositoryService.newModel();
	        modelData.setKey(processDefinition.getKey());
	        modelData.setName(processDefinition.getResourceName());
	        modelData.setCategory(processDefinition.getDeploymentId());

	        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
	        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
	        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
	        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
	        modelData.setMetaInfo(modelObjectNode.toString());

	        repositoryService.saveModel(modelData);
	        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
	        
	        return new AJAXResultMessage(true,"转换成功！");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new AJAXResultMessage(false,"转换失败，转码错误！");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			return new AJAXResultMessage(false,"转换失败，创建XML读取流错误！");
		}
    }
}
