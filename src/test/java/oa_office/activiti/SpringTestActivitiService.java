package oa_office.activiti;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oa_office.common.util.AJAXResultMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Transactional
public class SpringTestActivitiService {

	@Autowired
	private RepositoryService repositoryService;// 提供了管理和控制 流程部署和流程定义的操作。

	/**
	 * 1.部署流程
	 */
	/*
	 * @Test
	 * 
	 * @Rollback(false) public void depoly() { Deployment deployment
	 * =repositoryService.createDeployment()//2.创建部署对象
	 * .name("HelloWorld")//3.设置部署流程的名称.
	 * .addClasspathResource("diagrams/HelloWorld.bpmn")//4.增加流程的bpmn文件.
	 * .addClasspathResource("diagrams/HelloWorld.png")//5.增加流程的图片. .deploy();//6.部署
	 * 
	 * System.out.println("部署ID:"+deployment.getId());
	 * System.out.println("部署Name:"+deployment.getName());
	 * System.out.println("部署时间:"+deployment.getDeploymentTime().toLocaleString());
	 * }
	 */

	/**
	 * 1.部署流程
	 */
	@Test
	@Rollback(false)
	public void depoly1() {

		String fileName = "leave.bpmn";
		try {
			InputStream fileInputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/leave.bpmn");
			// 文件的扩展名
			String extension = FilenameUtils.getExtension(fileName);
			// zip或者bar类型的文件用ZipInputStream方式部署
			DeploymentBuilder deployment = repositoryService.createDeployment();
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment.addZipInputStream(zip);
			} else {
				// 其他类型的文件直接部署 bpmn
				deployment.addInputStream(fileName, fileInputStream);
			}
			deployment.deploy();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 3.读取流程资源
	 *
	 * @param processDefinitionId
	 *            流程定义ID
	 * @param resourceName
	 *            资源名称
	 */
	@Test
	@Rollback(false)
	public void readResource() {
		ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition pd = pdq.processDefinitionId("leave:1:4").singleResult();
		ProcessDefinition pd1 = pdq.processDefinitionKey("leave").singleResult();
		System.out.println(pd1);

		// // 通过接口读取
		// InputStream resourceAsStream =
		// repositoryService.getResourceAsStream(pd.getDeploymentId(), resourceName);
		//
		// // 输出资源内容到相应对象
		// byte[] b = new byte[1024];
		// int len = -1;
		// while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
		// response.getOutputStream().write(b, 0, len);
		// }
	}
    
	@Test
	@Rollback(false)
	public void updateState() {
		
		ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
		
		ProcessDefinition pd = pdq.processDefinitionKey("leave").singleResult();
		
		repositoryService.suspendProcessDefinitionById(pd.getId(), true, null);
        
		repositoryService.activateProcessDefinitionByKey(pd.getKey());
		
		//repositoryService.activateProcessDefinitionById(pd, true, null);

		//repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);

	}
   
	
	
	/*@RequestMapping(value = "/convert-to-model/{processDefinitionId}")
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
    }*/
}
