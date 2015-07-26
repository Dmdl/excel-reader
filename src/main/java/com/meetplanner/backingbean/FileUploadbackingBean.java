package com.meetplanner.backingbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meetplanner.util.Reader;

@Component
@ManagedBean
@RequestScoped
public class FileUploadbackingBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private Reader reader;
	private UploadedFile file;
	private static final String FILE_SAVE_LOCATION="E:\\doc\\upload\\";

	public FileUploadbackingBean(){
		//reader=new ExcelReader();
	}
	
	public void handleFileUpload(FileUploadEvent event){
		System.out.println("file upload....");
		System.out.println(event.getFile().getFileName()+" type "+event.getFile().getContentType());
		String contentType=event.getFile().getContentType();
		if("application/vnd.ms-excel".equalsIgnoreCase(contentType) || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(contentType)){
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

}
