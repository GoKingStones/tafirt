package config;  
  
import interceptor.ExceptionIntoLogInterceptor;
import model.Baidu;
import model.PageDao;
import model.TaskDao;
import model.Websites;

import com.jfinal.config.Constants;  
import com.jfinal.config.Handlers;  
import com.jfinal.config.Interceptors;  
import com.jfinal.config.JFinalConfig;  
import com.jfinal.config.Plugins;  
import com.jfinal.config.Routes;  
import com.jfinal.kit.PropKit;  
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;  
import com.jfinal.plugin.c3p0.C3p0Plugin;  
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.template.Engine;

import controller.CorrectController;
import controller.CrawController;
import controller.DisplayController;
import controller.SourceController;
import controller.TestController;
  
public class BaseConfig extends JFinalConfig {  
      
    @Override  
    /**
     * 配置常量，必须
     */
    public void configConstant(Constants me) {  
        // TODO Auto-generated method stub  
    	// 数据库配置文件
        PropKit.use("demo_config.txt");  
        me.setDevMode(PropKit.getBoolean("devMode", false));  
    }  
    @Override  
    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {  
        // TODO Auto-generated method stub  
          
    }  
    @Override  
    /**
     * 配置拦截器
     */
    public void configInterceptor(Interceptors me) {  
        // TODO Auto-generated method stub  
          
    	me.add(new ExceptionIntoLogInterceptor());
    }  
    @Override  
    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {  
        // TODO Auto-generated method stub  
    	// 配置C3p0数据库连接池插件，注意需要两个jar,mchange-commons.jar,c3p.jar
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());  
        me.add(c3p0Plugin);  
          
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);  
        me.add(arp);  
        
        // 方式一： 直接配置数据表映射
        arp.addMapping("officialwebsite", Websites.class);
        arp.addMapping("basicinfo", "id", PageDao.class);
        arp.addMapping("task", "id", TaskDao.class);
        //arp.addMapping("baidu", "id", Baidu.class);
        
        // 配置定时任务
        // cron表达式依次表示分、时、天、月、周
        Cron4jPlugin cp = new Cron4jPlugin();
        cp.addTask("* 0 15 * *", new CrawController());
        me.add(cp);
    }  
    @Override
    /**
     * 配置路由
     */
    public void configRoute(Routes me) {  
        // TODO Auto-generated method stub  
        /*
         * 啦啦啦，需要修改
         */
        me.add("/craw", CrawController.class);  
        me.add("/correct", CorrectController.class); 
        me.add("/source", SourceController.class);
        me.add("/display", DisplayController.class);
        me.add("/test", TestController.class);
    }
	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub
		
	}  
}  
