package controller;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import model.PageDao;

import com.jfinal.core.Controller;

import simhash.SimHasher;
import utils.exception.ServiceException;

import static utils.Const.DEFAULT_SIM_DISTANCE;

public class CorrectController  extends Controller{
	/**
	 * 
	 * @throws ServiceException
	 */
	public void index() throws ServiceException{
		System.out.println("correcting the subsig");
		PageDao pageDao = new PageDao();
		String sql = "select * from basicinfo";
		List<PageDao> pd = pageDao.dao.find(sql);
		System.out.println(pd.size());
		for(PageDao p:pd){
			String subsig = p.get("subsig");
			if(subsig.split(",").length==3){
				Long id = p.get("id");
				BigInteger sig = p.getBigInteger("signature");
				String txt = p.getStr("body");
				System.out.println(id);
				System.out.println(sig);
				SimHasher sh = new SimHasher(txt);
				BigInteger signature = sh.getSignature();
				System.out.println(signature);
				// simhash distance设置为3
				List<BigInteger> sub = sh.subByDistance(sh, DEFAULT_SIM_DISTANCE);
				String subsigStr = sub.toString().replace("[", "").replace("]", "").replace(" ", "");
				System.out.println(subsigStr);
				sql = "update basicinfo set subsig = '"+subsigStr+"'where id = '" + id +"'";
				boolean flag = pageDao.dao.update(p,sql);
				if(!flag){
					new ServiceException("update fail");
					break;
				}
			}
		}
		
		renderText("OK");
	}
}
