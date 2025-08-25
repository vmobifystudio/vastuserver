package com.vishwakarma.vastu.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.vishwakarma.vastu.repository.CustomRepository;

@NoRepositoryBean
public class CustomRepositoryImpl<T, ID extends Serializable> 
 extends SimpleJpaRepository<T, ID>  implements CustomRepository<T, ID> , Serializable{
  
	private static final long serialVersionUID = 1L;
 
	final static Logger logger = LoggerFactory.getLogger(CustomRepositoryImpl.class);
  
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
      
    private  Class<?> springDataRepositoryInterface; 
    
    public CustomRepositoryImpl (JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager , Class<?> springDataRepositoryInterface) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
        this.springDataRepositoryInterface = springDataRepositoryInterface;
        System.out.println("GenericRepositoryImpl - constructor");
    }
	
    public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getMetadata(domainClass, em), em, null);  
    }

	public Class<?> getSpringDataRepositoryInterface() {
    	return springDataRepositoryInterface;
    }

	public void setSpringDataRepositoryInterface(
			Class<?> springDataRepositoryInterface) {
		this.springDataRepositoryInterface = springDataRepositoryInterface;
	}
  
	public <S extends T> S save(S entity) {
		if (this.entityInformation.isNew(entity)) {
			this.em.persist(entity);
			flush();
			return entity;
		}
		entity = this.em.merge(entity);
		flush();
		return entity;
	}

	public T saveWithoutFlush(T entity) {
		return super.save(entity);
	}

	public List<T> saveWithoutFlush(Iterable<? extends T> entities) {
		List<T> result = new ArrayList<T>();
		if (entities == null) {
			return result;
		}

		for (T entity : entities) {
			result.add(saveWithoutFlush(entity));
		}
		return result;
	}
}
