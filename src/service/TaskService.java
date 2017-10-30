package service;

import java.util.List;

import utils.exception.ServiceException;

import model.TaskDao;



public interface TaskService {
	boolean save(TaskDao task) throws ServiceException;
	
	int find(TaskDao task);

	List<TaskDao> findAll();
	
	void remove(int id) throws ServiceException;
}
