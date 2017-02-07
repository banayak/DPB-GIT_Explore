package com.mbusa.dpb.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mbusa.dpb.common.constant.IConstants;

public class MyLibrary {
	  public String padCharacter(){
	    int number = 1500;	
	    // String format below will add leading zeros (the %0 syntax) 
	    // to the number above. 
	    // The length of the formatted string will be 7 characters.	
	    String formatted = String.format("%07d", number);	
	    System.out.println("Number with leading zeros: " + formatted);	    
	    System.out.println(padCharacter("*",8,"hello").length());
	    return "";
	 }
	  
	  public static void main(String arg[]) throws Exception{
		  
		  System.out.println(System.currentTimeMillis()+200000);
		  //padCharAtRight(" ",10,"chetan");
		 // subStringLastGivenChar("4JGAB54EX2A360760",8);
		 // subStringLastGivenChar("JGAB54EX2A360760",8);
		  /*padCharacter("#",1,"1");
		  padCharacter("#",1,"");
		  padCharacter("#",0,"");
		  padCharacter("#",10,"200.52");
		  */
		  /*Calendar c1 = Calendar.getInstance();   
			Calendar c2 = Calendar.getInstance();  	
			c1.add(Calendar.DATE,-6);
			System.out.println("c1:"+c1.getTimeInMillis());
			c1.add(Calendar.DATE,-6);
			System.out.println("c1:"+c1.getTimeInMillis());
			c1.add(Calendar.DATE,-6);*/
		   //new MyLibrary().date();
		   //createPaymentFile(null,"C:\\DPBFileProcess\\In\\SPUFI.OUTPUT.txt","C:\\DPBFileProcess\\In\\Vista");
		  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:SS");

			//date =  formatter.parse(strDate);
			java.sql.Date sqlDate = new java.sql.Date(Long.parseLong("1418497200160"));
			System.out.println("sqlDate:"+sqlDate);
	  }
		  
		  /*String []filename={"C:/input/VISTA.20140502.txt","C:/input/VISTA.20140503.txt"};
		    File file=new File("C:/output/new.txt");
		    //FileWriter output=new FileWriter(file);
		    try
		    {   
		    	FileWriter output=new FileWriter(file);
		      for(int i=0;i<filename.length;i++)
		      {
		        BufferedReader objBufferedReader = new BufferedReader(new FileReader(getDictionaryFilePath(filename[i])));

		        String line;
		        while ((line = objBufferedReader.readLine())!=null )
		        {
		        	output.write(line);
		        	if(line.length() == 250)
                    {
		        		 output.write(System.getProperty( "line.separator" ));               
                    }
		        }
		        objBufferedReader.close();
		      }
		      output.close();
		    }
		    catch (Exception e) 
		    {
		      throw new Exception (e);
		    }
		  }
*/
		  
			
	  

public static String getDictionaryFilePath(String filename) throws Exception
{
  String dictionaryFolderPath = null;
  File configFolder = new File(filename);
  try 
  {
    dictionaryFolderPath = configFolder.getAbsolutePath();
  } 
  catch (Exception e) 
  {
    throw new Exception (e);
  }
  return dictionaryFolderPath;
}
		
	    public static String padCharacter(String c, int num, String str){
	        System.out.println("replace:" + c+":num:"+num+":Actual str:"+str);
			 str =  str != null && str.length() > 0 ? str.trim():"";
			 int length = num - str.length()-1;
			 for(int i=0;i <= length;i++){
	       	 	str = c + str;
			 }
			 System.out.println(":" + str+":flength:"+str.length());  
			 return str;
	    }
	    public void date()
	    {
	    	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:SS");

				//date =  formatter.parse(strDate);
				java.sql.Date sqlDate = new java.sql.Date(Long.parseLong("1379403600547"));
				System.out.println("sqlDate:"+sqlDate);
	    }
	   
    public static String padCharAtRight(String charToPad, int totalChar, String str) {
          str = str != null && str.length() > 0 ? str.trim() : IConstants.EMPTY_STR;
          int length = totalChar - str.length() - 1;
          for (int i = 0; i <= length; i++) {
        	str =  str +charToPad;
          }
          System.out.println("str:" +str+":length:"+str.length());
          return str;
      }
    public static String subStringLastGivenChar(String toSubStr,int lastCount){
  	  String subStr = "";
  	 System.out.println("toSubStr:" +subStr+":toSubStr:"+toSubStr.length());
  	  if(toSubStr != null && toSubStr.trim().length() > 0){	    	  
	    	  if(toSubStr.length() == 16){
	    		  subStr = toSubStr.substring(lastCount);	    		  
	    	  }else{
	    		  subStr = toSubStr.substring(lastCount+1);
	    	  }
  	  }
  	 System.out.println("subStr:" +subStr+":length:"+subStr.length());
  	  return subStr;
    }
  
    /*public  static void createPaymentFile(List<String> fLines,String oldfilePath, String NewfilePath) {
          PrintWriter pw = null;
        try {
                BufferedReader reader = null;
                String line = null;
              File fileInput = new File(oldfilePath);
              File file = new File(NewfilePath);
                if (!file.exists()) {
                  file.createNewFile();
                }
                pw = new PrintWriter(new FileWriter(file+".txt"));
                String lineSep = System.getProperty("line.separator");
                String olddate = "";
                String currentdate = "";
              
                      if (fileInput.exists() && fileInput.canRead()) {
                            reader = new BufferedReader(new FileReader(fileInput));                            
                            for (int count = 1; (line = reader.readLine()) != null; count++) {
                            	
                              if(line.length() < 215)
                              {
                            	 
                            	 line= line+"                                     ";
                              }else if(line.length()== 215){
                            	  	line= line+"                                   ";    
                              }
		                              
	                        currentdate =  line.substring(0, 8);
	                        System.out.println("New:"+NewfilePath+"."+currentdate+".txt");
                        	if(!olddate.equalsIgnoreCase(currentdate)){
                        		pw.close();
                        		file = new File(NewfilePath+"."+currentdate+".txt");
                        		pw = new PrintWriter(new FileWriter(file));
                        	}
                        	olddate =  currentdate;
                            pw.write(line);
                            pw.write(lineSep);
                            }
                          }
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              pw.close();
            }
            
        }*/
    public  static void createPaymentFile(List<String> fLines,String oldfilePath, String NewfilePath) {
        PrintWriter pw = null;
      try {
              BufferedReader reader = null;
              String line = null;
            File fileInput = new File(oldfilePath);
            File file = new File(NewfilePath);
              if (!file.exists()) {
                file.createNewFile();
              }
              pw = new PrintWriter(new FileWriter(file+".txt"));
              String lineSep = System.getProperty("line.separator");
              String olddate = "";
              String currentdate = "";
            
            
                    if (fileInput.exists() && fileInput.canRead()) {
                          reader = new BufferedReader(new FileReader(fileInput));
                          RandomAccessFile raf = new RandomAccessFile(fileInput, "rw");

                          Map<Integer,PrintWriter> hMap = new HashMap<Integer,PrintWriter>();
                          for ( int count = 1 ; (line = raf.readLine()) != null; count++) {
                          	
                            if(line.length() < 215)
                            {
                          	 
                          	 line= line+"                                     ";
                            }else if(line.length()== 215){
                          	  	line= line+"                                   ";    
                            }
		                              
	                        currentdate =  line.substring(0, 8);
	                        int tempname = Integer.parseInt(currentdate)+1;
	                        
	                        if(count == 1) {
	                        	hMap.put(tempname, new PrintWriter(new File(NewfilePath+"."+tempname+".txt")));
	                        	hMap.get(tempname).println(line);
	                        } else {
	                        	Set<Integer> keySet = hMap.keySet();
	                        	boolean flag = false;
	                        	for (Integer date: keySet) {
	                        		if (tempname <= date) {
	                        			hMap.get(date).println(line);
	                        			flag = true;
	                        		} else {
	                        			hMap.get(date).close();
	                        			hMap.remove(date);
	                        			raf.seek(0);
	                        		}
	                        	}
	                        	if (!flag) {
	                        		hMap.put(tempname, new PrintWriter(new File(NewfilePath+"."+tempname+".txt")));
		                        	//hMap.get(tempname).write(line);
	                        	}
	                        }
	                        /*currentdate = String.valueOf(tempname);
	                        //HashMap hMap=new HashMap();
	                       // h
	                        System.out.println("New:"+NewfilePath+"."+currentdate+".txt");
	                        boolean isNewLine =  true;
                      	if(!olddate.equalsIgnoreCase(currentdate)){
                      		pw.close();
                      		 System.out.println("Hey ..." + raf.getFilePointer());
                      		 raf.seek(0);
                      		  System.out.println("Hey ..." + raf.getFilePointer());

                      		file = new File(NewfilePath+"."+currentdate+".txt");
                      		pw = new PrintWriter( new FileWriter(file));
                      		isNewLine = false;
                      	}
                      	olddate =  currentdate;
                      	if(isNewLine)
                      	{
                      		pw.write(lineSep);	
                      	}
                          pw.write(line);
                          raf.close();
                          }*/
                          

                        }
                          Set<Integer> keySet = hMap.keySet();
                      	for (Integer date: keySet) {
                      		hMap.get(date).close();
                      	}
                    }
                          
                          
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            pw.close();
          }
          
      }
  }