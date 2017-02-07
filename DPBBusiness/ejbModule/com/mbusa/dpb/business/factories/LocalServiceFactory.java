/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: LocalServiceFactory.java
 * Program Version			: 1.0
 * Program Description		: This class is used to get the services.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Sep 03, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.factories;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.mbusa.dpb.business.view.BonusProcessingBeanLocal;
import com.mbusa.dpb.business.view.DashBoardBeanLocal;
import com.mbusa.dpb.business.view.DefinitionBeanLocal;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.business.view.FileProcessingBeanLocal;
import com.mbusa.dpb.business.view.MasterDataBeanLocal;
import com.mbusa.dpb.business.view.ReportsBeanLocal;
import com.mbusa.dpb.common.exceptions.IExceptionIDs;
import com.mbusa.dpb.common.exceptions.TechnicalException;



public class LocalServiceFactory extends AbstractServiceFactory {

	//ejblocal:com.syntel.calculator.view.CalculatorBeanLocal
	//ejblocal:com.mbusa.dpb.view.DefinitionBeanLocal
	//ejblocal:com.mbusa.dpb.view.FileProcessingBeanLocal
	//ejblocal:com.mbusa.dpb.view.BonusProcessingBeanLocal
	//ejblocal:com.mbusa.dpb.view.ReportsBeanLocal
	//ejblocal:com.mbusa.dpb.view.MasterInfoBeanLocal
	//ejblocal:com.mbusa.dpb.view.DashBoardBeanLocal
	
	
	
	public static final String MASTER_INFO_SERVICE_JNDI 		= "ejblocal:com.mbusa.dpb.business.view.MasterDataBeanLocal";	
	private static final String DEF_SERVICE_JNDI 				= "ejblocal:com.mbusa.dpb.business.view.DefinitionBeanLocal";
	private static final String DASHBOARD_SERVICE_JNDI 			= "ejblocal:com.mbusa.dpb.business.view.DashBoardBeanLocal";
	private static final String FILE_PROCESSING_SERVICE_JNDI 	= "ejblocal:com.mbusa.dpb.business.view.FileProcessingBeanLocal";
	private static final String BONUS_PROCESSING_SERVICE_JNDI	= "ejblocal:com.mbusa.dpb.business.view.BonusProcessingBeanLocal";
	private static final String REPORT_SERVICE_JNDI 			= "ejblocal:com.mbusa.dpb.business.view.ReportsBeanLocal";
	private static final String COMMON_SERVICE_JNDI 			= "ejblocal:com.mbusa.dpb.business.view.DpbCommonBeanLocal";
	
	
	public DefinitionBeanLocal getDefinitionService() throws TechnicalException {
		DefinitionBeanLocal defLocalService;

		try {
			InitialContext ic = new InitialContext();
			Object objRef = ic.lookup(DEF_SERVICE_JNDI);
			defLocalService = (DefinitionBeanLocal) PortableRemoteObject.narrow(objRef, DefinitionBeanLocal.class);		
						
		} catch (NamingException ne) {
			// TODO Auto-generated catch block
			ne.printStackTrace();
			throw new TechnicalException(IExceptionIDs.BEAN_NOT_AVAILABLE,"Exception while creating definition Bean", ne);
		}
		return defLocalService;
	}
	

	public DashBoardBeanLocal getDashBoardService() throws TechnicalException {
		DashBoardBeanLocal dashboradService;

		try {
			InitialContext ic = new InitialContext();
			Object objRef = ic.lookup(DASHBOARD_SERVICE_JNDI);
			dashboradService = (DashBoardBeanLocal) PortableRemoteObject.narrow(objRef, DashBoardBeanLocal.class);				
			
		} catch (NamingException ce) {
			throw new TechnicalException(IExceptionIDs.BEAN_NOT_AVAILABLE,"Exception while creating dashboard Bean", ce);	
		}
		return dashboradService;
	}

	public FileProcessingBeanLocal getFileProcessingService() throws TechnicalException {
		FileProcessingBeanLocal fileService;

		try {
			InitialContext ic = new InitialContext();
			Object objRef = ic.lookup(FILE_PROCESSING_SERVICE_JNDI);
			fileService = (FileProcessingBeanLocal) PortableRemoteObject.narrow(objRef, FileProcessingBeanLocal.class);				
			
		} catch (NamingException ce) {
			throw new TechnicalException(IExceptionIDs.BEAN_NOT_AVAILABLE,"Exception while creating file processing Bean", ce);	
		}
		return fileService;
	}
	
	public BonusProcessingBeanLocal getBonusProcessingService() throws TechnicalException {
		BonusProcessingBeanLocal bonusService;

		try {
			InitialContext ic = new InitialContext();
			Object objRef = ic.lookup(BONUS_PROCESSING_SERVICE_JNDI);
			bonusService = (BonusProcessingBeanLocal) PortableRemoteObject.narrow(objRef, BonusProcessingBeanLocal.class);				
			
		} catch (NamingException ce) {
			throw new TechnicalException(IExceptionIDs.BEAN_NOT_AVAILABLE,"Exception while creating bonus processing Bean", ce);	
		}
		return bonusService;
	}
	public ReportsBeanLocal getReportsService() throws TechnicalException {
		ReportsBeanLocal reportService;

		try {
			InitialContext ic = new InitialContext();
			Object objRef = ic.lookup(REPORT_SERVICE_JNDI);
			reportService = (ReportsBeanLocal) PortableRemoteObject.narrow(objRef, ReportsBeanLocal.class);				
			
		} catch (NamingException ce) {
			throw new TechnicalException(IExceptionIDs.BEAN_NOT_AVAILABLE,"Exception while creating reports Bean", ce);	
		}
		return reportService;
	}
	public MasterDataBeanLocal getMasterDataService() throws TechnicalException {
		MasterDataBeanLocal masterService;

		try {
			InitialContext ic = new InitialContext();
			Object objRef = ic.lookup(MASTER_INFO_SERVICE_JNDI);
			masterService = (MasterDataBeanLocal) PortableRemoteObject.narrow(objRef, MasterDataBeanLocal.class);		
						
		} catch (NamingException ne) {
			// TODO Auto-generated catch block
			ne.printStackTrace();
			throw new TechnicalException(IExceptionIDs.BEAN_NOT_AVAILABLE,"Exception while creating master data Bean", ne);
		}
		return masterService;
	}
	
	public DpbCommonBeanLocal getDpbCommonService() throws TechnicalException {
		DpbCommonBeanLocal commonService;
		try {
			InitialContext ic = new InitialContext();
			Object objRef = ic.lookup(COMMON_SERVICE_JNDI);
			commonService = (DpbCommonBeanLocal) PortableRemoteObject.narrow(objRef, DpbCommonBeanLocal.class);				
			
		} catch (NamingException ce) {
			throw new TechnicalException(IExceptionIDs.BEAN_NOT_AVAILABLE,"Exception while creating common Bean", ce);	
		}
		return commonService;
	}
}
