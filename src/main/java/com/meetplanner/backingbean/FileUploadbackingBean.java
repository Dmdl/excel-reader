package com.meetplanner.backingbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.meetplanner.dto.GroupDTO;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.util.Reader;
import com.meetplanner.util.SpringApplicationContex;

public class FileUploadbackingBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Reader reader;
	private UploadedFile file;
	private static final String FILE_SAVE_LOCATION="E:\\doc\\upload\\";
	private Map<Integer, String> allGroups=new HashMap<Integer, String>();
	private FileUploadService fileUploadService;
	private String selectedGroup;

	public FileUploadbackingBean(){
		try{		
			fileUploadService=(FileUploadService)SpringApplicationContex.getBean("fileUploadService");
			System.out.println("fileUploadService :::::: "+fileUploadService);
			List<GroupDTO> groups = fileUploadService.getAllGroups();
			if(groups.size()>0){
				for(GroupDTO e:groups){
					allGroups.put(e.getId(), e.getName());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void handleFileUpload(FileUploadEvent event){
		System.out.println("file upload....");
		System.out.println(event.getFile().getFileName()+" type "+event.getFile().getContentType());
		String contentType=event.getFile().getContentType();
		if("application/vnd.ms-excel".equalsIgnoreCase(contentType) || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(contentType) || "application/kset".equals(contentType)){
			OutputStream outputStream=null;
			InputStream inputStream=null;
			try{
				outputStream=new FileOutputStream(new File(FILE_SAVE_LOCATION+event.getFile().getFileName()));
				inputStream = event.getFile().getInputstream();
				byte[] bytes = new byte[1024];
				int read = 0;
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				String filePath=FILE_SAVE_LOCATION+event.getFile().getFileName();
				System.out.println("filePath:::::::::::: "+filePath);
				reader.read(filePath);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File Upload successful", "File Upload successful"));
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Processing File", "Error Processing File"));
			}finally{
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}		 
				}
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not a Valid Excel File", "Not a Valid Excel File"));
		}
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public Map<Integer, String> getAllGroups() {
		return allGroups;
	}

	public void setAllGroups(Map<Integer, String> allGroups) {
		this.allGroups = allGroups;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

}
