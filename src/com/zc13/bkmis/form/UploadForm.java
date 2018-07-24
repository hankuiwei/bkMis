package com.zc13.bkmis.form;

import org.apache.struts.upload.FormFile;

public class UploadForm extends BasicForm {
  
	private FormFile file;

	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	
}
