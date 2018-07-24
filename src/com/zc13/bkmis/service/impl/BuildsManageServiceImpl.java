package com.zc13.bkmis.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IBuildsManageDAO;
import com.zc13.bkmis.dao.IMeterManageDAO;
import com.zc13.bkmis.dao.IRoomManageDAO;
import com.zc13.bkmis.form.BuildForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EFloorMap;
import com.zc13.bkmis.mapping.EFloorMapCoordinate;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.service.IBuildsManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.ISysParamManagerDAO;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.CloneObject;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;
/**
 * @author 侯哓娟
 * Date：Nov 20, 2010
 * Time：2:13:58 PM
 */
public class BuildsManageServiceImpl implements IBuildsManageService {

	Logger logger = Logger.getLogger(this.getClass());
	//注入IBuildsManageDAO
	private IBuildsManageDAO ibuildsManageDao;
	/* 注入imeterManageDao */
	private IMeterManageDAO imeterManageDao;
	//注入iSysParamManagerDAO
    private ISysParamManagerDAO iSysParamManagerDAO;
    /* 注入iroomManageDao */
    private IRoomManageDAO iroomManageDao;
	public ISysParamManagerDAO getISysParamManagerDAO() {
		return iSysParamManagerDAO;
	}
	public void setISysParamManagerDAO(ISysParamManagerDAO sysParamManagerDAOImpl){
		this.iSysParamManagerDAO = sysParamManagerDAOImpl;
	}
	public IBuildsManageDAO getIbuildsManageDao() {
		return ibuildsManageDao;
	}
	public void setIbuildsManageDao(IBuildsManageDAO ibuildsManageDao) {
		this.ibuildsManageDao = ibuildsManageDao;
	}
	
	public IMeterManageDAO getImeterManageDao() {
		return imeterManageDao;
	}
	public void setImeterManageDao(IMeterManageDAO imeterManageDao) {
		this.imeterManageDao = imeterManageDao;
	}
	public IRoomManageDAO getIroomManageDao() {
		return iroomManageDao;
	}
	public void setIroomManageDao(IRoomManageDAO iroomManageDao) {
		this.iroomManageDao = iroomManageDao;
	}
	public List<EBuilds> getBuilds() {
		List<EBuilds> list = null;
		try{
			list = ibuildsManageDao.getObjects(EBuilds.class);
		}catch(Exception e){
			logger.error("楼栋信息查询失败!BuildsManageServiceImpl.getBuilds()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋信息查询失败!BuildsManageServiceImpl.getBuilds()。");
		}
		return list;
	}
	
	@Override
	public boolean checkBuildCode(BuildForm buildForm) throws Exception {
		
		boolean flag = false;
		try{
			String buildCode = buildForm.getBuildCode();
			Integer lpId = buildForm.getLpId();
			ELp elp = (ELp) ibuildsManageDao.getObject(ELp.class, lpId);
			List<EBuilds> list = ibuildsManageDao.queryBuildsByELpAndCode(elp, buildCode);
			if(list.size() >= 1 && list != null){
				flag = false;
			}else{
				flag = true;
			}
		}catch(Exception e){
			logger.error("楼栋名称检测失败!BuildsManageServiceImpl.checkBuildName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋名称检测失败!BuildsManageServiceImpl.checkBuildName()。");
		}
		return flag;
	}
	
	@Override
	public boolean checkBuildName(BuildForm buildForm) throws Exception {
		
		boolean flag = false;
		try{
			String buildCode = buildForm.getBuildCode();
			String buildName = buildForm.getBuildName();
			Integer lpId = buildForm.getLpId();
			ELp elp = (ELp) ibuildsManageDao.getObject(ELp.class, lpId);
			List<EBuilds> list = ibuildsManageDao.queryBuildByELpAndName(elp,buildName);
			if(list.size() >= 1 && list != null){
				flag = false;
			}else{
				flag = true;
			}
		}catch(Exception e){
			logger.error("楼栋名称检测失败!BuildsManageServiceImpl.checkBuildName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋名称检测失败!BuildsManageServiceImpl.checkBuildName()。");
		}
		return flag;
	}
	
	@Override
	public boolean checkBuildNameAndCode(BuildForm buildForm) throws Exception {
		
		boolean flag = false;
		try{
			String buildCode = buildForm.getBuildCode();
			String buildName = buildForm.getBuildName();
			Integer lpId = buildForm.getLpId();
			ELp elp = (ELp) ibuildsManageDao.getObject(ELp.class, lpId);
			List<EBuilds> list = ibuildsManageDao.queryBuildByCLN(buildCode,elp,buildName);
			if(list.size() >= 1 && list != null){
				flag = false;
			}else{
				flag = true;
			}
		}catch(Exception e){
			logger.error("楼栋名称检测失败!BuildsManageServiceImpl.checkBuildName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋名称检测失败!BuildsManageServiceImpl.checkBuildName()。");
		}
		return flag;
	}
	@Override
	public void addBuild(BuildForm buildForm) throws Exception {
		try{
			/* 添加楼栋 */
			Integer lpId = buildForm.getLpId();
			ELp elp = (ELp) ibuildsManageDao.getObject(ELp.class, lpId);
			EBuilds ebuild = new EBuilds();
			BeanUtils.copyProperties(ebuild,buildForm);
			ebuild.setELp(elp);
			ibuildsManageDao.saveObject(ebuild);
			//添加系统日志
			ibuildsManageDao.logDetail(buildForm.getUserName(),Contants.ADD,"楼栋",Contants.L_BUILD,"2",ebuild);
			 /** 保存楼盘的表具信息 */
			   /* 从表单里面得到楼盘名称 */
			    String buildName = buildForm.getBuildName();
			    List<EBuilds> buildlist = ibuildsManageDao.queryBuildByName(buildName);
			    Integer buildId = 0;
			    for(EBuilds b:buildlist){
			    	buildId = b.getBuildId();
			    }
			    
			    /* 添加表具 
			     * 从Form中得到表具的信息拼接的字符串 */
			   String str1 = buildForm.getCode();
			   String str2 = buildForm.getType();
			   /* 把到的字符串从＂；＂分号处开始拆分　*/
			   String[] arr1 = str1.split(";");
			   String[] arr2 = str2.split(";");
			   String code = null;
			   String type = null;
			   Integer meterId = 0;
			   if (!"".equals(str1)  && !"".equals(str2)){
				   for(int i=0,j=0;i<arr1.length && j<arr2.length;i++,j++){
					   /* 循环出表具信息，得到表具编号 */
					   code = arr1[i];
					   type = arr2[j];
					   /* 根据表具编号得到一个表具list */
					   List<EMeter> list = imeterManageDao.queryMeterByCodeAndType(code, type);
					   for(EMeter e:list){
						   meterId = e.getId();
					   }
					   /* 再根据表具id去得到一个表具对象 */
					   EMeter emeter = (EMeter) imeterManageDao.getObject(EMeter.class, meterId);
					   /* 把楼盘号保存到表具对象里 */
					   emeter.setBuildId(buildId);
					   /* 更新表具对象 */
					   imeterManageDao.updateObject(emeter);
				   }
			   }
		}catch(Exception e){
			logger.error("楼栋添加失败!BuildsManageServiceImpl.addBuild()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋添加失败!BuildsManageServiceImpl.addBuild()。");
		}
		
	}
	@Override
	public EBuilds getEBuildsById(Integer buildId) throws Exception {
		EBuilds ebuild = new EBuilds();
		try{
			if(buildId == null){
				return null;
			}else{
				ebuild = (EBuilds)ibuildsManageDao.getObject(EBuilds.class, buildId);
			}
		}catch(Exception e){
			logger.error("楼栋信息获取失败!BuildsManageServiceImpl.getModifyInfo()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋信息获取失败!BuildsManageServiceImpl.getModifyInfo()。");
		}
		return ebuild;
	}
	@Override
	public void modifyBuild(BuildForm buildForm) throws Exception {
		try{
			/* 更新楼栋信息 */
			Integer buildId = buildForm.getBuildId();
			EBuilds ebuild = (EBuilds) ibuildsManageDao.getObject(EBuilds.class, buildId);
			EBuilds ebuild2 = new EBuilds();
			ebuild2 = (EBuilds)CloneObject.copy(ebuild);
			BeanUtils.copyProperties(ebuild2,buildForm);
			//写入系统日志
			ibuildsManageDao.logDetail(buildForm.getUserName(), Contants.MODIFY,"楼栋",Contants.L_BUILD, "1", ebuild2);
			//更新
			BeanUtils.copyProperties(ebuild,buildForm);
			ibuildsManageDao.updateObject(ebuild);
//			Integer lpId = buildForm.getLpId();
			
			/* 更新对应楼盘的表具信息 
		     * 从Form中得到新增的表具的信息拼接的字符串 */
		   String str1 = buildForm.getCode();
		   String str2 = buildForm.getType();
		   if (!"".equals(str1)  && !"".equals(str2)) {
				/* 把到的字符串从＂；＂分号处开始拆分 */
				String[] arr1 = str1.split(";");
				String[] arr2 = str2.split(";");
				String code = null;
				String type = null;
				Integer meterId = 0;
				for (int i = 0, j = 0; i < arr1.length && j < arr2.length; i++, j++) {
					/* 循环出表具信息，得到表具编号 */
					code = arr1[i];
					type = arr2[j];
					/* 根据表具编号得到一个表具list */
					List<EMeter> list = imeterManageDao.queryMeterByCodeAndType(code, type);
					for (EMeter e : list) {
						meterId = e.getId();
					}
					/* 再根据表具id去得到一个表具对象 */
					EMeter emeter = (EMeter) imeterManageDao.getObject(EMeter.class, meterId);
					/* 把楼盘号保存到表具对象里 */
					emeter.setBuildId(buildId);
//					emeter.setLpId(lpId);
					/* 更新表具对象 */
					imeterManageDao.updateObject(emeter);
				}
		   }
		}catch(Exception e){
			logger.error("楼栋信息修改失败!BuildsManageServiceImpl.modifyBuild()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋信息修改失败!BuildsManageServiceImpl.modifyBuild()。");
		}
		
	}
	@Override
	public void deletebuild(List<String> buildList,String userName) throws Exception {
		try{
			int buildId = 0;
			Iterator<String> it = buildList.iterator();
			while(it.hasNext()){
				buildId = Integer.parseInt(it.next());
				EBuilds eBuilds = (EBuilds)ibuildsManageDao.getObject(EBuilds.class, buildId);

				//添加系统日志
				ibuildsManageDao.logDetail(userName,Contants.DELETE,"楼栋",Contants.L_BUILD,"3",eBuilds);
				ibuildsManageDao.deleteObject(eBuilds);
			}
		}catch(Exception e){
			logger.error("楼栋信息删除失败!BuildsManageServiceImpl.deletebuild()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋信息删除失败!BuildsManageServiceImpl.deletebuild()。");
		}
		
	}
	@Override
	public List<EBuilds> getBuildsByELp(ELp ELp,String buildName) throws Exception {
		
		List<EBuilds> list = null;
		try{
			list = ibuildsManageDao.queryBuildsByELp(ELp,buildName);
		}catch(Exception e){
			logger.error("根据楼盘名称查寻楼栋信息失败!BuildsManageServiceImpl.getBuildsByLpname()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据楼盘名称查寻楼栋信息失败!BuildsManageServiceImpl.getBuildsByLpname()。");
		}
		return list;
	}
	@Override
	public List<EBuilds> getBuilds(BuildForm buildForm) throws Exception {
		
		List<EBuilds> list = null;
		try{
			list = ibuildsManageDao.queryBuild(buildForm);
		}catch(DataAccessException e){
			logger.error("楼栋信息查询失败!ibuildsManageDao.queryBuild()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋信息查询失败!BuildsManageServiceImpl.getBuilds()。");
		}
		return list;
	}
	@Override
	public int queryCounttotal(BuildForm buildForm) throws Exception {
		
		return ibuildsManageDao.queryCounttotal(buildForm);
	}
	@Override
	public EFloorMap getEFloorMap(String floor, Integer id,String tablename)
			throws Exception {
		
		List<EFloorMap> list = null;
		EFloorMap floorMap = new EFloorMap();
		Integer lpId = null;
		Integer buildId = null;
		String type = null;//平面图类型
		floor = GlobalMethod.NullToSpace(floor);
		if(Contants.E_LP.equals(tablename)){//如果是楼盘
			lpId = id;
			type = Contants.LP_MAP;
		}
		if(Contants.E_BUILD.equals(tablename)){//如果是楼栋
			buildId = id;
			if("".equals(floor)){//如果楼层号为空，则说明是楼栋平面图
				type = Contants.BUILD_MAP;
			}else{//否则，说明是楼层平面图
				type = Contants.FLOOR_MAP;
			}
		}
		//将参数封装成floorMap对象
		floorMap.setELp(new ELp(lpId));
		floorMap.setEBuilds(new EBuilds(buildId));
		floorMap.setFloor(floor);
		floorMap.setType(type);
		try{
			list = ibuildsManageDao.getEfloorMap(floorMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("楼栋信息查询失败!BuildsManageServiceImpl.getEFloorMap()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("楼栋信息查询失败!BuildsManageServiceImpl.getEFloorMap()。");
		}
		if(list!=null&&list.size()>0){
			floorMap = list.get(0);
		}
		return floorMap;
	}
	@Override
	public Map getPSize(String psize) throws Exception {
		
		Map map = null;
		try{
			 map = ibuildsManageDao.queryPSize(psize);
		}catch(Exception e){
			logger.error("图片大小查询失败!BuildsManageServiceImpl.getPSize()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("图片大小查询失败!BuildsManageServiceImpl.getPSize()。");
		}
		return map;
	}
	@Override
	public void addPlanMap(BuildForm buildForm) throws Exception {
		if(buildForm!=null){
			Integer lpId = null;//楼盘id
			Integer buildId = null;//楼栋id
			String type = null;//平面图类型
			if(Contants.E_LP.equals(buildForm.getTablename())){//如果是楼盘
				lpId = buildForm.getId();
				type = Contants.LP_MAP;
			}
			if(Contants.E_BUILD.equals(buildForm.getTablename())){//如果是楼栋
				buildId = buildForm.getId();
				if("".equals(GlobalMethod.NullToSpace(buildForm.getFloor()))){//如果没有楼层号，则说明是楼栋平面图
					type = Contants.BUILD_MAP;
				}else{//否则，是楼层平面图
					type = Contants.FLOOR_MAP;
				}
			}
			//将参数封装成floorMap对象
			EFloorMap floorMap = new EFloorMap();
			if(lpId!=null){
				floorMap.setELp((ELp)ibuildsManageDao.getObject(ELp.class, lpId));
			}
			if(buildId!=null){
				floorMap.setEBuilds((EBuilds)ibuildsManageDao.getObject(EBuilds.class, buildId));
			}
			floorMap.setFloor(buildForm.getFloor());
			floorMap.setType(type);
			try{
				List<EFloorMap> list = ibuildsManageDao.getEfloorMap(floorMap);
				Integer mapId = 0;
				if(list.size()!= 0){
					floorMap = list.get(0);
					floorMap.setUrl(buildForm.getMapUrl());
					ibuildsManageDao.updateObject(floorMap);
				}else{
					floorMap.setUrl(buildForm.getMapUrl());
					ibuildsManageDao.saveObject(floorMap);
			    }
				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("图片添加失败!BuildsManageServiceImpl.getPSize()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("图片添加失败!BuildsManageServiceImpl.getPSize()。");
			}
		}
	}
	
	 @Override
	 public List<EMeter> getMeterByLpIdAndName(Integer lpId,String meterName) throws BkmisServiceException {
		   boolean var = false;
		   List<EMeter> list = null;
			try{
				 list = imeterManageDao.MeterByLpIdAndName(lpId,meterName);
			}catch(Exception e){
				logger.error("根据buildId和表具名称查询表具信息失败!RoomManageServiceImpl.getMeterByBuildIdAndName()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("根据buildId和表具名称查询表具信息失败!RoomManageServiceImpl.getMeterByBuildIdAndName()。");
			}
			return list;
	  }
	 
	@Override
	public boolean checkMeter(BuildForm buildForm) throws Exception {
			boolean var = false;
			try{
				EMeter emeter = new EMeter();
				BeanUtils.copyProperties(emeter,buildForm);
				String meterName = emeter.getType();
				String meterCode = emeter.getCode();
				List<EMeter> list = null;
			    /* 同一种类型的表中不能有重复的表编号 */
				list = (List<EMeter>) imeterManageDao.queryMeterByCodeAndType(meterCode,meterName);
				if(list != null && list.size()>= 1){
					var = false;
				}else{
					var = true;
				}
			}catch(Exception e){
				logger.error("表具名称检测失败!BuildsManageServiceImpl.checkMeter()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("表具名称检测失败!BuildsManageServiceImpl.checkMeter()。");
			}
			return var;
		}
	@Override
	public void addMeter(BuildForm buildForm) throws Exception {
		try{
				EMeter emeter = new EMeter();
				BeanUtils.copyProperties(emeter,buildForm);
				
				imeterManageDao.saveObject(emeter);
				//添加系统日志
				ibuildsManageDao.logDetail(buildForm.getUserName(),Contants.ADD,"楼栋总表具",Contants.L_BUILD,"2",emeter);
			}catch(Exception e){
				logger.error("表具添加失败!BuildsManageServiceImpl.addMeter()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("表具添加失败!BuildsManageServiceImpl.addMeter()。");
			}
		}
	
	@Override
	public List<EMeter> getMeterByCodeAndName(String meterCode, String meterName)throws Exception {
			
		List<EMeter> list = null;
		try{
			 list = imeterManageDao.queryMeterByCodeAndType(meterCode, meterName);
		}catch(Exception e){
			logger.error("根据表具编号查询表具失败!BuildsManageServiceImpl.getMeterByCodeAndName()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据表具编号查询表具失败!BuildsManageServiceImpl.getMeterByCodeAndName()。");
		}
		return list;
	}
	
	@Override
	public List<SysCode> getMeterType() throws Exception {
			List<SysCode> list = null;
			try{
				list = imeterManageDao.queryMeterType();
			}catch(Exception e){
				logger.error("表具类型获得失败!BuildsManageServiceImpl.getMeterType()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("表具类型获得失败!BuildsManageServiceImpl.getMeterType()。");
			}
			return list;
		}
	
	@Override
	public String getMeterName(BuildForm buildForm)throws BkmisServiceException{
		
		String meterName = null;
		try{
			String codeValue = buildForm.getType();
			SysCode syscode =  iSysParamManagerDAO.getCodeByValue(codeValue);
			meterName = syscode.getCodeName();
		}catch(DataAccessException e){
			logger.error("从系统参数里获取表具的名称失败!iSysParamManagerDao.getCodeByValue()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("从系统参数里获取表具的名称失败!LpManageServiceImpl.getMeterName()。");
		}
		return meterName;
	}
	
	@Override
	 public String getMeterTypeByCodeName(String meterName)throws BkmisServiceException{
		
		String meterType = null;
		try{
			SysCode syscode =  iSysParamManagerDAO.getCodeByName(meterName);
			meterType = syscode.getCodeValue();
		}catch(DataAccessException e){
			logger.error("从系统参数里获取表具的名称失败!iSysParamManagerDao.getCodeByValue()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("从系统参数里获取表具的名称失败!LpManageServiceImpl.getMeterName()。");
		}
		return meterType;
	}
	
	@Override
	public void deleteMeter(String meterCode, String meterType) throws Exception {
		
		try{
			imeterManageDao.deleteMeter(meterCode,meterType);
		}catch(Exception e){
			logger.error("表具信息删除失败!BuildsManageServiceImpl.deleteMeter()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("表具信息删除失败!BuildsManageServiceImpl.deleteMeter()。");
		}
	}
	@Override
	public void deleteMeterByBuildId(List<String>  buildList) throws Exception {
		
		try{
			imeterManageDao.deleteMeterByBuildId(buildList);
		}catch(Exception e){
			logger.error("根据楼栋id删除表具信息失败!BuildsManageServiceImpl.deleteMeter()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException("根据楼栋id删除表具信息失败!BuildsManageServiceImpl.deleteMeter()。");
		}
		
	}
	@Override
	public List getEMeterByBuildId(Integer buildId) throws Exception {
		
		List list = null;
		try{
			 list = imeterManageDao.queryMeterByBuildId(buildId);
		}catch(Exception e){
			logger.error("根据楼栋id查询表具信息失败!BuildsManageServiceImpl.getEMeterByBuildId()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("根据楼栋id查询表具信息失败!BuildsManageServiceImpl.getEMeterByBuildId()。");
		}
		return list;
	}
	
	@Override
	public String upload(BuildForm uploadForm,String filePath) throws Exception {
		
		String path = null;
		//得到上传文件
		FormFile file = uploadForm.getFile();
		/** 判断上传文件是否为空 */
		if(file == null || file.getFileSize() == 0 ){
			   return null;
		 }else{
				InputStream stream = file.getInputStream();
				//得到上传文件的名称
				String filename = file.getFileName();
				//截取文件名的后缀
				String extention = filename.substring(filename.lastIndexOf('.'));
				//创建一个文件类型的对像,用于创建上传文件的存放路径
				File f = new File(filePath + "/plan");
				//判断file指定的文件路径是否存在，不存在就根据指定的抽象路径名创建目录
				if(!f.exists()){
					f.mkdir();
				}
				//new一个文件类型的用于存放上传文件的对象
				File realFile = new File(filePath + "/plan" +"/"+ filename);
				//把要上传的文件用输出字节流写出去
				OutputStream out = new FileOutputStream(realFile);
				out.write(file.getFileData());
				out.flush();
				out.close();
				path = realFile.toString();
		}
		return path;
	}
	
	@Override
	public Integer addRoomClient(String x, String y, String roomWidth,
			String chartId, String sroomId,String planSize) throws Exception {
		
		Double XCoordinate = null;//x坐标
		Double YCoordinate = null;//y坐标
		Double mapWidth = null;
		/* 用于判断是大图还是小图，数据库里只保存楼层图小图的房间图的坐标 */
		String bwidth = null;//大图宽度
		String bheight = null;//大图高度
		String swidth = null;//小图宽度
		String sheigth = null;//小图高度
		String rWidth = null;//实际宽度
		String rHeight = null;//实际高度
	    
		Integer roomId = null;//房间id
		EFloorMap eFloorMap = null;
		if(!"".equals(x)){
			XCoordinate = Double.parseDouble(x);
		}
		if(!"".equals(y)){
			YCoordinate = Double.parseDouble(y);
		}
		if(!"".equals(roomWidth)){
			mapWidth = Double.parseDouble(roomWidth);
		}
		if(!"".equals(sroomId)){
			roomId = Integer.parseInt(sroomId);
		}
		if(!"".equals(chartId)){
			eFloorMap = (EFloorMap) ibuildsManageDao.getObject(EFloorMap.class, Integer.parseInt(chartId));
		}
		
		EFloorMapCoordinate roomClentPlan = new EFloorMapCoordinate();
		
		/* 保存楼层平面图对应的小图的坐标 */
		if(!"".equals(planSize)){
			String[] temp = planSize.split("-");
			rWidth = temp[0];
			rHeight = temp[1];
			Map map = getChartSize();
			swidth = (String)map.get("swidth");
			sheigth = (String)map.get("sheigth");
			bwidth = (String)map.get("bwidth");
			bheight = (String)map.get("bheight");
			//如果是小图
			if(rWidth.equals(swidth) && rHeight.equals(sheigth)){
				roomClentPlan.setXCoordinate(XCoordinate);
				roomClentPlan.setYCoordinate(YCoordinate);
				roomClentPlan.setMapWidth(mapWidth);
			}
			//如果是大图
			if(rWidth.equals(bwidth) && rHeight.equals(bheight)){
				Map proportionChaart = getProportionOfChart(map);
				roomClentPlan.setXCoordinate(XCoordinate *((Double)(proportionChaart.get("widthProportion"))));
				roomClentPlan.setYCoordinate(YCoordinate * ((Double)(proportionChaart.get("heightProportion"))));
				roomClentPlan.setMapWidth(mapWidth *((Double)(proportionChaart.get("widthProportion"))));
			}
		}
		roomClentPlan.setEFloorMap(eFloorMap);
		roomClentPlan.setRoomId(roomId);
		ibuildsManageDao.saveObject(roomClentPlan);
		return roomClentPlan.getId();
	}
	@Override
	public List<EFloorMapCoordinate> getRoomMap(String schartId) throws Exception {

        Integer chartId = null;
        Double x = null;
		Double y = null;
		Double width = null;
		Double rx = null;
		Double ry = null;
		Integer roomChartId = null;
		EFloorMapCoordinate roomChart;
        if(!"".equals(schartId)){
        	chartId = Integer.parseInt(schartId);
        }
        /* 获取一个楼层平面图对象 */
        EFloorMap chart = (EFloorMap)ibuildsManageDao.getObject(EFloorMap.class, chartId);
        /* 获取房间图 */
        List<EFloorMapCoordinate> list= ibuildsManageDao.getRoomMap(chart);
        return list;
	}
	@Override
	public Map getChartSize() throws Exception {

		Map map = new HashMap();
		
		String type = "size";
		
		/* 得到小图的长，宽 */
		String name1 = "0";
		String bwidth = null;
		String bheight = null;
		String swidth = null;
		String sheigth = null;
		SysCode bSysCode = iSysParamManagerDAO.getPrama(type,name1);
		String str1 = bSysCode.getCodeValue();
		String[] temp1 = str1.split("-");
		swidth = temp1[0];
		sheigth = temp1[1];
		map.put("swidth", swidth);
		map.put("sheigth", sheigth);
		
		/* 得到大图的长，宽 */
		String name2 = "1";
		SysCode sSysCode = iSysParamManagerDAO.getPrama(type,name2);
		String str2 = sSysCode.getCodeValue();
		String[] temp2 = str2.split("-");
		bwidth = temp2[0];
		bheight= temp2[1];
		map.put("bwidth", bwidth);
		map.put("bheight", bheight);
		
		return map;
	}
	
	@Override
	public Map getProportionOfChart(Map map) throws Exception{
		
		Double  widthProportion = null;
		Double  heightProportion = null;
		
		Double bwidth = null;
		Double bheight = null;
		Double swidth = null;
		Double sheigth = null;
		Map proportionMap = new HashMap();
		swidth = Double.parseDouble((String) map.get("swidth"));
		sheigth = Double.parseDouble((String) map.get("sheigth"));
		bwidth = Double.parseDouble((String)map.get("bwidth"));
		bheight = Double.parseDouble((String)map.get("bheight"));
		widthProportion = swidth / bwidth;
		heightProportion = sheigth / bheight;
		/* 计算大图和小图的长宽比例 */
		proportionMap.put("widthProportion", widthProportion);
		proportionMap.put("heightProportion",heightProportion);
		
		return proportionMap;
		
	}
	//2011年2月11日，董道奎将此方法暂时作废
	@Override
	public void deleteRoomMap(String floor, Integer buildId)throws BkmisServiceException {

		Integer roomId = null;
		try{
			EBuilds build = (EBuilds)ibuildsManageDao.getObject(EBuilds.class, buildId);
			List<ERooms> list =  iroomManageDao.getRoomsByEBuildsAndFloor(floor, build);
			for(ERooms r:list){
				roomId = r.getRoomId();
				/* 删除某个房间对应的所有的房间图信息 */
				ibuildsManageDao.deleteRoomMap(roomId);
			}
		}catch(DataAccessException e){
			logger.error("重新上传一个楼层平面图的时候,删除原来房间图的信息失败!ibuildsManageDao.deleteRoomMap()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("重新上传一个楼层平面图的时候,删除原来房间图的信息失败!BuildsManageServiceImpl.deleteRoomMap()。");
		}
	}
	
	@Override
	public boolean checkRoomMap(String sroomId) throws BkmisServiceException {

		Integer roomId = null;
		Boolean flag = null;
		List<EFloorMapCoordinate> list = null;
		try{
			if(!"".equals(sroomId)){
				roomId = Integer.parseInt(sroomId);
			}
			list = ibuildsManageDao.getRoomMap(roomId);
			if(list.size() == 1){
				flag = true;
			}else{
				flag = false;
			}
		}catch(Exception e){
			logger.error("获取房间图信息失败!BuildsManageServiceImpl.getBuilds()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("检查房间图重复信息失败!BuildsManageServiceImpl.checkRoomMap()。");
		}
		return flag;
	}
	
	@Override
	public List getBuild(BuildForm buildForm) throws Exception {
		
		Integer lpId = buildForm.getLpId();
		String hql = "from EBuilds e";
		if(lpId!=null && lpId!=0){
			hql+=" where e.ELp.lpId = "+lpId;
		}
		return ibuildsManageDao.findObject(hql);
	}
	@Override
	public void deleteRMeter() throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		ibuildsManageDao.deleteRMeter();
		
	}
	@Override
	public List<Map> getClientAndRoomById(Integer roomId) {
		return ibuildsManageDao.getClientAndRoomById(roomId);
	}
	@Override
	public void deleteRoomMark(Integer chartId) throws Exception {
		ibuildsManageDao.deleteObject(ibuildsManageDao.getObject(EFloorMapCoordinate.class, chartId));
	}
}
