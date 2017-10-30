package model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class TaskDao extends Model<TaskDao> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TaskDao dao = new TaskDao();

	public boolean insert(TaskDao task) {
//		System.out.println("task unexsisted inserting");
		dao.clear();
		dao._setAttrs(task);
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("url",  task.get("url"));
//		map.put("time",  task.get("time"));
//		map.put("name",  task.get("name"));
//		map.put("id", 1);
//		
//		System.out.println(map.get("url"));
//		System.out.println(map.get("name"));
//		System.out.println(map.get("time"));
//		System.out.println(map.get("id"));
		
//		dao._setAttrs(map);
//		System.out.println("===================");
		

		boolean result = dao.save();
		
//		Record record = new Record().set("url", task.get("url")).set("name", task.get("name")).set("time", task.get("time"));
//		Db.save("task", record);
//		System.out.println("===================");
//		System.out.println(dao.getStr("time"));
//		System.out.println(dao.getStr("name"));
		
		return result;
	}

	public List<TaskDao> select(String url) {
		String sql = "select * from task where url = '" + url +"'";
//		System.out.println(sql);
		return dao.find(sql);
	}
	
	public TaskDao selectLatest(String url){
		List<TaskDao> list = dao.select(url);
		TaskDao task;
		if(list.size()>1){
			task = list.get(list.size()-1);
		}
		else if(list.size()==1){
			task = list.get(0);
		}
		else {
			task = null;
		}
		return task;
	}
	
	public Integer selectMaxID(String url){
		String sqlString = "select max(id) from task where url = '" + url +"'";
		Integer ret = Db.queryLong(sqlString).intValue();
		if(ret == null)
			ret = 0;
		return ret;
	}
	
	public List<TaskDao> selectAll() {
		return dao.find("select * from task");
	}

	public TaskDao select(int id) {
		return dao.findById(id);
	}

	public boolean delete(int id) {
		return dao.deleteById(id);
	}

	public boolean updateTime(TaskDao task, TaskDao exists) {
		boolean flag = false;
		Timestamp time = task.get("time");
		// 数据库中id是unsigned int auto increment，故取出类型为 Long,
		// 数据库中id是int auto increment，故取出类型为int.(unsigned表示是否有符号位)
		Long id = exists.get("id");
//		System.out.println(id);
		
		String sql = "update task set time = '" + time +"'where id = '" + id +"'";
		int num = Db.update(sql);
		if(num != 0)
			flag = true;
		return flag;
	}
}
