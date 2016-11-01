package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.imageio.ImageIO;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.SpecialNeed;

@ManagedBean(name = "sNeedManagerModel")
@SessionScoped
public class SNeedManagerModel extends UIModel {

	private List<SpecialNeed> allSNeeds;
	private int photoH, photoW;
	private SpecialNeed sNeed, tmpSNeed;
	
	public SNeedManagerModel() {
		init();
	}

	public void init() {
		allSNeeds = serviceLocator.getSpecialNeedService().findAll();
		sNeed = tmpSNeed = null;
	}

	public void selectSneed(ClickActionEvent re) {
		if (tmpSNeed != null)
			tmpSNeed.setUi_selected(false);
		tmpSNeed = (SpecialNeed) re.getComponent().getAttributes().get("sNeed");
		tmpSNeed.setUi_selected(true);
		sNeed = serviceLocator.getSpecialNeedService().retrieveEager(
				tmpSNeed.getId());
		initPhotoImage();
	}

	public void initPhotoImage() {
		byte[] tmpData = null;
		if (sNeed.getIcon() != null) {
			tmpData = sNeed.getIcon().getBlobFileData().getData();
			if (tmpData != null) {
				BufferedImage loadImg;
				try {
					loadImg = ImageIO.read(new ByteArrayInputStream(tmpData));
					int w = loadImg.getWidth();
					int h = loadImg.getHeight();
					if (w > h) {
						photoW = 150;
						photoH = photoW * h / w;
					} else {
						photoH = 100;
						photoW = photoH * w / h;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addNewSneed() {
		if (tmpSNeed != null)
			tmpSNeed.setUi_selected(false);
		sNeed = new SpecialNeed();
	}
	
	public void saveSNeed() {
		if (sNeed.getName() != null && !sNeed.getName().isEmpty()) {
			if (sNeed.getId() == null) {
				sNeed = serviceLocator.getSpecialNeedService().create(sNeed);
				tmpSNeed = sNeed;
				sNeed = serviceLocator.getSpecialNeedService().retrieve(
						tmpSNeed.getId()); // for not to update table data if
											// user have done any changes to
											// Special need
				tmpSNeed.setUi_selected(true);
				allSNeeds.add(tmpSNeed);
				showInfo("Special need added successfully..");
			} else {
				sNeed = serviceLocator.getSpecialNeedService().update(sNeed);
				sNeed.setUi_selected(true);
				allSNeeds.set(allSNeeds.indexOf(tmpSNeed), sNeed);
				tmpSNeed = null;
				tmpSNeed = sNeed;
				sNeed = serviceLocator.getSpecialNeedService().retrieve(
						tmpSNeed.getId()); // for not to update table data if
											// user have done any changes to
											// Special Need
				showInfo("Special need updated successfully..");
			}
		} else
			showError("Special Need Name can not be empty..");
		  
	}

	public void deleteSNeed() {
		try {
			serviceLocator.getSpecialNeedService().delete(sNeed.getId());
			allSNeeds.remove(tmpSNeed);
			sNeed = tmpSNeed = null;
			showInfo("Special need deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	/*
	 * getters and setters
	 */

	public List<SpecialNeed> getAllSNeeds() {
		return allSNeeds;
	}

	public SpecialNeed getsNeed() {
		return sNeed;
	}

	public void setsNeed(SpecialNeed sNeed) {
		this.sNeed = sNeed;
	}

	public int getPhotoH() {
		return photoH;
	}

	public int getPhotoW() {
		return photoW;
	}

}
