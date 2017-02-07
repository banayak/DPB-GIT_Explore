
package com.mbusa.dpb.common.util;

import com.mbusa.dpb.common.props.PropertyManager;



/**
 * This class {@link BusinessDelegate} is used to delegate all business unit releted task
 * to business layer.
 * 
 * This includes process server invocation for ITP Business Unit
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------

 *------------------------------------------------------------------------------------------
 */
public class MasterInfoLookup {

	

	static final private String CLASSNAME = MasterInfoLookup.class.getName();

	
	static final PropertyManager PROP_MNGR = PropertyManager.getPropertyManager();

	
	
	//public static final String CACHE_NAME = MasterInfoLookup.class.getName();
	
	
	private static final MasterInfoLookup SINGLETON = new MasterInfoLookup();
	
	public static final MasterInfoLookup INSTANCE = SINGLETON;
	
	//protected static IMasterInformationService MASTERINFO_BEAN = null;
	
	//protected static SRTCacheManager CACHE_MNGR = SRTCacheManager.getInstance();
	

	static final private String BUS_CATEGORIES_CKEY = "BUSINESS_CATEGORIES";
	static final private String LANGUAGE_IDS_CKEY = "LANGUAGE_IDS";
	
	protected static final Object SUPLR_ROLE_CACHE_LOCK = new Object();
	static final String STRUTS_RESOURCE_BUNDLE_PATH = "org.apache.struts.action.MESSAGE";
	
	/**
	 * Singleton
	 * 
	 * 
	 * @return MasterInfoLookup
	 */
	public static final MasterInfoLookup getInstance() {
		return SINGLETON;
	}
	protected static final Object CNTRY_LIST_CACHE_LOCK = new Object(); 
//	protected IMasterInformationService service = null;
	
	/**
	 * 
	 * 
	 * 
	 * @return IMasterInformationService
	 */
	/*public  getMasterInformationService() {
		String methodName = "getMasterInformationService()";
		LOGGER.entering(CLASSNAME, methodName);
		try {
			if(service == null) {
				IMasterInformationService lService = new LocalServiceFactory().getMasterInformationService();
				synchronized(this) {
					if(service == null) {
						service = lService;
					}
				}
			}
		} catch (TechnicalException te) {
			LOGGER.severe(CLASSNAME+" Method Name ="+methodName,""+te.getMessage(),te);
			throw new ServiceException(te);
		}
		LOGGER.exiting(CLASSNAME, methodName);
		return service;
	}
	*/
	/**
	 * This method fetches all countres.Country object contains the set of
	 * States.
	 * 
	 * 
	 * @return List 
	 * @throws ServiceException
	 */
	/*public List getCountryList() throws ServiceException{
		
		
		try {
			synchronized (CNTRY_LIST_CACHE_LOCK) {
				countryList = (List) CACHE_MNGR.getObject(MasterInfoLookup.CACHE_NAME, MasterInfoLookup.CNTRY_LIST_CKEY);
				if(countryList == null) {
					countryList = getMasterInformationService().getCountryList();
					CACHE_MNGR.putObject(MasterInfoLookup.CACHE_NAME, MasterInfoLookup.CNTRY_LIST_CKEY, countryList);
				}
			}
		}catch (RemoteException re) {
	
					
		}
		
	
		return countryList;
	}*/
	
}
