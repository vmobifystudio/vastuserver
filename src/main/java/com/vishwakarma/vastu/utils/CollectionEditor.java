package com.vishwakarma.vastu.utils;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.data.repository.CrudRepository;

import com.vishwakarma.vastu.model.BaseEntity;

public class CollectionEditor extends CustomCollectionEditor{

	private  CrudRepository<? extends BaseEntity, Long> repo;
	
	public CollectionEditor(Class collectionType,CrudRepository<? extends BaseEntity, Long> repo) {
		super(collectionType);
		this.repo = repo;
	}
	
	@Override
    protected Object convertElement(Object element)
    {
        Long id = null;

        if(element instanceof String && !((String)element).equals("")){
            //From the JSP 'element' will be a String
            try{
                id = Long.parseLong((String) element);
            }
            catch (NumberFormatException e) {
                System.out.println("Element was " + ((String) element));
                e.printStackTrace();
            }
        }
        else if(element instanceof Long) {
            //From the database 'element' will be a Long
            id = (Long) element;
        }

        return id != null ? repo.findOne(id) : null;
    }

}
