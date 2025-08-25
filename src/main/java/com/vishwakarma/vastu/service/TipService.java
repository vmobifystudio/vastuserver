package com.vishwakarma.vastu.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.model.Tip;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.model.specifications.TipSpecification;
import com.vishwakarma.vastu.repository.TipRepository;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Service
public class TipService {
	
	private static final int PAGE_SIZE = 10;
	
	@Resource
	private TipRepository tipRepository;
	
	@Transactional
	public Tip createTip(Tip tip, User loggedInUser, boolean fetchEagerly) {
		
		if(null == tip) {
			throw new VastuException("Invalid tip");
		}
		tip= tipRepository.save(tip);
		if(fetchEagerly){
			tip = fetchEagerly(tip);
		}
		return tip;
	}
	
	@Transactional
	public Page<Tip> getTips(Integer pageNumber, User loggedInUser, boolean fetchEagerly) {
		
		Page<Tip> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = tipRepository.findAll(pageRequest);
		Iterator<Tip> iterator = page.iterator();
		while (iterator.hasNext()) {
			Tip tip = (Tip) iterator.next();
			if(fetchEagerly){
				tip = fetchEagerly(tip);
			}
			tip = populateTransientFields(tip, loggedInUser);
		}
		return page;
	}
	
	@Transactional
	public Page<Tip> getTips(Integer pageNumber, boolean fetchEagerly) {
		
		Page<Tip> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = tipRepository.findAll(pageRequest);
		Iterator<Tip> iterator = page.iterator();
		while (iterator.hasNext()) {
			Tip tip = (Tip) iterator.next();
			if(fetchEagerly){
				tip = fetchEagerly(tip);
			}
			tip = populateTransientFields(tip);
		}
		return page;
	}
	
	@Transactional
	public List<Tip> getTip(){
		List<Tip> tips = tipRepository.findAll();
		return tips;
	}
	
	@Transactional
	public Page<Tip> searchTips(Integer pageNumber,String searchTerm, User loggedInUser,boolean fetchEagerly) {
		
		Page<Tip> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = tipRepository.findAll(TipSpecification.search(searchTerm), pageRequest);
		Iterator<Tip> iterator = page.iterator();
		while (iterator.hasNext()) {
			Tip tip = (Tip) iterator.next();

			if(fetchEagerly){
				tip = fetchEagerly(tip);
			}
			
			tip = populateTransientFields(tip, loggedInUser);
		}
		return page;
	}
	
	
	@Transactional
	public Tip getTip(Long id, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid tip id");
		}
		Tip tip = tipRepository.findOne(id);
		if(doPostValidation && null == tip) {
			throw new VastuException("tip with this Id does not exists");
		} else if(null == tip) {
			return tip;
		}
		if(fetchEagerly){
			tip = fetchEagerly(tip);
		}
		if(populateTransientFields) {
			return populateTransientFields(tip, loggedInUser);
		}
		return tip;
	}
	
	@Transactional
	public Tip getTipByCode(String code, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(code)) {
			throw new VastuException("Invalid tip code");
		}
		Tip tip = tipRepository.findByCode(code);
		if(doPostValidation && null == tip) {
			throw new VastuException("tip with this code does not exists");
		}
		if(fetchEagerly){
			tip = fetchEagerly(tip);
		}
		if(populateTransientFields) {
			return populateTransientFields(tip, loggedInUser);
		}
		return tip;
	}
	
	@Transactional
	public Tip getTipByTitle(String tipTitle, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(tipTitle)) {
			throw new VastuException("Invalid tip tipTitle");
		}
		Tip tip = tipRepository.findByTipTitle(tipTitle);
		if (doPostValidation && tip == null) {
			throw new VastuException("tip with this name does not exist");
		} else if (tip == null) {
			return tip;
		}
		return tip;
	}
	
	@Transactional
	public Tip updateTip(Long id, Tip tip, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid tip id");
		}
		
		Tip existingtip = tipRepository.findOne(id);
		
		if(null == existingtip) {
			throw new VastuException("tip with this Id does not exists");
		}
		
		existingtip.setTipDescription(tip.getTipDescription());
		existingtip.setTipTitle(tip.getTipTitle());
		
		existingtip = tipRepository.save(existingtip);
		if(fetchEagerly){
			existingtip = fetchEagerly(existingtip);
		}
		if(populateTransientFields) {
			return populateTransientFields(existingtip, loggedInUser);
		}	
		return existingtip;
	}
	
	public void deletetip(Long id) {
		
		if(null == id) {
			throw new VastuException("Invalid tip id");
		}
		Tip tip = tipRepository.findOne(id);
		if(null == tip) {
			throw new VastuException("tip with this Id does not exists");
		}
		tipRepository.delete(id);
	}
		
	private Tip populateTransientFields(Tip tip, User loggedInUser) {
		
		if(null == tip) {
			return tip;
		}
		if(null == loggedInUser) {
			throw new VastuException("Invalid tip");
		}
		if(loggedInUser.hasRole(Role.ROLE_SUPER_ADMIN)) {
			tip.setIsEditAllowed(true);
			tip.setIsDeleteAllowed(true);
		} else {
			tip.setIsEditAllowed(false);
			tip.setIsDeleteAllowed(false);
		}
		tip.setCipher(EncryptionUtil.encode(tip.getId()+""));
		return tip;
	}
	
	private Tip populateTransientFields(Tip tip) {
		
		if(null == tip) {
			return tip;
		}
		
		tip.setIsEditAllowed(false);
		tip.setIsDeleteAllowed(false);
		tip.setCipher(EncryptionUtil.encode(tip.getId()+""));
		return tip;
	}
	
	
	private Tip fetchEagerly(Tip tip) {
		
		return tip;
		
	}
}
