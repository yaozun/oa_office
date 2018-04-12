package com.oa_office.activiti.util;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class SkipListenner implements ExecutionListener{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -4498710515476773703L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		 System.out.println("ddd");
		 // 获取流程变量
        //Map<String, Object> variables = execution.getVariables();
        // 开启支持跳过表达式 ActivitiConstanct.getSkipExpression()就是"_ACTIVITI_SKIP_EXPRESSION_ENABLED"
        //variables.put("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);
        // 将修改同步到流程中
       // execution.setTransientVariables(variables);
        // 这种方式也行。直接设置流程变量
         execution.setVariable("_ACTIVITI_SKIP_EXPRESSION_ENABLED",true);
	}

}
