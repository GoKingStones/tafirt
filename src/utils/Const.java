package utils;

public interface Const {
	int OK = 0;
	int ERR = 1;
	
	/* retry times when download fails */
	int RETRY_TIMES = 3;
	int NO_SLEEP_TIME = 0;

	String GENERAL_PAGE_INSERT="page_insert";
	String GENERAL_PAGE_UPDATE="page_update";


	int DEFAULT_THREADS_NUM = 10;
	int DEFAULT_SIM_DISTANCE = 3;

	String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	String URL_SPLITTER=",";
}
