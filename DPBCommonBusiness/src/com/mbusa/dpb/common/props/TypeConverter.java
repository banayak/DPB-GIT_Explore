/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				: Services  
 * Author					: syntel
 * Program Name				: TypeConverter
 * Program Version			: 1.0
 * Program Description		: 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 *    	  	  1.0        First Draft
 * ------------------------------------------------------------------------------------------
 *********************************************************************************************/

package com.mbusa.dpb.common.props;

/**
 * @version 
 */
public interface TypeConverter {
    /**
     * @return
     */
    String getName();
    
    /**
     * @param obj
     * @return
     */
    Object convert(Object obj);
}
