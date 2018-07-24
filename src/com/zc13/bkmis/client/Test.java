package com.zc13.bkmis.client;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
public class Test {

	public static void main(String[] args) throws Exception {
		// ApplicationContext ac = new
		 //FileSystemXmlApplicationContext("WEB-INF/applicationContext.xml");
		 /*ApplicationContext ac = new ClassPathXmlApplicationContext("com/zc13/bkmis/client/applicationContext*.xml");
		 IRoomManageDAO roomManagerDao = (IRoomManageDAO)ac.getBean("iroomManageDao"); 
		 List list = roomManagerDao.findObject("select new Map(roomCode as room_code) from ERooms");
		 int count1 = list.size()%20==0?list.size():list.size()+1;
		 List Str20 = new ArrayList();
		 System.out.println("开始时间:"+new Date().getTime());
		 for(int i = 0;i<count1;i++){
			 String strs = "";
			 for(int j = 0;j<20&&i*20+j<list.size();j++){
				 strs+=((Map)list.get(i*20+j)).get("room_code")+" ";
			 }
			 System.out.println(strs);
		 }
		 System.out.println("结束时间："+new Date().getTime());*/
		 
		 /*
		 * IWriteMeterService writeMeterService =
		 * (IWriteMeterService)ac.getBean("writeMeterService"); List<AllMeterType>
		 * list = null; try { list = writeMeterService.getInfoForTree(); } catch
		 * (Exception e) { e.printStackTrace(); } for(AllMeterType a:list){
		 * AllMeterTypeId a1 = a.getId(); System.out.println(a1.getName()); }
		 */
		// System.out.println(ac.getBean("dataSource"));
		/*
		 * Set<Integer> set = new HashSet<Integer>(); Random random = new
		 * Random(); while(set.size() != 10){
		 * random.setSeed(System.currentTimeMillis());
		 * set.add(random.nextInt(10)+1); } System.out.println(set);
		 */
		// ICostTransactDAO c = (ICostTransactDAO)ac.getBean("costTransactDAO");
		// try {
		// List<CompactRoomCoststandard> list =
		// c.queryUsedCoststandard("2010-12-27");
		// for(CompactRoomCoststandard crc:list){
		// System.out.println(crc.getCCoststandard().getStandardName());
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		/*
		 * System.out.println("******************"); ICostTransactService ctsi =
		 * (ICostTransactService)ac.getBean("costTransactService"); try {
		 * ctsi.autoBuildBills(); } catch (Exception e) { e.printStackTrace(); }
		 */

		/*
		 * ICostTransactDAO a = (ICostTransactDAO)ac.getBean("costTransactDAO");
		 * //a.getCompactByClientIdAndCompactId(1,1); try {
		 * System.out.println("111111111"); a.update("update AwokeTask set
		 * amount=100 where code='pressDeposit'");
		 * System.out.println("222222222"); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		/*
		 * DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 * CostTransactServiceImpl c = new CostTransactServiceImpl();
		 * System.out.println(dateFormat.format(c.getStartDate4Circle("2010-01-03",
		 * "2010-01-03", 10, 3, 0).getTime()));
		 * System.out.println((double)12/(double)23);
		 */
		// A——Z 65——90
		// a——z 97——122
		/*String str = "print=哈哈和&id=123";
		System.out.println(java.net.URLEncoder.encode(str, "utf-8"));
		System.out.println(java.net.URLDecoder.decode(java.net.URLEncoder.encode(str, "utf-8"),"utf-8"));*/
		
//		System.out.println(DateUtil.getInterval("2011-05-03 12:23:54", "2011-05-04 10:13:12"));
//		System.out.println(DateUtil.round(23.45372,3));
		
		//System.out.println(11*12.2);
		
		/*PhoneCostForm form = new PhoneCostForm();
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/zc13/bkmis/client/applicationContext*.xml");
		IPhoneCostService phoneCostService = (IPhoneCostService)ac.getBean("phoneCostService");
		//phoneCostService.getPhoneCostInfoList(form, false);
		phoneCostService.storeCallInfo();*/
		//phoneCostService.test();
		//phoneCostService.storeCallInfo();
		
		//java访问共享文件夹测试
		byte buffer[] = new byte[1024] ; 
		int readed = 0 ;
		try{
//			SmbFile file = new SmbFile("smb://172.20.8.116/share/2011-10-09-2533.TXT");
			SmbFile file = new SmbFile("smb://172.40.3.11/share/2011-09-07-2533.TXT");
			SmbFileInputStream in = new SmbFileInputStream (file);
			if(file.exists()){
				String str = "";
				char c;
				int a;
		//		while((c = (char)in.read())!=13){
		//			str += c;
		//		}
				while((a=in.read())!=-1){
					c = (char)a;
					if(c!=13){
						str += c;
					}else{
						System.out.println(str);
						System.out.println("****************************");
						str = "";
					}
					
				}
		//		while((readed = in.read(buffer)) != -1){ 
		//			System.out.write(buffer); 
		//		}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
