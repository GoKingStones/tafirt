package web;

import java.util.List;

import javax.annotation.Resource;

import model.Baidu;


import static utils.Const.GENERAL_PAGE_INSERT;
import static utils.Const.GENERAL_PAGE_UPDATE;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;



public class TestPipeline implements Pipeline {
	@Resource
	private Baidu pageDao;

	@Override
	public void process(ResultItems resultItems, Task task) {
		System.out.println("in pipeline");
		List<Baidu> insert_list = resultItems.get(GENERAL_PAGE_INSERT);
		List<Baidu> update_list = resultItems.get(GENERAL_PAGE_UPDATE);
		System.out.println("insert list size: "+insert_list.size());
		System.out.println("update list size: "+update_list.size());
		for(Baidu generalPage:insert_list){
//			System.out.println(generalPage.get("ZoneName"));
//			System.out.println(generalPage.get("signature"));
//			System.out.println(generalPage.get("subsig"));
//			System.out.println(generalPage.get("num"));
//			System.out.println(generalPage.get("SourceURL"));
//			System.out.println(generalPage.get("NewsTitle"));
//			System.out.println(generalPage.get("NewsTime"));
//			System.out.println(generalPage.get("task"));
//			System.out.println(generalPage.get("body"));
			
			if (null != generalPage){
				System.out.println("in pipeline insert");
				pageDao.dao.insert(generalPage);
			}
		}
		for(Baidu generalPage:update_list){
//			System.out.println(generalPage.get("ZoneName"));
//			System.out.println(generalPage.get("signature"));
//			System.out.println(generalPage.get("subsig"));
			//System.out.println(generalPage.get("num"));
			//System.out.println(generalPage.get("id"));
//			System.out.println(generalPage.get("SourceURL"));
//			System.out.println(generalPage.get("NewsTitle"));
//			System.out.println(generalPage.get("NewsTime"));
//			System.out.println(generalPage.get("task"));
//			System.out.println(generalPage.get("body"));
			
			if (null != generalPage){
				System.out.println("in pipeline update");
				int num = generalPage.get("num");
				System.out.println(num);
				Long id = generalPage.get("id");
				System.out.println(id);
				String sql = "update baidu set num = '" + num +"'where id = '" + id +"'";
				pageDao.dao.update(generalPage,sql);
			}
		}
	}
}
