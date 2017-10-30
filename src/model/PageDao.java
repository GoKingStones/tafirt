package model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simhash.SimHasher;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import static utils.Const.DEFAULT_SIM_DISTANCE;

public class PageDao extends Model<PageDao> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final PageDao dao = new PageDao();
	
	public boolean insert(PageDao generalPage) {
//		System.out.println("in pagedao insert");

		
		String ZoneName = generalPage.get("ZoneName");
		String NewsTitle = generalPage.get("NewsTitle");
//		String NewsTime = generalPage.get("NewsTime");
//		String source = generalPage.get("source");
//		String SourceURL = generalPage.get("SourceURL");
//		BigInteger signature = generalPage.get("signature");
//		String subsig = generalPage.get("subsig");
//		String body = generalPage.get("body");	
//		int task = generalPage.get("task");
//		int num = generalPage.get("num");
//		
		System.out.println(ZoneName+" "+NewsTitle);
//		System.out.println(NewsTitle);
//		System.out.println(NewsTime);
//		System.out.println(source);
//		System.out.println(SourceURL);
//		System.out.println(signature);
//		System.out.println(subsig);
//		System.out.println(body);	
//		System.out.println(task);
//		System.out.println(num);
//		
//		Record record = new Record().set("ZoneName", ZoneName).set("NewsTitle", NewsTitle).set("NewsTime", NewsTime)
//				.set("source", source).set("SourceURL", SourceURL).set("signature", signature).set("subsig", subsig)
//				.set("body", body).set("task", task).set("num", num);
//		
//		System.out.println(record.get("ZoneName"));
//		System.out.println(record.get("NewsTitle"));
//		System.out.println(record.get("NewsTime"));
//		System.out.println(record.get("source"));
//		System.out.println(record.get("SourceURL"));
//		System.out.println(record.get("signature"));
//		System.out.println(record.get("subsig"));
//		System.out.println(record.get("body"));		
//		System.out.println(record.get("task"));
//		System.out.println(record.get("num"));
//
//		boolean result = Db.save("basicinfo", record);


		dao.clear();
		dao._setAttrs(generalPage);
//		System.out.println(dao.get("subsig"));
		boolean result = dao.save();
//		System.out.println("save page end");
//		System.out.println(result);
		return result;
	}
	
	public boolean update(PageDao generalPage, String sql) {
		// TODO Auto-generated method stub
//		System.out.println("in pagedao update");
		
		boolean flag = false;
//		int num = generalPage.get("num");
//		Long id = generalPage.get("id");
//		System.out.println(id);
//		String sql = "update task set num = '" + num +"'where id = '" + id +"'";
		int ret = Db.update(sql);
		if(ret != 0)
			flag = true;
		
//		Record record = new Record().set("num", generalPage.get("num"));
//		boolean result = Db.update("basicinfo", record);
//		dao._setAttrs(generalPage);
//		boolean result = dao.update();
		
//		System.out.println("update page end");
//		System.out.println(flag);
		return flag;
	}

	public  Integer getMaxId(){
		Integer ret;
		String sql = "select max(id) from basicinfo";
		ret = Db.queryInt(sql);
		if(ret == null)
			ret = 0;
		return ret;
	}
	
	public boolean select(String url) {
		String sql = "select * from basicinfo where SourceURL = '" + url +"'";
		boolean flag = true;
		List<PageDao> pd = dao.find(sql);
		if(pd==null || pd.size()==0)
			flag = false;
		return flag;
	}
	
	public int[] insert(List<PageDao> pages) {
//		boolean result = false;
//		for(PageDao p : pages){
//			dao.setAttrs(p);
//			result = dao.save();
//			if(!result){
//				System.out.println("错误插入第 "+ pages.indexOf(p) +"条数据");
//				break;
//			}
//		}
//		return result;
		
		int count = getMaxId();
		for(PageDao p : pages){
			count = count + 1;
			p.set("id", count);
		}
		String sql = "insert into basicinfo(id, ZoneName, NewsTitle, NewsTime, SourceURL, body, task, num) values(?,?,?,?,?,?,?,?)";
		return Db.batch(sql, "id, ZoneName, NewsTitle, NewsTime, SourceURL, body, task, num", pages, pages.size());
	}
	
	public int deleteByTask(int taskID) {
		return Db.update("delete from basicinfo where task = ?",taskID);
	}

	/**
	 * 查找数据库中是否已经有相同的网页
	 * 默认distance为3 
	 *
	 * @param subsig
	 * @return
	 */
/*
	public boolean FindSig(String subsig) {
		System.out.println("finding sig " + subsig);
		boolean flag = false;

		String sql = "select subsig from basicinfo";
		List<PageDao> sigList = dao.find(sql);
		Map<Integer, ArrayList<String>> sig = new HashMap<>();		
		ArrayList<String> l1 = new ArrayList<>();
		ArrayList<String> l2 = new ArrayList<>();
		ArrayList<String> l3 = new ArrayList<>();
		ArrayList<String> l4 = new ArrayList<>();
		for(PageDao pd : sigList){
			l1.add(pd.getStr("subsig").split(",")[0]);
			l2.add(pd.getStr("subsig").split(",")[1]);
			l3.add(pd.getStr("subsig").split(",")[2]);
			l4.add(pd.getStr("subsig").split(",")[3]);
		}		
		sig.put(0, l1);
		sig.put(1, l2);
		sig.put(2, l3);
		sig.put(3, l4);
		System.out.println(sig.get(0));
		if(sig.get(0).contains(subsig.split(",")[0]))
			flag = true;
		else if(sig.get(1).contains(subsig.split(",")[1]))
			flag = true;
		else if(sig.get(2).contains(subsig.split(",")[2]))
			flag = true;
		else if(sig.get(3).contains(subsig.split(",")[3]))
			flag = true;
		else {
			flag = false;
		}
		System.out.println(flag);
				
		return flag;
	}
*/
	/**
	 * 查找数据库中是否已经有相同的网页
	 * 默认distance为3 
	 *
	 * @param subsig
	 * @return
	 */
	public List<PageDao> selectPage(String subsigStr, SimHasher sh) {		
		List<PageDao> result = new ArrayList<PageDao>();
		for(String sub : subsigStr.split(",")){
			String sql = "select * from basicinfo where FIND_IN_SET('" + sub +"',subsig)";
//			System.out.println(sql);
		
			List<PageDao> page = dao.find(sql);

			for(PageDao pd:page){
				BigInteger sig = pd.getBigInteger("signature");
				int distance = sh.getHammingDistance(sig);
				if(distance <= DEFAULT_SIM_DISTANCE){
					result.add(pd);
				}
			}
		}
		return result;
	}
}
