package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;

import com.itelasoft.pso.beans.BlobFileData;
import com.itelasoft.pso.beans.EnumFileType;
import com.itelasoft.pso.beans.FileData;
import com.itelasoft.util.ImageUtil;

@ManagedBean(name = "imageUploadModel")
@SessionScoped
public class ImageUploadModel extends UIModel {
	private boolean visibleImageUpload;

	public void uploadStudentImage(FileEntryEvent event) {
		StudentManagerModel model = (StudentManagerModel) Util
				.getManagedBean("studentManagerModel");
		FileEntry fileEntry = (FileEntry) event.getSource();
		FileEntryResults results = fileEntry.getResults();
		for (FileInfo fileInfo : results.getFiles()) {
			if (isValidFile(fileInfo) && fileInfo.isSaved()) {
				if (fileInfo.getFile() != null) {
					FileData fileData;
					if (model.getStudent().getPhoto() != null)
						fileData = model.getStudent().getPhoto();
					else
						fileData = new FileData();
					fileData.setFileType(EnumFileType.STUDENT_IMAGE);
					fileData.setStudentId(model.getStudent().getId());
					model.getStudent()
							.setPhoto(setFileInfo(fileData, fileInfo));
					model.initPhotoImage();
					imagePopup();
					model.saveStudent();
				} else
					showError("File not supported");
			}
		}
	}

	public void uploadStaffImage(FileEntryEvent event) {
		StaffManagerModel model = (StaffManagerModel) Util
				.getManagedBean("staffManagerModel");
		FileEntry fileEntry = (FileEntry) event.getSource();
		FileEntryResults results = fileEntry.getResults();
		for (FileInfo fileInfo : results.getFiles()) {
			if (isValidFile(fileInfo) && fileInfo.isSaved()) {
				if (fileInfo.getFile() != null) {
					FileData fileData;
					if (model.getStaffMember().getPhoto() != null)
						fileData = model.getStaffMember().getPhoto();
					else
						fileData = new FileData();
					fileData.setFileType(EnumFileType.STAFF_IMAGE);
					fileData.setStaffId(model.getStaffMember().getId());
					model.getStaffMember().setPhoto(
							setFileInfo(fileData, fileInfo));
					model.initPhotoImage();
					model.saveStaff();
					imagePopup();
					//showInfo("Please save Staff Member..");
				} else{
					showError("File not supported");
				}
			}
		}
	}

	public void uploadVehicleImage(FileEntryEvent event) {
		VehicleManagerModel model = (VehicleManagerModel) Util
				.getManagedBean("vehicleManagerModel");
		FileEntry fileEntry = (FileEntry) event.getSource();
		FileEntryResults results = fileEntry.getResults();
		for (FileInfo fileInfo : results.getFiles()) {
			if (isValidFile(fileInfo) && fileInfo.isSaved()) {
				if (fileInfo.getFile() != null) {
					FileData fileData;
					if (model.getVehicle().getPhoto() != null)
						fileData = model.getVehicle().getPhoto();
					else
						fileData = new FileData();
					fileData.setFileType(EnumFileType.VEHICLE_IMAGE);
					fileData.setVehicleId(model.getVehicle().getId());
					model.getVehicle()
							.setPhoto(setFileInfo(fileData, fileInfo));
					//model.initPhotoImage();
					imagePopup();
					model.saveVehicle();
					//showInfo("Please save Vehicle..");
				} else
					showError("File not supported");
			}
		}
	}

	public void uploadGroupImage(FileEntryEvent event) {
		ProgramManagerModel model = (ProgramManagerModel) Util
				.getManagedBean("programManagerModel");
		FileEntry fileEntry = (FileEntry) event.getSource();
		FileEntryResults results = fileEntry.getResults();
		for (FileInfo fileInfo : results.getFiles()) {
			if (isValidFile(fileInfo) && fileInfo.isSaved()) {
				if (fileInfo.getFile() != null) {
					FileData fileData;
					if (model.getStudentGroup().getPhoto() != null)
						fileData = model.getStudentGroup().getPhoto();
					else
						fileData = new FileData();
					fileData.setFileType(EnumFileType.STUDENT_GROUP_IMAGE);
					//fileData.setVehicleId(model.getStudentGroup().getId());
					model.getStudentGroup().setPhoto(
							setFileInfo(fileData, fileInfo));
					model.initPhotoImage();
					imagePopup();
					model.saveStudentGroup();
					showInfo("Please save " + Util.getMessage("student_label") + " Group..");
				} else
					showError("File not supported");
			}
		}
	}

	public void uploadSNeedIcon(FileEntryEvent event) {
		SNeedManagerModel model = (SNeedManagerModel) Util
				.getManagedBean("sNeedManagerModel");
		FileEntry fileEntry = (FileEntry) event.getSource();
		FileEntryResults results = fileEntry.getResults();
		for (FileInfo fileInfo : results.getFiles()) {
			if (isValidFile(fileInfo) && fileInfo.isSaved()) {
				if (fileInfo.getFile() != null) {
					FileData fileData;
					if (model.getsNeed().getIcon() != null)
						fileData = model.getsNeed().getIcon();
					else
						fileData = new FileData();
					fileData.setFileType(EnumFileType.SPECIALNEED_ICON);
					fileData.setSNeedId(model.getsNeed().getId());
					model.getsNeed().setIcon(setFileInfo(fileData, fileInfo));
					model.initPhotoImage();
					imagePopup();
					ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			        try {
			        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			       } catch (IOException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			     }
					model.saveSNeed();
				} else
					showError("File not supported");
			}
		}
	}

	public byte[] setData(FileInfo currentFile) {
		try {
			if (currentFile.getFile() != null) {
				FileInputStream fileInputStream = new FileInputStream(
						currentFile.getFile());
				byte[] temp = new byte[1000];
				int ret = 0;
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
						(int) currentFile.getSize());
				while ((ret = fileInputStream.read(temp)) != -1) {
					outputStream.write(temp, 0, ret);
				}
				fileInputStream.close();
				return outputStream.toByteArray();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private FileData setFileInfo(FileData fileData, FileInfo fileInfo) {
		fileData.setFileName(fileInfo.getFileName());
		fileData.setContentType(fileInfo.getContentType());
		if(fileData.getBlobFileData()==null)
			fileData.setBlobFileData(new BlobFileData());
		fileData.getBlobFileData().setData(setData(fileInfo));
		fileData.setDateCreated(new Date());
		try {
			BufferedImage buffImg = ImageIO.read(new File(fileInfo.getFile()
					.toString()));
			int width = buffImg.getWidth();
			int height = buffImg.getHeight();
			if (width > 150 || height > 150) {
				fileData.getBlobFileData().setThumbnail(ImageUtil.resizeJpeg(buffImg));
			} else {
				fileData.getBlobFileData().setThumbnail(setData(fileInfo));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData;
	}

	public void imagePopup() {
		setVisibleImageUpload(!visibleImageUpload);
	}

	public void setVisibleImageUpload(boolean visibleImageUpload) {
		this.visibleImageUpload = visibleImageUpload;
	}

	public boolean isVisibleImageUpload() {
		return visibleImageUpload;
	}
	
	
	// this is for file validation of an image and currently it's using only for uploadStaffImage method.
	private boolean isValidFile(FileInfo fileInfo){
		
		String fileName = fileInfo.getFileName();
		if(!fileName.isEmpty() && !fileName.equals("")){
			try {
				String extention[] = fileName.split("\\.");
				if(extention.length<3){
					 if(extention[1].equalsIgnoreCase("png") || extention[1].equalsIgnoreCase("jpg") ){
						 return true;
					 }else{
						 showError("Invalid file extention");
					 }
				}else{
					showError("Invalid file name");
				}
			} catch (PatternSyntaxException  e) {
				showError("Cannot find the file extection");
			}
		}else{
			showError("Please select an Image to be uploaded");
		}
		return false;
	}
	
}
