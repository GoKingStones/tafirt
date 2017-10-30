package controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import model.PageDao;

import com.jfinal.core.Controller;

import simhash.SimHasher;
import utils.SourceNER;
import utils.exception.ServiceException;

import static utils.Const.DEFAULT_SIM_DISTANCE;

public class SourceController  extends Controller{
	/**
	 * 
	 * @throws ServiceException
	 */
	public void index() throws ServiceException{
		System.out.println("easy get the source");
		PageDao pageDao = new PageDao();
		String sql = "select * from basicinfo";
		List<PageDao> pd = pageDao.dao.find(sql);
		System.out.println(pd.size());
		for(PageDao p:pd){
			String source ="";
			String title = p.get("NewsTitle");
			
/*			if(title.contains("--")){
				String tmpString = title.replace(" ", "");
				source = tmpString.substring(tmpString.lastIndexOf("--")+2);
			}
			else if(title.contains("-")){
				String tmpString = title.replace(" ", "");
				source = tmpString.substring(tmpString.lastIndexOf("-")+1);
			}
			else if(title.contains("_")){
				String tmpString = title.replace(" ", "");
				source = tmpString.substring(tmpString.lastIndexOf("_")+1);
			}
*/			
			SourceNER sourceNER = new SourceNER();
			List<String> result = new ArrayList<>();
			result = sourceNER.source(title);
			source = result.toString().replace("[", "").replace("]", "").replace(" ", "");
						
			Long id = p.get("id");
			sql = "update basicinfo set SourceApartment = '"+source+"'where id = '" + id +"'";
			boolean flag = pageDao.dao.update(p,sql);
			if(!flag){
				new ServiceException("update fail");
				break;
			}
			
		}
		
		renderText("OK");
	}
}
