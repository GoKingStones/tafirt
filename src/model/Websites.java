package model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Websites extends Model<Websites> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Websites dao = new Websites();
	public Websites select(String url) {
		String sql = "select * from officialwebsite where url = " + url +"'";
		System.out.println(sql);
		return dao.findFirst(sql);
	}

	public List<Websites> selectAll() {
		return dao.find("select * from officialwebsite");
	}
}
