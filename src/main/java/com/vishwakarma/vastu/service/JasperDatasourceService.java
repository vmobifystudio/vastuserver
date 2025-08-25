package com.vishwakarma.vastu.service;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.repository.UserRepository;

@Service
public class JasperDatasourceService {

	@Autowired
	private UserRepository repository;
	
	/**
	 * Returns a data source that's wrapped within {@link JRDataSource}
	 * @return
	 */
	public JRDataSource getDataSource() {
		List<User> records = repository.findAll();
		return new JRBeanCollectionDataSource(records);
	}
}
