package com.vishwakarma.vastu.model;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.io.FileUtils;

@Entity
@Table(name= "Product", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
public class Product extends BaseEntity {

	private String code;
	
	private String productName;
	
	private double price;
	
	@Column(columnDefinition="text default null")
	private String description;

	private String productPicUrl;
	
	@Transient
	private byte[] productPicData;
	
	@Transient
	private String fileName;
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isKitchenroomScore;
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isBathRoomScore;
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isBedroomScore;
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isHallScore;
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isGalleryScore;
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isDirectioncutScore;  
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isEnterenceScore;  
	
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isWindowScore;  
	
	@PostPersist
	public void createProductPicFile() {
		if (null != productPicData) {
			try {
				File productPic = getProductPicFile();
				FileUtils.writeByteArrayToFile(productPic, productPicData);
				setProductPicUrl(productPic.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setCode("P-" + getId());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getProductPicData() {
		return productPicData;
	}

	public void setProductPicData(byte[] productPicData) {
		this.productPicData = productPicData;
	}

	public String getProductPicUrl() {
		return productPicUrl;
	}

	public void setProductPicUrl(String productPicUrl) {
		this.productPicUrl = productPicUrl;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public boolean getIsKitchenroomScore() {
		return isKitchenroomScore;
	}

	public void setIsKitchenroomScore(boolean isKitchenroomScore) {
		this.isKitchenroomScore = isKitchenroomScore;
	}

	public boolean getIsBathRoomScore() {
		return isBathRoomScore;
	}

	public void setIsBathRoomScore(boolean isBathRoomScore) {
		this.isBathRoomScore = isBathRoomScore;
	}

	public boolean getIsBedroomScore() {
		return isBedroomScore;
	}

	public void setIsBedroomScore(boolean isBedroomScore) {
		this.isBedroomScore = isBedroomScore;
	}

	public boolean getIsHallScore() {
		return isHallScore;
	}

	public void setIsHallScore(boolean isHallScore) {
		this.isHallScore = isHallScore;
	}

	public boolean getIsGalleryScore() {
		return isGalleryScore;
	}

	public void setIsGalleryScore(boolean isGalleryScore) {
		this.isGalleryScore = isGalleryScore;
	}

	public boolean getIsDirectioncutScore() {
		return isDirectioncutScore;
	}

	public void setIsDirectioncutScore(boolean isDirectioncutScore) {
		this.isDirectioncutScore = isDirectioncutScore;
	}

	public boolean getIsEnterenceScore() {
		return isEnterenceScore;
	}

	public void setIsEnterenceScore(boolean isEnterenceScore) {
		this.isEnterenceScore = isEnterenceScore;
	}

	public boolean getIsWindowScore() {
		return isWindowScore;
	}

	public void setIsWindowScore(boolean isWindowScore) {
		this.isWindowScore = isWindowScore;
	}

	private File getProductPicFile() {
		File baseDir = new File(System.getProperty("catalina.base"),
				"productsPics");
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			baseDir.mkdir();
		}
		File productPic = new File(baseDir, this.getId() + "_"+ this.getFileName());
		if(productPic.exists() && !productPic.isDirectory()) {
			productPic.delete();
		}
		return productPic;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Product)) {
			return super.equals(obj);
		}
		Product product = (Product) obj;
		
		if(getId() == null) {
			return super.equals(obj);
		}
		return getId().equals(product.getId());
	}
	
	@Override
	public int hashCode() {
		
		if(null == getId()) {
			return super.hashCode();
		}
		return getId().hashCode();
	}
}
