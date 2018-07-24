package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICompactManagerDAO;
import com.zc13.bkmis.dao.ICustomerRoomDAO;
import com.zc13.bkmis.form.CompactManagerForm;
import com.zc13.bkmis.mapping.AnalysisClientComeGo;
import com.zc13.bkmis.mapping.CAdvance;
import com.zc13.bkmis.mapping.CDeposit;
import com.zc13.bkmis.mapping.CEarnest;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.CompactIntention;
import com.zc13.bkmis.mapping.CompactQuit;
import com.zc13.bkmis.mapping.CompactRelet;
import com.zc13.bkmis.mapping.CompactRent;
import com.zc13.bkmis.mapping.CompactRoomCoststandard;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.service.ICompactManagerService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * @author 秦彦韬
 * Date：Dec 16, 2010
 * Time：5:15:20 PM
 */
public class CompactManagerServiceImpl extends BasicServiceImpl implements ICompactManagerService {

	private ICompactManagerDAO iCompactManagerDAO;
	
	private ICustomerRoomDAO iCustomerRoomDAO;
	
	public ICustomerRoomDAO getICustomerRoomDAO() {
		return iCustomerRoomDAO;
	}

	public void setICustomerRoomDAO(ICustomerRoomDAO customerRoomDAO) {
		iCustomerRoomDAO = customerRoomDAO;
	}

	public ICompactManagerDAO getICompactManagerDAO() {
		return iCompactManagerDAO;
	}

	public void setICompactManagerDAO(ICompactManagerDAO compactManagerDAO) {
		iCompactManagerDAO = compactManagerDAO;
	}

	//查询合同列表
	@Override
	public List<Compact> getCompactList(CompactManagerForm form) throws BkmisServiceException {
		
		List<Compact> list = iCompactManagerDAO.getCompactList(form);
		form.setList(list);
		return list;
	}

	//得到合同总数
	@Override
	public int getCount(CompactManagerForm form) throws BkmisServiceException {
		
		List<Compact> list = iCompactManagerDAO.getCount(form);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}

	//查询合同续租列表
	@Override
	public List<CompactRelet> getContractList(CompactManagerForm form)
			throws BkmisServiceException {

		List<CompactRelet> list = iCompactManagerDAO.getContractList(form);
		return list;
	}

	//查询合同续租总数
	@Override
	public int getContractCount(CompactManagerForm form)
			throws BkmisServiceException {

		List<CompactRelet> list = iCompactManagerDAO.getContractCount(form);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}
	
	//查询合同退租列表
	@Override
	public List<CompactQuit> getDelContractList(CompactManagerForm form)
			throws BkmisServiceException {

		List<CompactQuit> list = iCompactManagerDAO.getDelContractList(form);
		return list;
	}
	
	//查询合同退租总数
	@Override
	public int getDelContractCount(CompactManagerForm form)
			throws BkmisServiceException {

		List<CompactQuit> list = iCompactManagerDAO.getDelContractCount(form);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}

	//根据合同id得到合同信息
	@Override
	public Compact getCompactById(String compactId)
			throws BkmisServiceException {
		
		int id = Integer.parseInt(compactId);
		Compact compact = (Compact)iCompactManagerDAO.getObject(Compact.class, id);
		return compact;
	}

	//保存退租信息
	@Override
	public void saveQuit(CompactManagerForm form)
			throws BkmisServiceException {
		
		CompactQuit quit = new CompactQuit();
		try {
			BeanUtils.copyProperties(quit, form);
			quit.setApplayDate(GlobalMethod.getTime());
			quit.setCompact((Compact)iCompactManagerDAO.getObject(Compact.class,Integer.parseInt(form.getCompactId())));
			//下面的设置审批状态的步骤放到了通知审批后面
			//quit.setStatus(Contants.NOTSUBMIT);
			//iCompactManagerDAO.updateTask("collateQuit", 1);
			iCompactManagerDAO.saveObject(quit);
			//将合同的入驻状态置为已申请迁出。
			iCompactManagerDAO.updateAColumn("Compact", "IsNotice", Contants.HASAPPLYGO,"id",form.getCompactId());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	//得到合同退租的信息
	@Override
	public Object[] goEditQuit(int id) throws BkmisServiceException {
		
		CompactQuit quit = (CompactQuit)iCompactManagerDAO.getObject(CompactQuit.class, id);
		Compact compact = (Compact)iCompactManagerDAO.getObject(Compact.class,quit.getCompact().getId());
		Object[] objects = new Object[]{quit,compact};
		return objects;
	}

	//编辑合同退租
	@Override
	public void editQuit(CompactManagerForm form) throws BkmisServiceException {
		
		CompactQuit quit = (CompactQuit)iCompactManagerDAO.getObject(CompactQuit.class,form.getId());
		try {
			BeanUtils.copyProperties(quit, form);
			quit.setCompact((Compact)iCompactManagerDAO.getObject(Compact.class,Integer.parseInt(form.getCompactId())));
			//quit.setStatus(Contants.NOTSUBMIT);
			iCompactManagerDAO.updateObject(quit);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	//得到需要审批的合同列表
	@Override
	public String getPassList(CompactManagerForm form) throws BkmisServiceException {
		
		String forword = null;
		String flag = form.getType();
		if(flag==null||"".equals(flag)){
			flag = Contants.INTENTION;
		}
		if(Contants.INTENTION.equals(flag)){//意向书
			form.setType(Contants.INTENTION);
			form.setStatus(Contants.ONAPPROVAL);
			List<CompactIntention> list = iCompactManagerDAO.getIntentionList(form);
			form.setCompactIntentions(list);
			forword = "pass0";
		}else if(Contants.COMPACT.equals(flag)){
			form.setType(Contants.COMPACT);
			form.setStatus(Contants.ONAPPROVAL);
			List<Compact> list = iCompactManagerDAO.getCompactList(form);
			form.setList(list);
			forword = "pass1";
		}
		if(Contants.COMPACTRELET.equals(flag)){
			form.setType(Contants.COMPACTRELET);
			form.setStatus(Contants.ONAPPROVAL);
			List<CompactRelet> list = iCompactManagerDAO.getContractList(form);
			form.setCompactRelets(list);
			forword = "pass2";
		}
		if(Contants.COMPACTCHANGE.equals(flag)){
			form.setType(Contants.COMPACTCHANGE);
			form.setStatus(Contants.ONAPPROVAL);
			List<CompactChange> list = iCompactManagerDAO.getCompactChangeList(form);
			form.setCompactChanges(list);
			forword = "pass4";
		}
		if(Contants.COMPACTDEAL.equals(flag)){
			form.setType(flag);
			form.setStatus(Contants.ONAPPROVAL);
			List<CompactQuit> list = iCompactManagerDAO.getDelContractList(form);
			form.setCompactQuits(list);
			forword = "pass3";
		}
		return forword;
	}
	
	//得到需要校验的合同列表
	@Override
	public String getCheckList(CompactManagerForm form) throws BkmisServiceException {
		
		String forword = null;
		String type = form.getType();
		if(type==null||"".equals(type)){
			type = Contants.INTENTION;
		}
		if(Contants.INTENTION.equals(type)){//意向书
			form.setType(Contants.INTENTION);
			form.setStatus(Contants.NOTSUBMIT);
			List<CompactIntention> list = iCompactManagerDAO.getIntentionList(form);
			form.setCompactIntentions(list);
			forword = "check0";
		}else if(Contants.COMPACT.equals(type)){//租赁合同
			form.setType(Contants.COMPACT);
			form.setStatus(Contants.NOTSUBMIT);
			List<Compact> list = iCompactManagerDAO.getCompactList(form);
			form.setList(list);
			forword = "check1";
		}else if(Contants.COMPACTRELET.equals(type)){//续租合同
			form.setType(Contants.COMPACTRELET);
			form.setStatus(Contants.NOTSUBMIT);
			List<CompactRelet> list = iCompactManagerDAO.getContractList(form);
			form.setCompactRelets(list);
			forword = "check2";
		}else if(Contants.COMPACTCHANGE.equals(type)){//变更合同
			form.setType(type);
			form.setStatus(Contants.NOTSUBMIT);
			List<CompactChange> list = iCompactManagerDAO.getCompactChangeList(form);
			form.setCompactChanges(list);
			forword = "check4";
		}else if(Contants.COMPACTDEAL.equals(type)){//退租合同
			form.setType(type);
			form.setStatus(Contants.NOTSUBMIT);
			List<CompactQuit> list = iCompactManagerDAO.getDelContractList(form);
			form.setCompactQuits(list);
			forword = "check3";
		}
		return forword;
	}
 
	//得到合同变更总数
	@Override
	public int getCompactChangeCount(CompactManagerForm form)
			throws BkmisServiceException {

		List<CompactChange> list = iCompactManagerDAO.getCompactChangeCount(form);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}
	
	//得到合同变更列表
	@Override
	public List<CompactChange> getCompactChangeList(CompactManagerForm form)
			throws BkmisServiceException {

		List<CompactChange> list = iCompactManagerDAO.getCompactChangeList(form);
		return list;
	}
	@Override
	public void setDead() throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		String todayDate = GlobalMethod.getTime();
		String endDate = GlobalMethod.getEndDate(todayDate, 30);
		List<Compact> beTermlist = iCompactManagerDAO.getAtTermList(endDate);//即将过期的未被续租的合同列表
		List<Compact> haveTermlist = iCompactManagerDAO.getAtTermList2(todayDate);//今天过期的合同列表(主要针对续租合同的老合同过期)
		if(beTermlist.size()>0){//更新任务提示
			iCompactManagerDAO.updateTask(beTermlist.size());
		}
		/****************2012-12-12将合同自动置为过期**********************/
		/*if(haveTermlist.size()>0){//将过期的合同置为作废
			for(int i = 0;i<haveTermlist.size();i++){
				Compact compact = (Compact)haveTermlist.get(i);
				compact.setIsDestine(Contants.HASOVER);
				//将合同对应的新的通过审批的续租合同的合同状态改为常规，入住状态改为已入住,此处存在下列问题：
				//如果老合同已经过期，新的续租合同还没有通过审批，那么下次更新的时候，haveTermlist中就不能够得到老合同的信息了
				//从而也不能更新新合同的状态了，该问题如何解决？？？？有待解决！！
				iCompactManagerDAO.update("update Compact c set c.isDestine='"+Contants.NORMARL+"',c.isNotice='"+Contants.HAVEIN+"' where c.status='"+Contants.THROUGHAPPROVAL+"' and  c.id in(select t.compactByNewId.id from CompactRelet t where t.compactByOldId.id="+compact.getId()+")");
				iCompactManagerDAO.updateObject(compact);
			}
		}*/
		
	}

	//审批退租合同
	@Override
	public void applyQuit(CompactManagerForm form)
			throws BkmisServiceException {
		String status = Contants.THROUGHAPPROVAL;
		if("0".equals(form.getFlag())){
			status = Contants.NOTTHROUGH;
		}
		CompactQuit quit = (CompactQuit)iCompactManagerDAO.getObject(CompactQuit.class,form.getId());
		quit.setStatus(status);
		iCompactManagerDAO.updateObject(quit);
		iCustomerRoomDAO.updateTask("approveQuit", -1);
	}

	//审批新合同
	@Override
	public String applyCompact(CompactManagerForm form)throws BkmisServiceException {

		String status = Contants.THROUGHAPPROVAL;
		if("0".equals(form.getFlag())){
			status = Contants.NOTTHROUGH;
		}
		Compact compact = (Compact)iCompactManagerDAO.getObject(Compact.class,Integer.parseInt(form.getCompactId()));
		compact.setStatus(status);
		iCompactManagerDAO.updateObject(compact);
		iCustomerRoomDAO.updateTask("approveNew", -1);
		//写系统日志
		if("1".equals(form.getFlag())){
			iCustomerRoomDAO.log(form.getUserName(), Contants.CHECK, "审批通过了编号为"+ compact.getCode()+"的新合同", Contants.L_COMPACTMANAGE,"合同审批");
		}else{
			iCustomerRoomDAO.log(form.getUserName(), Contants.CHECK, "审批未通过通过编号为" + compact.getCode() + "的新合同", Contants.L_COMPACTMANAGE,"合同审批");
		}
		if("1".equals(form.getFlag())){//通过审批
			if(Contants.DESTINES.equals(compact.getIsDestine())){//如果当前审核的是预租的合同，则只保持定金
				//保存定金
				CEarnest cEarnest = new CEarnest();
				cEarnest.setClientId(compact.getClientId());
				CEarnest cEarnest2 = iCompactManagerDAO.getCEarnest(compact.getClientId());
				if(cEarnest2==null){
					iCompactManagerDAO.saveObject(cEarnest);
				}
			}else{//
				List<CompactRent> rents = iCustomerRoomDAO.getCompactRent(compact.getId());
				for(int i=0;i<rents.size();i++){
					rents.get(i).setStatus(Contants.VALID);
				}
				List<ERoomClient> rooms = iCustomerRoomDAO.getERoomClientByPactId(compact.getId());
				for(int i=0;i<rooms.size();i++){
					rooms.get(i).setStatus(Contants.VALID);
				}
				List<CompactRoomCoststandard> standards = iCustomerRoomDAO.getCompactRoomCoststandard(compact.getId());
				for(int i=0;i<standards.size();i++){
					standards.get(i).setStatus(Contants.VALID);
				}
				//保存客户在住时间表
				AnalysisClientComeGo comeGo = new AnalysisClientComeGo();
				CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class,compact.getClientId());
				List<AnalysisClientComeGo> comeGos = iCustomerRoomDAO.getComeGo(compact.getClientId(),compact.getLpId());
				if(comeGos!=null&&comeGos.size()>0){//客户有入住信息
					AnalysisClientComeGo comeGo2 = comeGos.get(0);
					String enddate = comeGo2.getGoDate();
					int flag = compact.getBeginDate().compareTo(enddate);
					if(flag<0){
						comeGo2.setGoDate(compact.getEndDate());
						iCustomerRoomDAO.updateObject(comeGo2);
					}else if(flag>0){
						comeGo.setELp((ELp)iCustomerRoomDAO.getObject(ELp.class,compact.getLpId()));
						comeGo.setCompactClient(client);
						comeGo.setComeDate(compact.getBeginDate());
						comeGo.setGoDate(compact.getEndDate());
						iCustomerRoomDAO.saveObject(comeGo);
					}
				}else{//没有在住时间信息的客户
					comeGo.setELp((ELp)iCustomerRoomDAO.getObject(ELp.class,compact.getLpId()));
					comeGo.setCompactClient(client);
					comeGo.setComeDate(compact.getBeginDate());
					comeGo.setGoDate(compact.getEndDate());
					iCustomerRoomDAO.saveObject(comeGo);
				}
				
				//保存押金
				CDeposit rentDeposit = new CDeposit();//房租押金
				rentDeposit.setClientId(compact.getClientId());
				rentDeposit.setDepositType(Contants.RENT_DEPOSIT);
				CDeposit decorationDeposit = new CDeposit();//装修押金
				decorationDeposit.setClientId(compact.getClientId());
				decorationDeposit.setDepositType(Contants.DECORATION_DEPOSIT);
				CDeposit rentDeposit2 = iCompactManagerDAO.getDeposit(compact.getClientId(),Contants.RENT_DEPOSIT);
				if(rentDeposit2==null){
					iCompactManagerDAO.saveObject(rentDeposit);
				}
				CDeposit decorationDeposit2 = iCompactManagerDAO.getDeposit(compact.getClientId(),Contants.DECORATION_DEPOSIT);
				if(decorationDeposit2==null){
					iCompactManagerDAO.saveObject(decorationDeposit);
				}
				
				//保存预收款
				CAdvance cAdvance = new CAdvance();
				cAdvance.setClientId(compact.getClientId());
				CAdvance cAdvance1 = iCompactManagerDAO.getCAdvance(compact.getClientId());
				if(cAdvance1==null){
					iCompactManagerDAO.saveObject(cAdvance);
				}
			}
			
		}
		
		form.setFlag("true");
		return status;
	}

	//审批变更合同和续租合同
	@Override
	public void applyCompact(String compactId, String id, String type,
			String flag,String userName) throws BkmisServiceException {

		String status = Contants.THROUGHAPPROVAL;//通过审批
		if("0".equals(flag)){
			status = Contants.NOTTHROUGH;
		}
		Compact compact = (Compact)iCompactManagerDAO.getObject(Compact.class,Integer.parseInt(compactId));
		compact.setStatus(status);
		//作为使用了hibernate的工程，下面这句话即使不加，compact也应当是已经更新过
		//iCompactManagerDAO.updateObject(compact);
		CompactChange change = null;
		if("change".equals(type)){ //如果是变更合同
			change = (CompactChange)iCompactManagerDAO.getObject(CompactChange.class,Integer.parseInt(id));
			change.setStatus(status);
			iCompactManagerDAO.updateObject(change);
			iCustomerRoomDAO.updateTask("approveChange", -1);
			if("1".equals(flag)){//审核同意
				//保存合同状态和入驻状态
				compact.setIsDestine(Contants.NORMARL);
				compact.setIsNotice(Contants.HAVEIN);
				//新合同生效的同时，将老合同置为已过期，已变更
				change.getCompactByOldId().setIsDestine(Contants.HASOVER);
				change.getCompactByOldId().setIsNotice(Contants.HASCHANGE);
				iCustomerRoomDAO.log(userName, Contants.CHECK, "审批通过了编号为" + compact.getCode() + "的变更合同", Contants.L_COMPACTMANAGE, "合同审批");
			}else{
				iCustomerRoomDAO.log(userName, Contants.CHECK, "编号为" + compact.getCode() + "的变更合同审批未通过", Contants.L_COMPACTMANAGE, "合同审批");
			}
		}else if("relet".equals(type)){//如果是续租合同
			CompactRelet relet = (CompactRelet)iCompactManagerDAO.getObject(CompactRelet.class,Integer.parseInt(id));
			relet.setStatus(status);
			iCompactManagerDAO.updateObject(relet);
			iCustomerRoomDAO.updateTask("approveRelet", -1);
			//保存合同状态和入驻状态
			//compact.setIsDestine(Contants.NORMARL);
			//compact.setIsNotice(Contants.NOTNOTICE);
			if("1".equals(flag)){
				//将旧合同的入住状态改为已续
				relet.getCompactByOldId().setIsNotice(Contants.HASRELET);
				iCustomerRoomDAO.log(userName, Contants.CHECK, "审批通过了编号为" + compact.getCode() + "的续租合同", Contants.L_COMPACTMANAGE, "合同审批");
			}else{
				iCustomerRoomDAO.log(userName, Contants.CHECK, "编号为" + compact.getCode() + "的变更合同审批未通过", Contants.L_COMPACTMANAGE, "合同审批");
			}
		}else{
			//保存合同状态和入驻状态
			compact.setIsDestine(Contants.DESTINES);
			compact.setIsNotice(Contants.NOTNOTICE);
			if("1".equals(flag)){
				iCustomerRoomDAO.log(userName, Contants.CHECK, "审批通过了编号为" + compact.getCode() + "的新合同", Contants.L_COMPACTMANAGE, "合同审批");
			}else{
				iCustomerRoomDAO.log(userName, Contants.CHECK, "编号为" + compact.getCode() + "的新合同审批未通过", Contants.L_COMPACTMANAGE, "合同审批");
			}
		}
		if("1".equals(flag)){//通过审批的情况
			AnalysisClientComeGo comeGo = new AnalysisClientComeGo();
			CompactClient client = (CompactClient)iCustomerRoomDAO.getObject(CompactClient.class,compact.getClientId());
			List<AnalysisClientComeGo> comeGos = iCustomerRoomDAO.getComeGo(compact.getClientId(),compact.getLpId());
			if(comeGos!=null&&comeGos.size()>0){
				AnalysisClientComeGo comeGo2 = comeGos.get(0);
				String enddate = comeGo2.getGoDate();
				int flag1 = compact.getBeginDate().compareTo(enddate);
				if(flag1<0){
					comeGo2.setGoDate(compact.getEndDate());
					iCustomerRoomDAO.updateObject(comeGo2);
				}else if(flag1>0){
					comeGo.setELp((ELp)iCustomerRoomDAO.getObject(ELp.class,compact.getLpId()));
					comeGo.setCompactClient(client);
					comeGo.setComeDate(compact.getBeginDate());
					comeGo.setGoDate(compact.getEndDate());
					iCustomerRoomDAO.saveObject(comeGo);
				}
			}else{
				comeGo.setELp((ELp)iCustomerRoomDAO.getObject(ELp.class,compact.getLpId()));
				comeGo.setCompactClient(client);
				comeGo.setComeDate(compact.getBeginDate());
				comeGo.setGoDate(compact.getEndDate());
				iCustomerRoomDAO.saveObject(comeGo);
			}
			//保存押金
			CDeposit rentDeposit = new CDeposit();//房租押金
			rentDeposit.setClientId(compact.getClientId());
			rentDeposit.setDepositType(Contants.RENT_DEPOSIT);
			CDeposit decorationDeposit = new CDeposit();//装修押金
			decorationDeposit.setClientId(compact.getClientId());
			decorationDeposit.setDepositType(Contants.DECORATION_DEPOSIT);
			CDeposit rentDeposit2 = iCompactManagerDAO.getDeposit(compact.getClientId(),Contants.RENT_DEPOSIT);
			if(rentDeposit2==null){
				iCompactManagerDAO.saveObject(rentDeposit);
			}
			CDeposit decorationDeposit2 = iCompactManagerDAO.getDeposit(compact.getClientId(),Contants.DECORATION_DEPOSIT);
			if(decorationDeposit2==null){
				iCompactManagerDAO.saveObject(decorationDeposit);
			}
			//保存预收款
			CAdvance cAdvance = new CAdvance();
			cAdvance.setClientId(compact.getClientId());
			CAdvance cAdvance1 = iCompactManagerDAO.getCAdvance(compact.getClientId());
			if(cAdvance1==null){
				iCompactManagerDAO.saveObject(cAdvance);
			}
		}
	}

	//检查退租合同的状态
	@Override
	public String checkQuit(int id) throws BkmisServiceException {
		
		List<CompactQuit> quits = iCompactManagerDAO.getQuitByCompactId(id);
		if(quits!=null&&quits.size()>0){
			return quits.get(0).getStatus();
		}else{
			return "";
		}
	}
	/**
	 * 为导出报表提供的查询
	 * CompactManagerDAOImpl.getCompactListForE
	 */
	public void getCompactListForE(CompactManagerForm form) throws DataAccessException {
		
		List<Compact> list = iCompactManagerDAO.getCompactListForE(form);
		form.setList(list);
		
	}

	/**
	 * 删除合同
	 */
	@Override
	public void deleteCompact(CompactManagerForm form2)
			throws DataAccessException {
		if(form2!=null){
			String type = GlobalMethod.NullToSpace(form2.getType());//合同类型:租赁合同、续租合同、变更合同
			String ids = GlobalMethod.NullToSpace(form2.getIds());//获得要删除的合同id的字符串
			if(type.equals(Contants.COMPACTRELET)){//如果是续租合同
				//将续租合同的前一个合同的入住状态(isNotice)改为已入住
				iCompactManagerDAO.update("update Compact c set c.isNotice='"+Contants.HAVEIN+"' where c.id in (select t.compactByOldId.id from CompactRelet t where t.compactByNewId.id in("+ids+"))");
				//删除合同续租信息
				iCompactManagerDAO.update("delete from CompactRelet c where c.compactByNewId.id in("+ids+")");
			}
			if(type.equals(Contants.COMPACTCHANGE)){//如果是变更合同
				//将变更合同的前一个合同的入住状态(isNotice)改为已入住
				iCompactManagerDAO.update("update Compact c set c.isNotice='"+Contants.HAVEIN+"' where c.id in (select t.compactByOldId.id from CompactChange t where t.compactByNewId.id in("+ids+"))");
				//删除合同变更信息
				iCompactManagerDAO.update("delete from CompactChange c where c.compactByNewId.id in("+ids+")");
			}
			
			//删除合同
			iCompactManagerDAO.update("delete from Compact c where c.id in("+ids+")");
		}
	}

	/***
	 * 得到所有合同总数
	 * @param form
	 * @return
	 * @throws BkmisServiceException
	 * Date:2013-3-26 
	 * Time:下午10:04:31
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int getAllCompactCount(CompactManagerForm form)
			throws BkmisServiceException {
		
		List list = iCompactManagerDAO.getAllCompactCount(form);
		if(list != null && list.size()>0){
			return list.size();
		}else{
			return 0;
		}
		
	}

	/**
	 * 查询合同列表 
	 * @param form
	 * @return
	 * @throws BkmisServiceException
	 * Date:2013-3-26 
	 * Time:下午10:04:38
	 */
	@Override
	public List<Compact> getAllCompactList(CompactManagerForm form) throws BkmisServiceException {
		
		return iCompactManagerDAO.getAllCompactList(form);
	}
}
