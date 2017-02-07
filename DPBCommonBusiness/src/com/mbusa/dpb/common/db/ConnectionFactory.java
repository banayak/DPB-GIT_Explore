package com.mbusa.dpb.common.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mbusa.dpb.common.constant.ILogConstants;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;


public class ConnectionFactory {

	//private static final LOGGER LOGGER = LOGGER.getLOGGER(ConnectionFactory.class);
	private static DPBLog LOGGER = DPBLog.getInstance(ConnectionFactory.class);
	public static String appDbSchema = null;
	static final private String CLASSNAME = ConnectionFactory.class.getName();
	protected String dbJndi;
	static final public String PROP_APP_DB_JNDI = "app.database.jndi";
	static final public String PROP_TASK_DB_JNDI = "task.database.jndi";
	static final public String PROP_APP_DB_SCHEMA = "app.database.schema";
	static final public String PROP_TASK_DB_SCHEMA = "task.database.schema";
	private boolean initialized;
	private static DataSource datasource = null;
	private Context initialContext = null;
	
	static private ConnectionFactory SINGLETON = null;
	
	synchronized static public ConnectionFactory getConnectionFactory() {
		if(SINGLETON == null) {
			SINGLETON = new ConnectionFactory();		
		}
		return SINGLETON;
	}
	
	protected void initialize() throws NamingException  {
		final String methodName = "initialize";
		LOGGER.enter(CLASSNAME, methodName);
		
		dbJndi = PropertyManager.getPropertyManager().getPropertyAsString(PROP_APP_DB_JNDI);
		if(LOGGER.isInfoEnabled()){
			LOGGER.info(ILogConstants.DB_JNDI_NAME+ILogConstants.CONN_JNDI_02_DESC+dbJndi);
		}
		if(dbJndi == null || "".equals(dbJndi)) {
			String errMsg = MessageFormat.format(ILogConstants.CONN_FACTORY_01, new Object[]{PROP_APP_DB_JNDI});
			new TechnicalException("",errMsg);
		}
		appDbSchema = PropertyManager.getPropertyManager().getPropertyAsString(PROP_APP_DB_SCHEMA);
		if(appDbSchema == null || "".equals(appDbSchema)) {
			String errMsg = MessageFormat.format(ILogConstants.CONN_FACTORY_SCHEMA, new Object[]{PROP_APP_DB_JNDI});
			new TechnicalException("",errMsg);
		}
		try {
			initialContext = new InitialContext();
			datasource = (DataSource) initialContext.lookup(dbJndi);
			initialized = true;
		} catch (NamingException exc) {
			LOGGER.error("Lookup fail db " + exc.toString()	+ " The context is " + initialContext);
			DPBExceptionHandler.getInstance().handleException(exc);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}	
	public Connection getConnection() throws SQLException,NamingException {
		final String methodName = "initialize";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		Statement st = null;
		try {
			if(!initialized) { 
				initialize(); 
			}
			conn = datasource.getConnection();
			st = conn.createStatement();		
			st.execute("set current schema = "+appDbSchema);
		} catch (SQLException exc) {			
			DPBExceptionHandler.getInstance().handleException(exc);			
		}finally {
			try {
				if (st != null) {
					st.close();
				}				
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conn;
	}
	public static Connection getConnectionForTask() throws SQLException,NamingException {
		final String methodName = "initialize";
		LOGGER.enter(CLASSNAME, methodName);		
		Context initialContext = null;
		Connection conn = null;
		String taskDbJndi;
		String taskDbSchema;
		DataSource taskDataSource = null;
		try {
			taskDbJndi = PropertyManager.getPropertyManager().getPropertyAsString(PROP_TASK_DB_JNDI);
			initialContext = new InitialContext();
			taskDataSource = (DataSource) initialContext.lookup(taskDbJndi);			
		} catch (NamingException exc) {
			LOGGER.error("Lookup fail for task jndi db " + exc.toString()
					+ " The context is " + initialContext);
			DPBExceptionHandler.getInstance().handleException(exc);
		}
		try {
			taskDbSchema = PropertyManager.getPropertyManager().getPropertyAsString(PROP_TASK_DB_SCHEMA);
			conn = taskDataSource.getConnection();
			Statement st = conn.createStatement();		
			st.execute("set current schema = "+taskDbSchema);
			st.close();	
		} catch (SQLException exc) {
			LOGGER.error(" Fail to connect (task) database " + exc.toString()
					+ " The context is " + initialContext);
			DPBExceptionHandler.getInstance().handleException(exc);			
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conn;
	}
	public Connection getConnection(boolean isScheduler)throws SQLException,NamingException  {
		if(isScheduler){
			return getConnectionForTask();			
		}else{
			return getConnection();
		}
		
	}
}
