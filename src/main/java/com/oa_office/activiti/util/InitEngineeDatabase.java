package com.oa_office.activiti.util;

import org.activiti.engine.ProcessEngineConfiguration;

public class InitEngineeDatabase {
	public static void main(String[] args) {
        ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
    }

}
