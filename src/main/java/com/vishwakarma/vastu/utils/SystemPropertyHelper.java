package com.vishwakarma.vastu.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vishwakarma.vastu.model.SystemProperty;
import com.vishwakarma.vastu.repository.SystemPropertyRepository;

@Component
public class SystemPropertyHelper {

	@Resource
	private SystemPropertyRepository systemPropertyRepository;
	
	@PostConstruct
	public void setApiKey() {
		SystemProperty sysProp = systemPropertyRepository.findByPropName(SystemProperty.API_KEY);
		if (sysProp == null) {
			sysProp = new SystemProperty();
			sysProp.setPropName(SystemProperty.API_KEY);
			sysProp.setPropValue(RandomStringUtils.randomAlphanumeric(16));
			systemPropertyRepository.save(sysProp);
		}
	}
	
	public boolean validateApiKey(String apiKey) {
		SystemProperty sysProp = systemPropertyRepository.findByPropName(SystemProperty.API_KEY);
		if (sysProp!=null) {
			return apiKey.equals(sysProp.getPropValue());
		}
		else {
			return false;
		}
	} 
	
	@Transactional
	public void incrementMasterDataVersion() {
		SystemProperty versionProp = systemPropertyRepository.findByPropName(SystemProperty.MASTER_DATA_VERSION);
		versionProp.setPropValue(Integer.parseInt(versionProp.getPropValue())+1+"");
		systemPropertyRepository.save(versionProp);
	}
	
	public String getSystemProperty(String property, String defaultVal) {
		SystemProperty prop = systemPropertyRepository.findByPropName(property);
		return prop!=null ? prop.getPropValue() : defaultVal;
	}
	
	public void setSystemProperty(String propertyName, String propertyValue) {
		SystemProperty prop = systemPropertyRepository.findByPropName(propertyName);
		if (null!=prop) {
			prop.setPropValue(propertyValue);			
		}
		else {
			prop = new SystemProperty();
			prop.setPropName(propertyName);
			prop.setPropValue(propertyValue);
		}
		systemPropertyRepository.save(prop);
	}
	
	public Boolean hasSystemProperty(String property) {
		SystemProperty prop = systemPropertyRepository.findByPropName(property);
		return prop!=null ? true : false;
	}
	
	public Page<SystemProperty> getSystemProperties(Integer pageNumber) {
		PageRequest request =
	            new PageRequest(pageNumber -1, AppConstants.PAGE_SIZE, Sort.Direction.DESC, "id");
		//UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return systemPropertyRepository.findAll(request);		
	}
	
	public SystemProperty getSystemProperty(String propName) {
		//UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return systemPropertyRepository.findByPropName(propName);
	}
	
	public boolean getBooleanProperty(String property, boolean defaultVal) {
		SystemProperty prop = systemPropertyRepository.findByPropName(property);
		return prop!=null ? Boolean.parseBoolean(prop.getPropValue()) : defaultVal;
	}
}
