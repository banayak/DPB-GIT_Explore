package com.mbusa.dpb.web.delegate;

import java.util.List;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.helper.MenuNode;

public class CommonDelegate {
	
	private LocalServiceFactory local =  new LocalServiceFactory();
	/**
	 * @param uRole
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public List<MenuNode> getMenuItems(String uRole) throws BusinessException,TechnicalException{
		List<MenuNode> mList = null;
		try {
			mList = local.getMasterDataService().getMenuItems(uRole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mList;
	}

}
