package com.mbusa.dpb.common.bo;

import java.util.List;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;



public interface IDashBoardManager {

	public List<DPBProcessLogBean> getLastTwoDaysProcesses();
	
}
