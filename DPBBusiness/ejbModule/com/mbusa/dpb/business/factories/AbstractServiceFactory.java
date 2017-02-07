/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: AbstractServiceFactory.java
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

import com.mbusa.dpb.business.view.BonusProcessingBeanLocal;
import com.mbusa.dpb.business.view.DashBoardBeanLocal;
import com.mbusa.dpb.business.view.DefinitionBeanLocal;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.business.view.FileProcessingBeanLocal;
import com.mbusa.dpb.business.view.MasterDataBeanLocal;
import com.mbusa.dpb.business.view.ReportsBeanLocal;
import com.mbusa.dpb.common.exceptions.TechnicalException;


public abstract class AbstractServiceFactory  {
	
	
	public abstract DefinitionBeanLocal getDefinitionService() throws TechnicalException;

	
	public abstract DashBoardBeanLocal getDashBoardService() throws TechnicalException;
	
	
	public abstract FileProcessingBeanLocal getFileProcessingService() throws TechnicalException;
	
	
	abstract public BonusProcessingBeanLocal getBonusProcessingService() throws TechnicalException;
	
	abstract public ReportsBeanLocal getReportsService() throws TechnicalException;
	
	abstract public MasterDataBeanLocal getMasterDataService() throws TechnicalException;
	
	abstract public DpbCommonBeanLocal getDpbCommonService() throws TechnicalException;
}


