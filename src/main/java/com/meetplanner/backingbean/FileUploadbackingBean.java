package com.meetplanner.backingbean;

import javax.faces.bean.ManagedBean;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploader")
public class FileUploadbackingBean {

	private UploadedFile file;

	public void handleFileUpload(FileUploadEvent event){
		System.out.println(event.getFile().getFileName()+" type "+event.getFile().getContentType());
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
