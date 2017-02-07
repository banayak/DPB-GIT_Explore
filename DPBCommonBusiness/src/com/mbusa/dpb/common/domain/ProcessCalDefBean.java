package com.mbusa.dpb.common.domain;


/**
 * @author AP5004613
 * */

import java.util.Calendar;

public class ProcessCalDefBean 

{

private String processType;
private String processDefinitionID;
private Calendar retaildate; // changes by Nikhil P

/**
 * @return the Process Type
 */

public String getProcessType() {
	return processType;
}

/**
 * @return the Process ID
 */

public String getProcessDefinitionID() {
	return processDefinitionID;
}

/**
 * @return the Retail Date
 */

public Calendar getRetaildate() {
	return retaildate;
}

public void setProcessType(String processType) {
	this.processType = processType;
}

public void setProcessDefinitionID(String processDefinitionID) {
	this.processDefinitionID = processDefinitionID;
}

public void setRetaildate(Calendar retaildate) {
	this.retaildate = retaildate;
}

@Override
public boolean equals(Object o) {
	boolean isEqual = false;
	if(o instanceof String){
		String day = (String) o;
		if(retaildate.get(Calendar.DAY_OF_MONTH) != 0 && String.valueOf(retaildate.get(Calendar.DAY_OF_MONTH)).equalsIgnoreCase(day)){
			isEqual = true;
		}
	}
	return isEqual;
}

}
