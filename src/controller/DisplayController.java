package controller;



import java.sql.Date;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;

import model.PageDao;
import model.Websites;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class DisplayController extends Controller{

	 public void index()  
	    {   
		 
		 	System.out.println("in controller");
//		 	this.render("/HTML/Display.html");
		 	this.render("/zone_search.html");
	    }
	 
	    public void asys()  
	    {  
	    	List<Websites> web = new ArrayList<Websites>();
	    	Map<String, List<Websites>> map = new HashMap<String,List<Websites>>();
	    	String name = null;
	    	int num = 0;
	          
	    	Websites websites = new Websites();
	    	String sql = "select * from officialwebsite";
	        web = websites.dao.find(sql);
//	        System.out.println("get page "+web.size());
	        map.put("list", web);
//	        System.out.println(map);
	          
	        if(web != null)  
	        	renderJson(map);        
	    }
	    
	    public void getData()  
	    {  
	    	String zone = getPara("zone");
	    	System.out.println(zone);
	    	if(zone!="default"){
	    		System.out.println("in getData");
	    		Map<String, List<PageDao>> map = new HashMap<String,List<PageDao>>();
	    		
	    		String sql = "select * from basicinfo where ZoneName='"+zone+"' " +
	    				"and source='百度' ORDER BY NewsTime";
	    		System.out.println(sql);
	    		List<PageDao> webList = new ArrayList<>();	    		
	    		PageDao page = new PageDao();
	    		webList = page.dao.find(sql);
	    		
	    		
	    		sql = "select * from basicinfo where ZoneName='"+zone+"' " +
	    				"and source='官方网站' ORDER BY NewsTime";
	    		System.out.println(sql);
	    		List<PageDao> pageList = new ArrayList<>();
	    		pageList = page.dao.find(sql);
	    		
	    		
	    		sql = "select * from basicinfo where ZoneName='"+zone+"' " +
	    				"ORDER BY NewsTime";
	    		System.out.println(sql);
	    		List<PageDao> allList = new ArrayList<>();
	    		allList = page.dao.find(sql);
	    		
/*	    		for(PageDao p:webList){
	    			if(p.getDate("NewsTime")==null){
	    				p.set("NewsTime", new java.util.Date(2002,12,31));
	    			}
	    		}
	    		for(PageDao p:pageList){
	    			if(p.getDate("NewsTime")==null){
	    				p.set("NewsTime", new java.util.Date(2002,12,31));
	    			}
	    		}
	    		for(PageDao p:allList){
	    			if(p.getDate("NewsTime")==null){
	    				p.set("NewsTime", new java.util.Date(2002,12,31));
	    			}
	    		}*/
	    		
	    		List<java.util.Date> time = new ArrayList<>();
	    		for(PageDao p:allList){
	    			if(!time.contains(p.getDate("NewsTime")))
	    				time.add(p.getDate("NewsTime"));
	    		}
//	    		System.out.println(time.size());
//	    		for(java.util.Date t:time)
//	    			System.out.println(t);
//	    		System.out.println("--------------------------------");
	    		
	    		List<PageDao> WebList = new ArrayList<>();
	    		for(int i=0;i<time.size();i++){
	    			PageDao tmp = new PageDao();
//	    			System.out.println(time.get(i));	    			
	    			tmp.set("NewsTime", time.get(i));	    			
//	    			System.out.println(tmp.getDate("NewsTime"));
	    			int num = this.find(webList,tmp.getDate("NewsTime"));
	    			if(num == 0){
	    				tmp.set("num", null);
	    			}
	    			else{
	    				tmp.set("num", num);
	    			}
	    			WebList.add(tmp);
	    		}	    		
	    		map.put("baidu", WebList);
//	    		System.out.println(map.get("baidu").size());
//	    			    		  		
//	    		for(PageDao p:map.get("baidu")){
//	    			System.out.println(p.getDate("NewsTime"));
//	    			System.out.println(p.getInt("num"));
//	    		}
	    		
	    		List<PageDao> PageList = new ArrayList<>();
	    		for(int i=0;i<time.size();i++){
	    			PageDao tmp = new PageDao();
//	    			System.out.println(time.get(i));	    			
	    			tmp.set("NewsTime", time.get(i));	    			
//	    			System.out.println(tmp.getDate("NewsTime"));
	    			int num = this.find(pageList,tmp.getDate("NewsTime"));
	    			if(num == 0){
	    				tmp.set("num", null);
	    			}
	    			else{
	    				tmp.set("num", num);
	    			}
	    			PageList.add(tmp);
	    		}	    		
	    		map.put("official", PageList);
//	    		System.out.println(map.get("official").size());
//	    			    		  		
//	    		for(PageDao p:map.get("official")){
//	    			System.out.println(p.getDate("NewsTime"));
//	    			System.out.println(p.getInt("num"));
//	    		}
	    		
	    		
	    		List<PageDao> AllList = new ArrayList<>();
	    		for(int i=0;i<time.size();i++){
	    			PageDao tmp = new PageDao();
//	    			System.out.println(time.get(i));	    			
	    			tmp.set("NewsTime", time.get(i));	    			
//	    			System.out.println(tmp.getDate("NewsTime"));
	    			int num = this.find(allList,tmp.getDate("NewsTime"));
	    			if(num == 0){
	    				tmp.set("num", null);
	    			}
	    			else{
	    				tmp.set("num", num);
	    			}
	    			AllList.add(tmp);
	    		}	    		
	    		map.put("all", AllList);
//	    		System.out.println(map.get("all").size());
//	    			    		  		
//	    		for(PageDao p:map.get("all")){
//	    			System.out.println(p.getDate("NewsTime"));
//	    			System.out.println(p.getInt("num"));
//	    		}
	    		
	    		

	    		
/*	    		for(PageDao p:webList){
	    			System.out.println(p.getDate("NewsTime"));
	    			System.out.println(p.getInt("num"));
	    		}
	    		System.out.println("===============");
	    		for(PageDao p:pageList){
	    			System.out.println(p.getDate("NewsTime"));
	    			System.out.println(p.getInt("num"));
	    		}
	    		System.out.println("===============");
	    		for(PageDao p:allList){
	    			System.out.println(p.getDate("NewsTime"));
	    			System.out.println(p.getInt("num"));
	    		}
	    		System.out.println("===============");*/	    			    		
	    		
/*
	    		String sql = "SELECT DATE_FORMAT(NewsTime, '%Y-%m-%d') time, sum(num) sum_num " +
	    				"FROM (select * from basicinfo where ZoneName='埃及中非泰达产业园' " +
	    				"and source='百度' ORDER BY NewsTime) tmp GROUP BY DATE_FORMAT( NewsTime, '%Y-%m-%d' )";
	    		System.out.println(sql);
//	    		List<PageDao> webList = new ArrayList<>();	    		
//	    		PageDao page = new PageDao();
//	    		webList = page.dao.find(sql);
	    		List<Record> webList = Db.find(sql);
	    		
	    		
	    		sql = "SELECT DATE_FORMAT(NewsTime, '%Y-%m-%d') time, sum(num) sum_num " +
	    				"FROM (select * from basicinfo where ZoneName='埃及中非泰达产业园' " +
	    				"and source='官方网站' ORDER BY NewsTime) tmp GROUP BY DATE_FORMAT( NewsTime, '%Y-%m-%d' )";
	    		System.out.println(sql);
//	    		List<PageDao> pageList = new ArrayList<>();
//	    		pageList = page.dao.find(sql);
	    		List<Record> pageList = Db.find(sql);
	    		
	    		
	    		sql = "SELECT DATE_FORMAT(NewsTime, '%Y-%m-%d') time, sum(num) sum_num " +
	    				"FROM (select * from basicinfo where ZoneName='埃及中非泰达产业园' " +
	    				"ORDER BY NewsTime) tmp GROUP BY DATE_FORMAT( NewsTime, '%Y-%m-%d' )";
	    		System.out.println(sql);
//	    		List<PageDao> allList = new ArrayList<>();
//	    		allList = page.dao.find(sql);
	    		List<Record> allList = Db.find(sql);
	    		
	    		for(Record p:webList){
	    			if(p.getDate("time")==null){
	    				p.set("time", new java.util.Date(2002,12,31));
	    			}
	    		}
	    		for(Record p:pageList){
	    			if(p.getDate("time")==null){
	    				p.set("time", new java.util.Date(2002,12,31));
	    			}
	    		}
	    		for(Record p:allList){
	    			if(p.getDate("time")==null){
	    				p.set("time", new java.util.Date(2002,12,31));
	    			}
	    		}
	    		
	    		for(Record p:webList){
	    			System.out.println(p.getDate("time"));
	    			System.out.println(p.getStr("sum_num"));
	    		}
	    		System.out.println("===============");
	    		for(Record p:pageList){
	    			System.out.println(p.getDate("time"));
	    			System.out.println(p.getStr("sum_num"));
	    		}
	    		System.out.println("===============");
	    		for(Record p:allList){
	    			System.out.println(p.getDate("time"));
	    			System.out.println(p.getStr("sum_num"));
	    		}
	    		System.out.println("===============");
	    		
	    		
//	    		List<PageDao> Web = new ArrayList<>();
//	    		for(int i=0;i<allList.size();i++){
//	    			PageDao tmp = new PageDao();
//	    			System.out.println(allList.get(i).getDate("NewsTime"));
////	    			if(allList.get(i).getDate("NewsTime")==null){
//	    				tmp.set("NewsTime", new java.util.Date(2002,12,31));
////	    			}
////	    			else{
////	    				tmp.set("NewsTime", allList.get(i).getDate("NewsTime"));
////	    			}
//	    			System.out.println(tmp.getDate("NewsTime"));
//	    			int num = this.find(webList,tmp.getDate("NewsTime"));
//	    			if(num == 0){
//	    				tmp.set("num", null);
//	    			}
//	    			else{
//	    				tmp.set("num", num);
//	    			}
//	    			Web.add(tmp);
//	    		}	    		
//	    		map.put("baidu", Web);
//	    		System.out.println(map.get("baidu").size());
//	    		
//	    		
////	    		map.put("official", pageList);
////	    		System.out.println(map.get("official").size());
//	    		
//	    		for(PageDao p:map.get("baidu")){
//	    			System.out.println(p.getDate("NewsTime"));
//	    			System.out.println(p.getStr("NewsTitle"));
//	    		}
 *  *
 */
	    		
	    		if(map != null)
	    			renderJson(map);

	    	}      
	    }

		private int find(List<PageDao> list, java.util.Date date) {
			// TODO Auto-generated method stub
			int num = 0;
			
			if(date == null){
				for(PageDao p:list){
					if(p.getDate("NewsTime") == null)
						num += p.getInt("num");
				}
			}
			else{
				for(PageDao p:list){
//	    			System.out.println(p.getDate("NewsTime"));
//	    			System.out.println(date);   				    			
					if(p.getDate("NewsTime") == null){
						continue;
					}
					else if(p.getDate("NewsTime").compareTo(date)==0){
						num += p.getInt("num");
					}
				}
			}
			return num;
		}
		
	    public void getDetail()  
	    {
	    	String zone = getPara("zone");
	    	String time = getPara("time");
	    	System.out.println(zone);
	    	System.out.println(time);
	    	if(zone!="default"){
	    		System.out.println("in getDetail");
	    		Map<String, List<PageDao>> map = new HashMap<String,List<PageDao>>();
	    		
	    		String sql="";
	    		if(time.equals("Tue Feb 01 2000 00:00:00 GMT+0800 (中国标准时间)"))
	    			sql = "select * from basicinfo where ZoneName='"+zone+"' and NewsTime is NULL";
	    		else
	    			sql = "select * from basicinfo where ZoneName='"+zone+"' and NewsTime = '" +time+"'";
	    		System.out.println(sql);
	    		List<PageDao> webList = new ArrayList<>();	    		
	    		PageDao page = new PageDao();
	    		webList = page.dao.find(sql);
//	    		System.out.println(webList.size());
//	    		for(PageDao p:webList){
//	    			System.out.println(p.getStr("NewsTitle"));
//	    		}
	    		map.put("list", webList);
	    		if(map!=null)
	    			renderJson(map);
	    	}
	    }

}
