package com.vishwakarma.vastu.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.model.UserInfo;
import com.vishwakarma.vastu.model.specifications.UserSpecifications;
import com.vishwakarma.vastu.repository.UserRepository;
import com.vishwakarma.vastu.utils.AppConstants;
import com.vishwakarma.vastu.utils.EncryptionUtil;
import com.vishwakarma.vastu.utils.Md5Util;
import com.vishwakarma.vastu.utils.NotificationHelper;
import com.vishwakarma.vastu.utils.RoleCache;

@Service
public class UserService {

	@Resource
	private UserRepository userRepository;
	
	@Autowired 
	private RoleCache roleCache;
	
	@Resource
	private NotificationHelper notificationHelper;
	
	@Transactional
	public User createUser(User user, User loggedInUser, boolean isRegister) {
		
		if(null == user) {
			throw new VastuException("Invalid user");
		}
		if(StringUtils.isBlank(user.getEmail())) {
			throw new VastuException("Invalid email");
		}
		String password = RandomStringUtils.randomAlphanumeric(AppConstants.PASSWORD_LENGTH);
		user.setPassword(Md5Util.md5(password));
		user.setIsEnabled(true);
		User existingUser = getUserByEmail(user.getEmail(), false);
		if(null != existingUser) {
			throw new VastuException("User with same email already exists");
		}
		user = userRepository.save(user);
		notificationHelper.sendNotificationForNewAccount(user, password);
		if(!isRegister) {
			return populateTransientFields(user, loggedInUser);
		}
		return user;
	}
	
	@Transactional
	public List<User> getAllUsers(User loggedInUser) {
		
		List<User> users = userRepository.findAll();
		for (User user : users) {
			user = populateTransientFields(user, loggedInUser);
		}
		return users;
	}
	
	@Transactional
	public Page<User> getUsers(Integer pageNumber, User loggedInUser) {
		
		Page<User> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, AppConstants.PAGE_SIZE, Direction.ASC, "id");
		page = userRepository.findAll(pageRequest);
		Iterator<User> iterator = page.iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			user = populateTransientFields(user, loggedInUser);
		}
		return page;
	}
	
	@Transactional
	public List<User> getUsersForRole(String role, User loggedInUser) {
		List<User> users = userRepository.findByRole(roleCache.getRole(role).getId());
		for (User user : users) {
			user = populateTransientFields(user, loggedInUser);
		}
		return users;
	}
	
	@Transactional
	public Page<User> searchUsers(Integer pageNumber, String searchTerm, User loggedInUser) {
		
		Page<User> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, AppConstants.PAGE_SIZE, Direction.ASC, "id");
		page = userRepository.findAll(UserSpecifications.search(searchTerm), pageRequest);
		Iterator<User> iterator = page.iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			user = populateTransientFields(user, loggedInUser);
		}
		return page;
	}
	
	public User getUser(Long id, boolean doPostValidation, User loggedInUser) {
		
		if(null == id) {
			throw new VastuException("Invalid user id");
		}
		User user = getUser(id, false);
		if(doPostValidation && null == user) {
			throw new VastuException("User with this Id does not exists");
		}
		return populateTransientFields(user, loggedInUser);
	}
	
	public User getSuperUser() {
		User user = userRepository.findByRole(roleCache.getRole(Role.ROLE_SUPER_ADMIN).getId()).get(0);
		return user;
	}

	/**
	 * returns @User without Post validation & without populating transient fields
	 * @param id
	 * @return
	 */
	public User getUser(Long id, boolean doPostValidation) {
		
		User user;
		if(null == id) {
			throw new VastuException("Invalid user id");
		}
		user = userRepository.findOne(id);
		if(doPostValidation && null == user) {
			throw new VastuException("User with this Id does not exists");
		}
		return user;
	}

	/**
	 * returns @User without Post validation & without populating transient fields
	 * @param email
	 * @return
	 */
	/*public User getUserByUserName(String email, boolean doPostValidation) {
		
		User user;
		if(StringUtils.isBlank(email)) {
			throw new vastuException("Invalid username");
		}
		user = userRepository.findByEmail(email);
		if(doPostValidation && null == user) {
			throw new vastuException("User with this email does not exists");
		}
		return user;
	}*/
	
	/**
	 * returns @User without Post validation & without populating transient fields
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email, boolean doPostValidation) {
		
		User user;
		if(StringUtils.isBlank(email)) {
			throw new VastuException("Invalid email");
		}
		user = userRepository.findByEmail(email);
		if(doPostValidation && null == user) {
			throw new VastuException("User with this email does not exists");
		}
		return user;
	}
	
	/**
	 * Search users by phone no search term
	 * @param doPostValidation
	 * @return
	 */
	public List<User> getUsersByPhoneNumber(Long userId,String searchTerm, boolean doPostValidation,User loggedInUser) {
		
		List<User> users = userRepository.findAll(Specifications.where(UserSpecifications.searchByPhoneNumber(userId,searchTerm)));
		for (User user : users) {
			user = populateTransientFields(user, loggedInUser);
		}
		return users;
	}
	
	/**
	 * 
	 * Updates the user. Do not updates email, password, roles & verification code
	 * 
	 * @param id - Id of existing user
	 * @param user - Updated User
	 * @param loggedInUser - LoggedIn User
	 * @return Updated User
	 * 
	 * @author Vishal
	 */
	public User updateUser(Long id, User user, User loggedInUser) {
		
		if(null == id) {
			throw new VastuException("Invalid user id");
		}
		User existingUser = userRepository.findOne(id);
		if(null == existingUser) {
			throw new VastuException("User with this Id does not exists");
		}
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setMobileNumber(user.getMobileNumber());
		existingUser.setIsEnabled(user.getIsEnabled());
		existingUser.setIsVerified(user.getIsVerified());
		existingUser.setDob(user.getDob());
		existingUser = userRepository.save(existingUser);
		existingUser.setCipher(EncryptionUtil.encode(user.getId()+""));
		return populateTransientFields(existingUser, loggedInUser);
	}
	
	public void deleteUser(Long id) {
		
		if(null == id) {
			throw new VastuException("Invalid user id");
		}
		User user = userRepository.findOne(id);
		if(null == user) {
			throw new VastuException("User with this Id does not exists");
		}
		userRepository.delete(id);
	}
	
	private User populateTransientFields(User user, User loggedInUser) {
		
		if(null == user) {
			return user;
		}
		if(null == loggedInUser) {
			throw new VastuException("Invalid user");
		}
		if(loggedInUser.hasRole(Role.ROLE_SUPER_ADMIN)) {
			user.setIsEditAllowed(true);
			user.setIsDeleteAllowed(true);
		} else {
			user.setIsEditAllowed(false);
			user.setIsDeleteAllowed(false);
		}
		user.setCipher(EncryptionUtil.encode(user.getId()+""));
		return user;
	}
	
	public boolean checkPassword(Long userId, String password) {
		User user = userRepository.findOne(userId);
		return Md5Util.md5(password).equals(user.getPassword());
	}
	
	public void changePassword(String newPassword) {
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findOne(userInfo.getId());
		if(null == user) {
			throw new VastuException("User not found");
		}
		user.setPassword(Md5Util.md5(newPassword));
		userRepository.save(user);
	}
	
	public String recoverPassword(Long userId) {
		
		User user = getUser(userId, true);
		String password = RandomStringUtils.randomAlphanumeric(AppConstants.PASSWORD_LENGTH);
		user.setPassword(Md5Util.md5(password));
		userRepository.save(user);
		return password;
	}
	
	public User getLoggedInUser() {
		
		UserInfo userInfo = getLoggedInUserInfo();
		if(null == userInfo) {
			throw new VastuException("Invalid logged-in user information");
		}
		User user = getUser(userInfo.getId(), false);
		return user;
	}

	/**
	 * @return
	 */
	public UserInfo getLoggedInUserInfo() {
		UserInfo userInfo;
		try {
			userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
			throw new VastuException("Error retrieving logged in user information");
		}
		return userInfo;
	}
}
