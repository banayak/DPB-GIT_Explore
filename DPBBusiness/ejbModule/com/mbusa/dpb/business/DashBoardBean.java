package com.mbusa.dpb.business;

import com.mbusa.dpb.business.view.DashBoardBeanLocal;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class DashBoardBean
 */
@Stateless
@Local(DashBoardBeanLocal.class)
public class DashBoardBean implements DashBoardBeanLocal {

    /**
     * Default constructor. 
     */
    public DashBoardBean() {
        // TODO Auto-generated constructor stub
    }

}
