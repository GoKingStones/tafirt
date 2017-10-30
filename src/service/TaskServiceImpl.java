package service;

import java.util.List;

import utils.exception.ServiceException;

import model.PageDao;
import model.TaskDao;


public class TaskServiceImpl implements TaskService {
	@Override
	public boolean save(TaskDao task) throws ServiceException {
//		System.out.println("saving");
		TaskDao exists = TaskDao.dao.selectLatest(task.getStr("url"));
//		System.out.println("exists?");
//		if(null!=exists)
//			throw new ServiceException("相同URL的任务记录已经存在,请更改任务");
		if(null!=exists){
			System.out.println("相同URL的任务记录已经存在,在 "+exists.getTimestamp("time")+" 进行过");
			// 为了避免一直不停的插入任务，麻烦
			return TaskDao.dao.updateTime(task, exists);
		}
		else
			return TaskDao.dao.insert(task);
	}

	@Override
	public List<TaskDao> findAll() {
		return TaskDao.dao.selectAll();
	}

	@Override
	public void remove(int id) throws ServiceException {
		TaskDao task = TaskDao.dao.select(id);
		if(null == task)
			throw new ServiceException("该任务不存在");
		int tId = task.getInt("id");
		TaskDao.dao.delete(id);
		PageDao.dao.deleteByTask(tId);
	}

	@Override
	public int find(TaskDao task) {
		String url = task.getStr("url");
		int id = TaskDao.dao.selectMaxID(url);
		return id;
	}
}
