package com.mbusa.dpb.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.constant.IReportQueryConstants;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.domain.BlockedVehicle;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.DealerBonusVehReport;
import com.mbusa.dpb.common.domain.DealerVehicleReport;
import com.mbusa.dpb.common.domain.DleRsrvSumRptBean;
import com.mbusa.dpb.common.domain.ReportContentDefinitionBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.ReportQuery;
import com.mbusa.dpb.common.domain.RetailDate;
import com.mbusa.dpb.common.domain.RetailsAndPaymentsReport;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
//import com.mbusa.dpb.common.constant.IDefinitionQueryConstants;
public class ReportsDAOImpl implements IReportsDAO {

	private ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportsDAOImpl.class);
	private static final  String CLASSNAME = ReportsDAOImpl.class.getName();

	public List<BlockedVehicle> getBlockVehicleReport(String dealerId,String vehicleId,Date fromDate,Date toDate){
		
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean isAloneDate = false;
		boolean isAlonedId =false;
		boolean isAlonevId =false;
		List <BlockedVehicle> bList = null;
		BlockedVehicle bVehicle = null; 
		try {
			con  = conFactory.getConnection();		
			StringBuffer BLOCK_VEHICLE_REPORTS_QUERY = new StringBuffer(IReportQueryConstants.BLOCKING_RPT_QRY);
			//boolean flag = false;
		
			String[] arr=null;
			int commas = 0;
			if(vehicleId.indexOf(",")!=-1)
			{
				for(int i = 0; i < vehicleId.length(); i++) {
				    if(vehicleId.charAt(i) == ',') 
				    	arr=vehicleId.split(",");
				    commas++;
				}
			}
			int lastIndex=0;
			
		
			int i = 1;
			
			if(dealerId!= null && !IConstants.EMPTY_STR.equals(dealerId) && ((vehicleId== null || IConstants.EMPTY_STR.equals(vehicleId)) && (fromDate== null && toDate== null))){
					
						BLOCK_VEHICLE_REPORTS_QUERY.append(" ID_DLR =?");
					
					isAlonedId=true;
				}
			
			 if(vehicleId!= null && !IConstants.EMPTY_STR.equals(vehicleId) && ((dealerId== null || IConstants.EMPTY_STR.equals(dealerId))  && (fromDate== null && toDate== null))){
				
				 
				 if(commas == 0)
					{
					 	BLOCK_VEHICLE_REPORTS_QUERY.append(" NUM_VIN in(?)"); 
					}
					else
					{	//sqlBuilder=new StringBuilder(BLOCK_VEHICLE_REPORTS_QUERY.append(" NUM_VIN  in (?)").toString());
						BLOCK_VEHICLE_REPORTS_QUERY.append(" NUM_VIN in(?)"); 
						for (int j=1; j< arr.length ;j++)
						{
							lastIndex = BLOCK_VEHICLE_REPORTS_QUERY.lastIndexOf("?");
							BLOCK_VEHICLE_REPORTS_QUERY.insert(lastIndex+1, ",?");
						}
						//BLOCK_VEHICLE_REPORTS_QUERY = new StringBuffer(sqlBuilder);
					}
					isAlonevId=true;
					
				
			} if(fromDate!= null && toDate!= null && ((vehicleId== null || IConstants.EMPTY_STR.equals(vehicleId))&& (dealerId== null || IConstants.EMPTY_STR.equals(dealerId)))){
					
					BLOCK_VEHICLE_REPORTS_QUERY.append(" DTE_RTL >= ? AND  DTE_RTL <= ? "); 
					isAloneDate=true;
				}
			
			
			
			if(dealerId!= null && !IConstants.EMPTY_STR.equals(dealerId) && vehicleId!= null && !IConstants.EMPTY_STR.equals(vehicleId) && (fromDate== null && toDate== null)){
				BLOCK_VEHICLE_REPORTS_QUERY.append(" ID_DLR =? AND NUM_VIN in (? )"); 
				if(commas != 0)
					{	//sqlBuilder=new StringBuilder(BLOCK_VEHICLE_REPORTS_QUERY);
					 
						for (int j=1; j< arr.length ;j++)
						{
							lastIndex = BLOCK_VEHICLE_REPORTS_QUERY.lastIndexOf("?");
							BLOCK_VEHICLE_REPORTS_QUERY.insert(lastIndex+1, ",?");
						}
						//BLOCK_VEHICLE_REPORTS_QUERY = new StringBuffer(sqlBuilder);
					}
				isAlonedId=true;	
				isAlonevId=true;
			}
		
		 if((vehicleId!= null && !IConstants.EMPTY_STR.equals(vehicleId)) && (dealerId== null || IConstants.EMPTY_STR.equals(dealerId))  && (fromDate!= null && toDate!= null)){
			
				BLOCK_VEHICLE_REPORTS_QUERY.append("  DTE_RTL >= ? AND  DTE_RTL <= ? AND NUM_VIN  in (?)");
				if(commas != 0)
				{	//sqlBuilder=new StringBuilder(BLOCK_VEHICLE_REPORTS_QUERY);
				 
					for (int j=1; j< arr.length ;j++)
					{
						lastIndex = BLOCK_VEHICLE_REPORTS_QUERY.lastIndexOf("?");
						BLOCK_VEHICLE_REPORTS_QUERY.insert(lastIndex+1, ",?");
					}
					//BLOCK_VEHICLE_REPORTS_QUERY = new StringBuffer(sqlBuilder);
				}
				isAlonevId=true;
				isAloneDate = true;
		} if(fromDate!= null && toDate!= null &&  (vehicleId== null || IConstants.EMPTY_STR.equals(vehicleId))&& (dealerId!= null && !IConstants.EMPTY_STR.equals(dealerId) ) ){
				
				BLOCK_VEHICLE_REPORTS_QUERY.append(" ID_DLR =? AND  DTE_RTL >= ? AND  DTE_RTL <= ? "); 
				isAloneDate = true;
				isAlonedId=true;
			}
			

		if(fromDate!= null && toDate!= null &&  (vehicleId!= null && !IConstants.EMPTY_STR.equals(vehicleId))&& (dealerId!= null && !IConstants.EMPTY_STR.equals(dealerId) )) {
			
			BLOCK_VEHICLE_REPORTS_QUERY.append(" ID_DLR =?  AND  DTE_RTL >= ? AND  DTE_RTL <= ? AND NUM_VIN  in (?)");
			if(commas != 0)
			{//	sqlBuilder=new StringBuilder(BLOCK_VEHICLE_REPORTS_QUERY);
			 
				for (int j=1; j< arr.length ;j++)
				{
					lastIndex = BLOCK_VEHICLE_REPORTS_QUERY.lastIndexOf("?");
					BLOCK_VEHICLE_REPORTS_QUERY.insert(lastIndex+1, ",?");
				}
				//BLOCK_VEHICLE_REPORTS_QUERY = new StringBuffer(sqlBuilder);
			}
			isAloneDate = true;
			isAlonedId =true;
			isAlonevId =true;
		}
		
			BLOCK_VEHICLE_REPORTS_QUERY.append(" ORDER BY ID_DLR,NUM_PO");
			ps = con.prepareStatement(BLOCK_VEHICLE_REPORTS_QUERY.toString());
			
			if(isAlonedId==true && isAloneDate==false && isAlonevId==false )
			{
				ps.setString(1,dealerId);
			}
			if(isAlonedId==false && isAloneDate==false && isAlonevId==true)
			{
				 if(commas == 0)
					{
					 	ps.setString(1,vehicleId);
					}
				 else
				 {
					 for(int k=0;k < arr.length;k++)
					 {
						 ps.setString(k+1, arr[k]);
					 }
				 }
			}
			if(isAlonedId==false && isAloneDate==true && isAlonevId==false )
			{
				ps.setDate(1,fromDate);
				ps.setDate(2,toDate);
			}
			
			if(isAlonedId==true && isAloneDate==false && isAlonevId==true )
			{
				ps.setString(1,dealerId);
				if(commas == 0)
				{
				 	ps.setString(2,vehicleId);
				}
				 else
				 {
					 for(int k=0;k < arr.length;k++)
					 {
						 ps.setString(k+2, arr[k]);
					 }
				 }
				//ps.setString(2,vehicleId);
			}
			
			if(isAlonedId == true && isAloneDate==true && isAlonevId==false )
			{
				ps.setString(1,dealerId);
				ps.setDate(2,fromDate);
				ps.setDate(3,toDate);
			}
			if(isAlonedId==false && isAloneDate==true && isAlonevId==true)
			{
				ps.setDate(1,fromDate);
				ps.setDate(2,toDate);
				if(commas == 0)
				{
				 	ps.setString(3,vehicleId);
				}
				 else
				 {
					 for(int k=0;k < arr.length;k++)
					 {
						 ps.setString(k+3, arr[k]);
					 }
				 }
			}
			
			if(isAlonedId==true && isAloneDate==true && isAlonevId==true)
			{
				ps.setString(1,dealerId); 
				ps.setDate(2,fromDate);
				ps.setDate(3,toDate);
				if(commas == 0)
				{
				 	ps.setString(4,vehicleId);
				}
				 else
				 {
					 for(int k=0;k < arr.length;k++)
					 {
						 ps.setString(k+4, arr[k]);
					 }
				 }
			}
			

			
			rs = ps.executeQuery();	
			bList = new ArrayList<BlockedVehicle>();
	
			while(rs.next()){
				bVehicle =  new BlockedVehicle();
				bVehicle.setSrNo(i);
				String id=rs.getString("ID_DLR");
				bVehicle.setIdDealer(id);
				bVehicle.setPoNumber(rs.getString("NUM_PO"));		
				bVehicle.setUpdatedDate(rs.getDate("DTS_LAST_UPDT"));
				bVehicle.setVinNo(rs.getString("NUM_VIN"));		
				bVehicle.setRetailDate(rs.getDate("DTE_RTL"));
				//int condId = rs.getInt("ID_DPB_CND");
				bVehicle.setTxtBlckReason(rs.getString("reason"));
				bVehicle.setDdrUsecode(rs.getString("CDE_VEH_DDR_USE"));
				bList.add(bVehicle);
				i++;
			}			
		}catch(SQLException  e){
			LOGGER.error("DPB.BLOCK.VEHICLE.LIST.DAO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BLOCK.VEHICLE.LIST.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return bList;
	}
	public Map<String, Object> generateReport(List<String> vehicleType,String viewAccountVin, int dealer, Date fromDate, Date toDate, int year) {
		final String methodName = "generateReport";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		Map<String,Object> genreport =  new HashMap<String,Object>(); 
		List <DealerVehicleReport> dvehList = null;
		DealerVehicleReport dvReport;
		List <DealerBonusVehReport> dbnsList = null;
		StringBuffer BLOCK_VEHICLE_REPORTS_QUERY = null;
		DealerBonusVehReport dbvReport;
		String query="";
		int lastIndex=0;
		try {
				con  = conFactory.getConnection();		
				String [] arr=new String[vehicleType.size()];
				arr=vehicleType.toArray(arr);
				if(( viewAccountVin.equalsIgnoreCase("VIN")) && (dealer <=0 )  &&  fromDate!=null && toDate!=null )
				{
					if(vehicleType.isEmpty() != true )
					{
						BLOCK_VEHICLE_REPORTS_QUERY=new StringBuffer(IReportQueryConstants.LBPREPORT_VINS_QUERY);
						for (int j=1; j< arr.length ;j++)
						{
							lastIndex = BLOCK_VEHICLE_REPORTS_QUERY.lastIndexOf("?");
							BLOCK_VEHICLE_REPORTS_QUERY.insert(lastIndex+1, ",?");
						}			
					}
					else
					{
						BLOCK_VEHICLE_REPORTS_QUERY=new StringBuffer(IReportQueryConstants.DEFAULT_LBPREPORT_VINS_QUERY);
					}
					
				}
				if( ( viewAccountVin.equalsIgnoreCase("V")) && (dealer >0 )  &&  fromDate==null && toDate==null )
				{
					if(vehicleType.isEmpty() != true )
					{
						BLOCK_VEHICLE_REPORTS_QUERY=new StringBuffer(IReportQueryConstants.TOTVEH_REPORT_QUERY);
						for (int j=1; j< arr.length ;j++)
						{
							lastIndex = BLOCK_VEHICLE_REPORTS_QUERY.lastIndexOf("?");
							BLOCK_VEHICLE_REPORTS_QUERY.insert(lastIndex+1, ",?");
						}			
					}
					else
					{
						BLOCK_VEHICLE_REPORTS_QUERY=new StringBuffer(IReportQueryConstants.DEFAULT_TOTVEHREPORT_QUERY);
					}
				}
				if(( viewAccountVin.equalsIgnoreCase("D")) && (dealer >0)  &&  fromDate==null && toDate==null )
				{
					//VIN Query Dealer Quarter
					if(vehicleType.isEmpty() != true )
					{
						BLOCK_VEHICLE_REPORTS_QUERY=new StringBuffer(IReportQueryConstants.LBPREPORT_DEALER_QUERY);
						for (int j=1; j< arr.length ;j++)
						{
							lastIndex = BLOCK_VEHICLE_REPORTS_QUERY.lastIndexOf("?");
							BLOCK_VEHICLE_REPORTS_QUERY.insert(lastIndex+1, ",?");
						}			
					}
					else
					{
						BLOCK_VEHICLE_REPORTS_QUERY=new StringBuffer(IReportQueryConstants.DEFAULT_LBPREPORT_DEALER_QUERY);
					}
					
					if(year==0){
						java.util.Date today = new java.util.Date();
						year = today.getYear();
					}
				}
			
				int i = 0;
				ps = con.prepareStatement(BLOCK_VEHICLE_REPORTS_QUERY.toString());
			
				dvehList = new ArrayList<DealerVehicleReport>();
				dbnsList=new ArrayList<DealerBonusVehReport>();
			
				if(vehicleType.isEmpty() != true)
				{
					if(viewAccountVin.equalsIgnoreCase("V")||  viewAccountVin.equalsIgnoreCase("D") )
					{
						int k=2;
						ps.setInt(1, dealer);
						for(String veh: vehicleType){
							ps.setInt(k, year);
							ps.setString(k+1, veh);
							
							k++;
						}
					}
					if(viewAccountVin.equalsIgnoreCase("VIN"))
					{	int k=3; 
						ps.setDate(1, fromDate);
						ps.setDate(2, toDate);
						for(String veh: vehicleType){
							ps.setString(k, veh);
							k++;
						}
					}
					
				}
				if(vehicleType.isEmpty())
				{
					if(viewAccountVin.equalsIgnoreCase("V")||  viewAccountVin.equalsIgnoreCase("D") )
					{
						ps.setInt(1, dealer);
						ps.setInt(2, year);
					}
					if(viewAccountVin.equalsIgnoreCase("VIN"))
					{
						ps.setDate(1, fromDate);
						ps.setDate(2, toDate);
					}
						
				}
				rs = ps.executeQuery();	
			
				if(viewAccountVin.equalsIgnoreCase("V"))
						{
							while(rs.next()){
								i++;
								dbvReport=new DealerBonusVehReport();
								dbvReport.setSrNo(i);
								dbvReport.setDealerId(rs.getString(1));	
								dbvReport.setDealerName(rs.getString(2));
								if(dbvReport.getDealerId() == null && dbvReport.getDealerName() == null)
								{	dbvReport.setDealerId("");
									dbvReport.setDealerName("");
									dbvReport.setFlag(true);
								}
								dbvReport.setTotalVehicles(rs.getInt(3));
								dbnsList.add(dbvReport);
				
							}
							genreport.put("TOTVEH", dbnsList);
						}
				if(viewAccountVin.equalsIgnoreCase("D"))
				{
					while(rs.next()){
						i++;
						dbvReport=new DealerBonusVehReport();
						dbvReport.setSrNo(i);
						dbvReport.setDealerId(rs.getString(1));	
						dbvReport.setDealerName(rs.getString(2));
						dbvReport.setPoNumber(rs.getString(3));		
						dbnsList.add(dbvReport);
		
					}
					genreport.put("DEALER", dbnsList);
				}
				if(viewAccountVin.equalsIgnoreCase("VIN"))
				{
					while(rs.next()){
						i++;
						
						dvReport =  new DealerVehicleReport();
						dvReport.setSrNo(i);
						dvReport.setVin(rs.getString(1));
						dvReport.setQuarter(rs.getInt(2));
						dvReport.setAccountBalance(rs.getInt(3));
						dvehList.add(dvReport);
						
		
					}
					genreport.put("VINMAP", dvehList);
				}
		}catch(SQLException  e){
			LOGGER.error("DPB.BLOCK.VEHICLE.LIST.DAO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BLOCK.VEHICLE.LIST.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return genreport;
	}
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) {
		final String methodName = "getProcessLogsDeatils";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List <DPBProcessLogBean> procesDetail = null;	
		DPBProcessLogBean processCalDefBean = null;
		try {
			con  = conFactory.getConnection();
			String query =IReportQueryConstants.getPaymentDeatilsQuery;

			if (con != null) {				
				ps = con.prepareStatement(query);
				ps.setInt(1, procId);
				rs = ps.executeQuery();
				procesDetail = new ArrayList<DPBProcessLogBean>();
				int i = 1;
				while (rs.next()) {
					processCalDefBean =  new DPBProcessLogBean();
					processCalDefBean.setDpbProcessId(i);
					processCalDefBean.setProcessMessage(rs.getString(1));
					processCalDefBean.setDpbProcessStatus(rs.getString(2));
					procesDetail.add(processCalDefBean);
					i++;
				}				
			}
		}
		catch(SQLException  e){
			LOGGER.error("DPB.BLOCK.VEHICLE.LIST.DAO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BLOCK.VEHICLE.LIST.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return procesDetail;
	}

	public List <RetailsAndPaymentsReport> generateRtlAndPayReport(List<String> vType,String vehicleId,java.sql.Date fromDate,java.sql.Date toDate,String level)
	{
		final String methodName = "generateRtlAndPayReport";
		List <RetailsAndPaymentsReport> lst =null;
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try {
			con  = conFactory.getConnection();	
			ps = con.prepareStatement("select ID_RPT_QRY,NAM_RPT,ID_CREA_USER,DTS_CREA,ID_LAST_UPDT_USER,DTS_LAST_UPDT from RPT_QRY");
			rs = ps.executeQuery();

			if (con != null) {
				while(rs.next()){

				}
			}
		}
		catch(SQLException  e){
			LOGGER.error("DPB.RPT.PAY.DAO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.RPT.PAY.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return lst;
	}
	/**
	 * @author OS5011279
	 * @throws BusinessException 
	 */
	public List<ReportDefinitionBean> generateReportList() throws BusinessException{
		final String methodName = "generateReportList";
		LOGGER.enter(CLASSNAME, methodName);

		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		List<ReportDefinitionBean> rList = null;
		ReportDefinitionBean reportBean; 
		try {
			con  = conFactory.getConnection();	
			ps = con.prepareStatement(IReportQueryConstants.GEN_RPT_LIST_QRY);
			rs = ps.executeQuery();
			rList = new ArrayList<ReportDefinitionBean>();

			if (con != null) {
				while(rs.next()){
					reportBean = new ReportDefinitionBean();
					reportBean.setReportDefId(rs.getInt("ID_DPB_RPT"));
					String desc=rs.getString("DES_DPB_RPT");
					if(desc !=null && !desc.equals(""))
					{
						reportBean.setReportName(DateCalenderUtil.makeNonNullString(rs.getString("DES_DPB_RPT")).trim());
					}
					else
					{
						reportBean.setReportName(DateCalenderUtil.makeNonNullString(rs.getString("NAM_DPB_RPT")).trim());
					}
                    reportBean.setFlgDailyMonthly(rs.getInt("ID_DPB_RPT")+"_"+rs.getString("CDE_DPB_SCHD"));
					rList.add(reportBean);
				}
				/*if(rList.isEmpty()){
					throw new BusinessException("msg", "No Reports Found");
				}*/
			}
		}
		catch(SQLException  e){
			LOGGER.error("Exception Occured while getting the Report definition from DB", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception Occured while getting the Report definition from DB", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);	
		return rList;
	}
	public List reportsGenerate(int reportId){

		final String methodName = "reportGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		List<ReportQuery> rqList = new ArrayList<ReportQuery>();
		List<ReportContentDefinitionBean> rcList = new ArrayList<ReportContentDefinitionBean>();
		ReportQuery rquery = null ;
		ReportContentDefinitionBean rContent = null ;
		try {
			con  = conFactory.getConnection();	
			ps = con.prepareStatement(IReportQueryConstants.GEN_RPT_QRY);
			ps.setInt(1, reportId);
			rs = ps.executeQuery();
			if (con != null) {
				while(rs.next()){
					rquery = new ReportQuery(); 
					rContent =  new ReportContentDefinitionBean();
					String ctnt = null;
					String qry = null;
					String ftr = null;
					byte[] cblob = rs.getBytes("TXT_DPB_RPT_CTNT");
						if(cblob != null){
							ctnt = new String(cblob);
						}
					rContent.setReportContent(ctnt);
					byte[] qblob = rs.getBytes("TXT_DPB_RPT_QRY");
						if(qblob != null){
							qry = new String(qblob);
						}rquery.setQuery(qry);
					byte[] cfblob = rs.getBytes("FOOTER");
						if(cfblob != null){
							ftr = new String(cfblob);
						}rContent.setReportFooter(ftr);
						rContent.setQryRsltLpp(rs.getInt("CNT_DPB_RPT_QRY_RSLT_LPP"));
					rqList.add(rquery);
					rcList.add(rContent);
				}
				list.add(rcList);
				list.add(rqList);
			}
		}
		catch(SQLException  e){
			LOGGER.error("Exception Occured while getting the Report definition from DB", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception Occured while getting the Report definition from DB", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("SQLException occured",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return list;
	}

	public List<List<String>> getQueryData(String qry){
		final String methodName = "getQueryData";
		LOGGER.enter(CLASSNAME, methodName);
			
		List<String> colNames = new ArrayList<String>();
		List<String> colValues ;
		List<List<String>> colValList = new ArrayList<List<String>>();
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		String colVal;
		char ch = ' ';
		try {
			con  = conFactory.getConnection();	
			//System.out.println("Query:"+qry);
			ps = con.prepareStatement(qry);
			rsmd = ps.getMetaData();
			int noOfColumn = rsmd.getColumnCount();
			
			for(int i=0; i<noOfColumn; i++){
				colNames.add(rsmd.getColumnName(i+1));
			}
			
			rs = ps.executeQuery();
			while(rs.next()){
				colValues = new ArrayList<String>();
				for(String str: colNames){
					colVal = rs.getString(str);
					
					if (colVal != null){
						String temp = colVal;
						float val = 0;
						try {
							val = Float.parseFloat(temp.replaceAll(",", "").trim());
							
							if (val < 0) {
								colVal = colVal.replaceAll("-", " ");
								colVal = colVal.concat("-");
							} else {
								colVal = colVal.concat(" ");
							}
						} catch(NumberFormatException e) {
							if (temp.contains("$-"))
								colVal = temp.replaceAll("$-", "-$");
						}
					}
					colValues.add(colVal);
					
				}
				colValList.add(colValues);
				
			}
		}
		catch(SQLException  e){
			LOGGER.error("Exception Occured while getting the Data from DB", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception Occured while getting the Data from DB", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("SQLException occured",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		
		return colValList;
	}
	@Override
	public ReportDefinitionBean retrieveReportId(int processId) {
		final String methodName = "retrieveReportId";
		LOGGER.enter(CLASSNAME, methodName);
		int reportId = 0;
		ReportDefinitionBean reportDefBean = null;
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try {
			con  = conFactory.getConnection();	
			ps = con.prepareStatement(IReportQueryConstants.FETCH_RPT_DATA_BY_PROC_ID);
			ps.setInt(1, processId);
			rs = ps.executeQuery();
			reportDefBean = new ReportDefinitionBean();
			while(rs.next()){
				reportDefBean.setScheduleCode(rs.getString("CDE_DPB_SCHD"));
				reportDefBean.setReportDefId(rs.getInt("ID_DPB_RPT"));
				reportDefBean.setReportName(rs.getString("NAM_DPB_RPT"));
			}
		}
		catch(SQLException  e){
			LOGGER.error("GET.RPT.ID.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("GET.RPT.ID.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("GET.RPT.ID.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);	
		return reportDefBean;
	}
	public List<VistaFileProcessing> getDlrReserveExceptionList(List<String>  vehicleType, String month ,String year, String dealerIds){
		final String methodName = "getVehicleExceptionList";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean isAloneDate = false;
		boolean isVehSelected = false;
		boolean isAlonedId =false;
		boolean isAlonevId =false;
		List <VistaFileProcessing> eList = null;
		VistaFileProcessing vistaRtl = null; 
		try {
			con  = conFactory.getConnection();		
			StringBuffer DLR_RESERVE_EXCEPTION_REPORTS_QUERY = new StringBuffer(IReportQueryConstants.DLR_RESERVE_EXCEPTION_RPT_QRY);
			
			boolean flag = false;
			String [] arrVeh = new String[vehicleType.size()];
			arrVeh = vehicleType.toArray(arrVeh);
			int lastIndex = 0;
			if(!vehicleType.isEmpty())
			{	
					DLR_RESERVE_EXCEPTION_REPORTS_QUERY.append(" and  CDE_VEH_TYP  in (?) ");
					for (int j = 1; j< arrVeh.length ;j++)
					{	
						lastIndex = DLR_RESERVE_EXCEPTION_REPORTS_QUERY.lastIndexOf("?");
						DLR_RESERVE_EXCEPTION_REPORTS_QUERY.insert(lastIndex+1, ",? ");
					}
					isVehSelected = true;
			}
			String[] arr = null;
			int commas = 0;
			if(dealerIds!= null && !IConstants.EMPTY_STR.equals(dealerIds)  
					&& dealerIds.indexOf(",")!=-1){
				for(int i = 0; i < dealerIds.length(); i++) {
				    if(dealerIds.charAt(i) == ',') 
				    	arr = dealerIds.split(",");
				    commas++;
				}
			}
			// user entered only dealer ids.
			if(dealerIds!= null && !IConstants.EMPTY_STR.equals(dealerIds)){
				DLR_RESERVE_EXCEPTION_REPORTS_QUERY.append(" and ID_DLR in (?)"); 
				if(commas != 0)
				{	
					for (int j=1; j< arr.length ;j++)
					{
						lastIndex = DLR_RESERVE_EXCEPTION_REPORTS_QUERY.lastIndexOf("?");
						DLR_RESERVE_EXCEPTION_REPORTS_QUERY.insert(lastIndex+1, ",?");
					}
				}
				isAlonedId=true;	
				isAlonevId=true;
			}
			if((month != null && !IConstants.EMPTY_STR.equals(month) 
							&& year != null && !IConstants.EMPTY_STR.equals(year))){					
					DLR_RESERVE_EXCEPTION_REPORTS_QUERY.append(" AND MONTH(DTE_RTL) = ?  AND  YEAR(DTE_RTL) = ? "); 
					isAloneDate=true;
			}
		int count = 0 ;
		ps = con.prepareStatement(DLR_RESERVE_EXCEPTION_REPORTS_QUERY.toString());
		if(isVehSelected)
		{
			for (int j = 0; j < arrVeh.length ;j++)
			{	
				ps.setString(++count,arrVeh[j]);
			}
			
		}
		if(dealerIds!= null && dealerIds.trim().length() > 0 && arr == null ){
			ps.setString(++count,dealerIds.trim());
		}
		
		if((month != null && !IConstants.EMPTY_STR.equals(month) 
				&& year != null && !IConstants.EMPTY_STR.equals(year))){					
			ps.setString(++count,month);
			ps.setString(++count,year);
		}		
			
		rs = ps.executeQuery();	
		eList = new ArrayList<VistaFileProcessing>();

		while(rs.next()){
			vistaRtl =  new VistaFileProcessing();
			vistaRtl.setPoNo(rs.getString("NUM_PO"));
			vistaRtl.setVinNum(rs.getString("NUM_VIN"));
			vistaRtl.setRetailDate(rs.getDate("DTE_RTL"));
			vistaRtl.setDealerId(rs.getString("ID_DLR"));
			vistaRtl.setModelYearDate(rs.getString("DTE_MDL_YR"));
			vistaRtl.setModelText(rs.getString("TXT_MODL"));				
			vistaRtl.setRegionCode(rs.getString("CDE_RGN"));
			//vistaRtl.setVehTypeCode(rs.getString("CDE_VEH_TYP"));
			vistaRtl.setVehicleCount(rs.getString("VEH_CNT"));
			vistaRtl.setReason(rs.getString("REASON"));
			eList.add(vistaRtl);
		}
		}catch(SQLException  e){
			LOGGER.error("DPB.VEHICLE.EXCEPTION.LIST.DAO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.VEHICLE.EXCEPTION.LIST.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.VEHICLE.EXCEPTION.LIST.DAO.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return eList;
	}
	@Override
	public List<DleRsrvSumRptBean> getDlrRsrvSumReport(List<String> vehicles,int year,int month){
		final String methodName = "getDlrRsrvSumReport";
		LOGGER.enter(CLASSNAME, methodName);
		List<DleRsrvSumRptBean> drsrList = new ArrayList<DleRsrvSumRptBean>();;
		DleRsrvSumRptBean drsrBean = null;
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try {
			con  = conFactory.getConnection();
			String query = IReportQueryConstants.DLR_RESERVE_SUM_RPT_QRY;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			drsrBean = new DleRsrvSumRptBean();
			while(rs.next()){
				drsrBean = new DleRsrvSumRptBean();
				drsrBean.setPrgType(rs.getString(1));
				drsrBean.setYear(rs.getInt(2));
				drsrBean.setPrgCount(rs.getInt(3));
				drsrBean.setSumAmount(rs.getDouble(4));
				drsrList.add(drsrBean);
			}
		}
		catch(SQLException  e){
			LOGGER.error("DLR.RSRV.RPT.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DLR.RSRV.RPT.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DLR.RSRV.RPT.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return drsrList;
		
	}
	@Override
	public String fetchQuery(String queryName) {
		final String methodName = "fetchQuery";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			con  = conFactory.getConnection();
			ps = con.prepareStatement(IReportQueryConstants.FETCH_QUERY_BY_NAME);
			ps.setString(1, queryName);
			rs = ps.executeQuery();
			while(rs.next()) {
				byte[] cblob = rs.getBytes("TXT_DPB_RPT_QRY");
				if(cblob != null){
					query = new String(cblob);
				}
			}
		}
		catch(SQLException  e){
			LOGGER.error("Exception occured while fetching the report query", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception occured while fetching the report query", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Exception occured while fetching the report query", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return query;
	}
	/**
	 * Assume we have only one entry for the one NAM_DPB_RPT_QRY (queryName)
	 */
	@Override
	public Map<String,String> fetchQuery(List<String> queryNames) {
		final String methodName = "fetchQuery";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> queryMap = new HashMap<String,String>();
		try {
		StringBuffer queryBuffer =  new StringBuffer(IReportQueryConstants.FETCH_QUERY_BY_NAME_ALL + "('");
		if(queryNames != null && queryNames.size() > 0) {
			
			for(int i=0;i<queryNames.size();i++) {
				queryBuffer.append(queryNames.get(i)+"'");
				if(i != (queryNames.size() -1)){
					queryBuffer.append(",'");
				} 
			}			
		}
		queryBuffer.append(") with ur");
		
			con  = conFactory.getConnection();
			ps = con.prepareStatement(queryBuffer.toString());			
			rs = ps.executeQuery();
			int i = 0;
			while(rs.next()) {
				String queryName = rs.getString("NAM_DPB_RPT_QRY");
				queryName = queryName != null ? queryName.trim() : queryName;
				byte[] cblob = rs.getBytes("TXT_DPB_RPT_QRY");
				if(cblob != null){
					queryMap.put(queryName,new String(cblob));
					i++;
				}
			}
		}
		catch(SQLException  e){
			LOGGER.error("Exception occured while fetching the report query", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception occured while fetching the report query", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Exception occured while fetching the report query", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return queryMap;
	}
	
	
	@Override
	public int getReportLpp(int rptId) {
		final String methodName = "getReportLpp";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		int lpp = 0;
		String schedule = "";
		try {
			con  = conFactory.getConnection();
			ps = con.prepareStatement(IReportQueryConstants.FETCH_REPORT_LPP);
			ps.setInt(1, rptId);
			rs = ps.executeQuery();
			while(rs.next()) {
				lpp = rs.getInt("QTY_DPB_RPT_LPP");
				schedule = rs.getString("CDE_DPB_SCHD");
			}
			if (lpp == 0) {
				if (IConstants.SCHD_DAILY.equalsIgnoreCase(schedule)) {
					lpp = IConstants.DEFAULT_LPP_DAILY;
				} else if (IConstants.SCHD_MONTH.equalsIgnoreCase(schedule)) {
					lpp = IConstants.DEFAULT_LPP_MONTHLY;
				} else if (IConstants.SCHD_YEARLY.equalsIgnoreCase(schedule)) {
					lpp = IConstants.DEFAULT_LPP_YEARLY;
				}
			}
		}
		catch(SQLException  e){
			LOGGER.error("Exception occured while fetching the report LPP", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception occured while fetching the report LPP", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Exception occured while fetching the report query", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return lpp;
	}
	
	/**
	 * This method will return the Retail Begin date and Retail End date for the given Year and Month/Quarter
	 * @param fromYr
	 * @param toYr
	 * @param fromMon
	 * @param toMon
	 * @param fromQtr
	 * @param toQtr
	 * @param isMonth
	 * @return List<RetailDate>
	 */
	public List<RetailDate> getRetailDates(String fromYr, String toYr, String fromMon, String toMon, 
							String fromQtr, String toQtr, boolean isMonth) {
		final String methodName = "getRetailDates";
		LOGGER.enter(CLASSNAME, methodName);
		
		List<RetailDate> rtlDteList = new ArrayList<RetailDate>();
		RetailDate rtlDte = null;
		
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		
		try {
			con  = conFactory.getConnection();
			if (isMonth) {
				query = IReportQueryConstants.GET_RTL_DTS_FOR_YR_MNTH;
			} else { // criteria for quarter input with Year
				query = IReportQueryConstants.GET_RTL_DTS_FOR_YR_QTR;
			}
			
			ps = con.prepareStatement(query);
			ps.setString(1, fromYr);
			ps.setString(3, toYr);
			
			if (isMonth) {
				ps.setString(2, fromMon);
				ps.setString(4, toMon);
			} else {
				ps.setString(2, fromQtr);
				ps.setString(4, toQtr);
			}
			
			rs = ps.executeQuery();
			while(rs.next()) {
				rtlDte = new RetailDate();
				
				rtlDte.setCurrentYear(rs.getString("NUM_RETL_YR"));
				if (isMonth) {
					rtlDte.setRtlStartDate(rs.getString("DTE_RETL_MTH_BEG"));
					rtlDte.setRtlEndDate(rs.getString("DTE_RETL_MTH_END"));
					rtlDte.setCurrentMonth(rs.getString("NUM_RETL_MTH"));
				} else {
					rtlDte.setRtlStartDate(rs.getString("DTE_RETL_QTR_BEG"));
					rtlDte.setRtlEndDate(rs.getString("DTE_RETL_QTR_END"));
					rtlDte.setCurrentQuarter(rs.getString("NUM_RETL_QTR"));
				}
				rtlDteList.add(rtlDte);
			}
			
		}
		catch(SQLException  e){
			LOGGER.error("Exception occured while fetching the Retail date data", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception occured while fetching the Retail date data", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Exception occured while fetching the report query", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		
		
		return rtlDteList;
	}
	/*DCQPR Start  - Performance Improvement 10/06/2016*/
	public List<String> getDealerData(String vehicleType) {
		final String methodName = "getDealerData";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> dealerlst=new ArrayList<String>();
				try {
			con  = conFactory.getConnection();
			ps = con.prepareStatement(IReportQueryConstants.DCQPR_DEALER_UNBLK_CARS);
			ps.setString(1, vehicleType);
			rs = ps.executeQuery();
			while(rs.next()){
				String  s = new String();
				s=rs.getString("DEALER");
				//System.out.println("list " +rs.getString("dealer"));
				dealerlst.add(s);
				
			}System.out.println("get dealerData Result set: "+dealerlst.size());
		}
				
		catch(SQLException  e){
			LOGGER.error("Exception occured while fetching the dealer data", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("Exception occured while fetching the dealer data", ne);
			DPBExceptionHandler.getInstance().handleException(ne);			
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Exception occured while fetching the report query", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return dealerlst;
	}
	
	/*DCQPR END  - Performance Improvement 10/06/2016*/
	
	
}
