package com.mbusa.dpb.common.domain;

public class QCRelationBean {
private int qcrId;
private int contId;
private String nameCtnt;
private int queryId;
private String nameQry;
private int seqNum;
private int footerId;
private String nameFooter;
private String createdUserId;
private String lastUpdUserId;


public String getNameCtnt() {
	return nameCtnt;
}
public void setNameCtnt(String nameCtnt) {
	this.nameCtnt = nameCtnt;
}
public String getNameQry() {
	return nameQry;
}
public void setNameQry(String nameQry) {
	this.nameQry = nameQry;
}
public String getNameFooter() {
	return nameFooter;
}
public void setNameFooter(String nameFooter) {
	this.nameFooter = nameFooter;
}
public int getQcrId() {
	return qcrId;
}
public void setQcrId(int qcrId) {
	this.qcrId = qcrId;
}
public int getContId() {
	return contId;
}
public void setContId(int contId) {
	this.contId = contId;
}
public int getQueryId() {
	return queryId;
}
public void setQueryId(int queryId) {
	this.queryId = queryId;
}
public int getSeqNum() {
	return seqNum;
}
public void setSeqNum(int seqNum) {
	this.seqNum = seqNum;
}
public int getFooterId() {
	return footerId;
}
public void setFooterId(int footerId) {
	this.footerId = footerId;
}
public String getCreatedUserId() {
	return createdUserId;
}
public void setCreatedUserId(String createdUserId) {
	this.createdUserId = createdUserId;
}
public String getLastUpdUserId() {
	return lastUpdUserId;
}
public void setLastUpdUserId(String lastUpdUserId) {
	this.lastUpdUserId = lastUpdUserId;
}


}
