package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.CAccounttemplateForm;
import com.zc13.bkmis.mapping.ELp;

public interface ICAccounttemplateService extends IBasicService {
	
	public List getCAccounttemplateList(CAccounttemplateForm account) throws Exception ;
	
	public void saveCAccounttemplate(CAccounttemplateForm items) throws Exception ;
	
	public void deleteCAccounttemplate(CAccounttemplateForm items) throws Exception ;
	
	public List<ELp> getLpList(CAccounttemplateForm account) throws Exception;

}
