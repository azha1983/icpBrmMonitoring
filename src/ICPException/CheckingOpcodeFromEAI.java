package ICPException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.Vector;

public class CheckingOpcodeFromEAI {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
 	private String dbasePass = "EAI_READ";
    private String dbaseUser = "EAI_READ";
    private String dbaseIP = "10.41.33.110";
    
    
    public static void main( String[] args ) throws Exception 
   {
    	
    	CheckingOpcodeFromEAI c = new CheckingOpcodeFromEAI();
    	
    	
		PrintStream p1; 
		
		
		FileInputStream fstream = new FileInputStream(res.getString("file13.orderIDEAI"));		
		FileOutputStream out1 = new FileOutputStream(res.getString("file2.exceptionOut"));
		
		
		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		p1 = new PrintStream(out1);
		
		
		
		int x = 1;
		
		String strLine = "Null";
		
		
		String eaiID = "Null";
		String auditDate = "Null";
		String opcode = "Null";
		String payload = "Null";
		String fileURL = "Null";
		String outputReturn = "Null";
		
		PrintStream p2;
		
		//c.checkEAITableTEST("1-4NXDEOX");
		
		while ((strLine = br.readLine()) != null)  
		{
			
			System.out.println(x + ") " + strLine);
			
		if(!strLine.equalsIgnoreCase("Null"))
			{
				
				Vector output1 = new Vector();
				
				output1 = c.checkEAITable(strLine);
				
				if(output1.size()>0)
				{
		    		System.out.println("Vector 1 not null...proceed");
		    		
			    	for(int y=0;y<output1.size();y++)
			    	{
			    		System.out.println(y+1 + ":: " + y);
			    		Vector info = (Vector)output1.elementAt(y);
			    		eaiID = (String)info.elementAt(0);
			    		auditDate = (String)info.elementAt(1);	
			    		opcode = (String)info.elementAt(2);	
			    		payload = (String)info.elementAt(3);	
						
			    		System.out.println(eaiID + "|" + auditDate + "|" + opcode);
			    		break;
			    		
			    	}
			    	
			    	
			    	
					fileURL = "C:/WorkSpace/FileInput/ExceptionICP/CheckOpcodeFromEAI/ListEAI_ID_Payload.txt";
					
					FileOutputStream out2 = new FileOutputStream(fileURL);
					p2 = new PrintStream(out2);
					
					
					p2.println(payload);
					outputReturn = "Null";
					
					//boolean checkBlankValue = false;
					
					

					//checkBlankValue = true;
					
						
					
					//if(checkBlankValue == false)
					//{
						
					System.out.println("---> " + outputReturn);
											
					if(opcode.equalsIgnoreCase("PCM_OP_CUST_MODIFY_CUSTOMER"))
					{    				
					
					System.out.println("Enter ---> PCM_OP_CUST_MODIFY_CUSTOMER");
					//-----------check error opcode modify customer
					CheckLoginFromEAI checkErrModifyCustomer = new CheckLoginFromEAI();			
					checkErrModifyCustomer.err_Bad_value(fileURL);	
					
					
					
					String result = checkErrModifyCustomer.getServiceDetailFromTable(checkErrModifyCustomer.getLoginX(), checkErrModifyCustomer.getAccObjX(), checkErrModifyCustomer.getServiceTypeX());			
					
					if(result.equalsIgnoreCase("No login created in Service T"))
					{
						//double check cost center
						CheckCostCenterMapping checkCost = new CheckCostCenterMapping();
						
						checkCost.err_Blank_Value(fileURL);
				    	
				    	
				    	String outputCostCenter = checkCost.checkMappingCostCenter(checkCost.getCostCenterVal(), checkCost.getIndustrialCod(), checkCost.getSegmentCod());
				    	
				    	System.out.println("Result is " + result);
				    	
				    	if(outputCostCenter.equalsIgnoreCase("Null"))
				    	{
				    		outputReturn = "Cost Center mapping not found in BRM for -> Cost Center = " + checkCost.getCostCenterVal() + ", Industrial Code = " + checkCost.getIndustrialCod() + " Segment Code = " + checkCost.getSegmentCod();
				    	}
				    	else
				    	{
				    		outputReturn = "Service not yet created,Cost center mapping found. Please re-trigger";  		
				    	}
						
					}
					else
					{
						outputReturn = result;
					}
					
					
					}
					else if(opcode.equalsIgnoreCase("30001") || opcode.equalsIgnoreCase("30002"))
					{
						System.out.println("Enter ---> 30001 n 30002");
						String statusGet = "";
						
						Opcode30001n30002 o = new Opcode30001n30002();
						
						o.err_30001_30002(fileURL);
												
						if(!o.wrongOpcode)
						{
							statusGet = o.checkProdDiscPuchaseStatus(o.purchasePoid,o.getOpcodeType());
							
							if(statusGet.equalsIgnoreCase("3"))
							outputReturn = "[EAI] Discount/Product already cancelled in BRM";
							else if(statusGet.equalsIgnoreCase("2"))
							outputReturn = "[EAI] Discount/Product status is inActive in BRM";
							else
							outputReturn = "[OSM] Discount/Product still active, please re-trigger";
						}
						else
						{
							outputReturn = "[EAI] Wrong opcode sent";
						}
					}
				
					else if(opcode.equalsIgnoreCase("PCM_OP_SUBSCRIPTION_PURCHASE_DEAL"))
					{
						System.out.println("Enter ---> PCM_OP_SUBSCRIPTION_PURCHASE_DEAL");
						OpcodePurchase o = new OpcodePurchase();
						
						o.err_Purchase(fileURL);
						
						if(o.getServicePoid().equalsIgnoreCase("Null"))
						{
							outputReturn = "No service poid found. This is account level product";
						}
						else
						{
							String result = o.checkServiceStatus(o.servicePoid);
							
							if(result.equalsIgnoreCase("10100"))
							{
								//outputReturn = "[EAI] Service is Active in BRM";
								Vector checkPurchase = new Vector();
								
								checkPurchase = o.checkProdDiscPuchaseStatus(o.getAccountPoid(), o.getServicePoid(),o.getPoidObj(), o.getPurchaseType());
								
								String purchasePoid = "";
								String created = "";
								String status = "";
								String finalStatus = "";
								
								if(checkPurchase.size()>0)
								{
							    	for(int y=0;y<checkPurchase.size();y++)
							    	{
							    		//System.out.println(y+1 + ":: " + y);
							    		Vector info = (Vector)checkPurchase.elementAt(y);
							    		purchasePoid = (String)info.elementAt(0);
							    		created = (String)info.elementAt(1);	
							    		status = (String)info.elementAt(2);							
							    		
							    	}
							    	
							    	if(status.equalsIgnoreCase("1"))
							    	{
							    		finalStatus = "Active";
							    	}
							    	else if(status.equalsIgnoreCase("2"))
							    	{
							    		finalStatus = "Inactive";
							    	}
							    	else if(status.equalsIgnoreCase("3"))
							    	{
							    		finalStatus = "Closed";
							    	}
							    	else
							    	{
							    		finalStatus = "Unknown";
							    	}
							    	
							    	outputReturn = "[EAI] " + o.getPurchaseType() + " already purchased on " + created + " with purchase poid " + purchasePoid + " and status is " + finalStatus;
								}
								else
								{
									outputReturn = "[EAI] Service is Active and no purchase happen";
								}
							}
							else if(result.equalsIgnoreCase("10102"))
							{
								outputReturn = "[EAI] Service is InActive in BRM";
							}
							else if(result.equalsIgnoreCase("10103"))
							{
								outputReturn = "[EAI] Service poid sent is already Closed in BRM";
							}
							else
							{
								outputReturn = "[EAI] Service is 0.0.0.1 /service/telephony " + o.servicePoid + " 0 not found in BRM. Please re-create service then re-trigger";
							}
						}
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_CUST_MODIFY_PROFILE"))
					{
						//double check cost center
						CheckCostCenterMapping checkCost = new CheckCostCenterMapping();
						
						checkCost.err_Blank_Value(fileURL);
				    	
				    	
				    	String outputCostCenter = checkCost.checkMappingCostCenter(checkCost.getCostCenterVal(), checkCost.getIndustrialCod(), checkCost.getSegmentCod());
				    	
				    	
				    	
				    	if(outputCostCenter.equalsIgnoreCase("Null"))
				    	{
				    		outputReturn = "Cost Center mapping not found in BRM for -> Cost Center = " + checkCost.getCostCenterVal() + ", Industrial Code = " + checkCost.getIndustrialCod() + " Segment Code = " + checkCost.getSegmentCod();
				    	}
				    	else
				    	{
				    		outputReturn = "Cost center mapping found. Please re-trigger";  		
				    	}
						
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_READ_OBJ"))
					{

				    	OpcodeReadObj opc = new OpcodeReadObj();
				    	
				    	opc.missingPoidInPurchasedTable(fileURL);
				    					    	
				    	if(!opc.getPurchasedPoidString().equalsIgnoreCase(""))
				    	{
				    					    		
				    		String purchaseType = "Null";
				    		String result = "Null";
				    		
				    		if(opc.getPurchasedPoidString().indexOf("product") > -1)
				    		{
				    			purchaseType = "Product";
				    			
				    			result = opc.checkPurchaseTable(opc.getPurchasedPoid(), purchaseType);
				    			
				    			if(result.equalsIgnoreCase("Null"))
				    			{
				    				outputReturn = "[EAI] " + opc.getPurchasedPoidString() + " not found in BRM.";
				    			}
				    			else
				    			{
				    				outputReturn = "[OSM] Please re-trigger";
				    			}
				    			
				    			
				    		}
				    		else if(opc.getPurchasedPoidString().indexOf("discount") > -1)
				    		{
				    			purchaseType = "Discount";
				    			
				   			result = opc.checkPurchaseTable(opc.getPurchasedPoid(), purchaseType);
				    			
				    			if(result.equalsIgnoreCase("Null"))
				    			{
				    				outputReturn = "[EAI] " + opc.getPurchasedPoidString() + " not found in BRM.";
				    			}
				    			else
				    			{
				    				outputReturn = "[OSM] Please re-trigger";
				    			}
				    		}
				    		else
				    		{
				    			outputReturn = "No reading purchased table";
				    		}
				    	}
				    	else
				    	{
				    		outputReturn = "No reading purchased table";
				    	}
				    	
				    	
				    
					}
					
					
					//check blank poid first
					CheckBlankValue checkBlank = new CheckBlankValue();
					checkBlank.err_Blank_Value(fileURL);
						
					if(checkBlank.getOpcodeLineReturn().indexOf("BLANK")> -1 || checkBlank.getOpcodeLineReturn().indexOf("<ACCOUNT_OBJ/>")> -1)					
					outputReturn = "Field " + checkBlank.getOpcodeLineReturn() + " Null. Please fill in then re-trigger.";
					
					
					
					p2.close();
			    	
	
			    	
			    	
				}
				else
				{
					System.out.println("Vector is null");
					opcode = "Cannot find BRM opcode";
					
				}
			}
			else
			{
				System.out.println("No EAI ID");
				opcode = "Cannot find BRM opcode";
			}
			

		
			p1.println(strLine + "|" + opcode + "|" + outputReturn);
			
			
			
			strLine = "Null";						
			eaiID = "Null";
			auditDate = "Null";
			opcode = "Null";
			payload = "Null";
			outputReturn = "Null";
			
			x++;
		}
    	
   }
    
    public void checkingFlistByOpcode() throws IOException, Exception
    {
    	
    	
		PrintStream p1; 
		
		
		FileInputStream fstream = new FileInputStream(res.getString("file13.orderIDEAI"));		
		FileOutputStream out1 = new FileOutputStream(res.getString("file2.exceptionOut"));
		
		
		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		p1 = new PrintStream(out1);
		
		
		
		int x = 1;
		
		String strLine = "Null";
		
		
		String eaiID = "Null";
		String auditDate = "Null";
		String opcode = "Null";
		String payload = "Null";
		String fileURL = "Null";
		String outputReturn = "Null";
		String agingCount = "Null";
		
		PrintStream p2;
		
		//c.checkEAITableTEST("1-4NXDEOX");
		
		while ((strLine = br.readLine()) != null)  
		{
			
			System.out.println(x + ") " + strLine);
			
		if(!strLine.equalsIgnoreCase("Null"))
			{
				
				Vector output1 = new Vector();
				
				output1 = this.checkEAITable(strLine);
				
				if(output1.size()>0)
				{
		    		System.out.println("Vector 1 not null...proceed");
		    		
			    	for(int y=0;y<output1.size();y++)
			    	{
			    		System.out.println(y+1 + ":: " + y);
			    		Vector info = (Vector)output1.elementAt(y);
			    		eaiID = (String)info.elementAt(0);
			    		auditDate = (String)info.elementAt(1);	
			    		opcode = (String)info.elementAt(2);	
			    		payload = (String)info.elementAt(3);	
						
			    		System.out.println(eaiID + "|" + auditDate + "|" + opcode);
			    		break;
			    		
			    	}
			    	
			    	//-----------Check date statistic
			    	
			    	//DateStatistic dateS = new DateStatistic();
			    	
			    	//agingCount = dateS.checkDateStats(auditDate);
			    	
			    	
					fileURL = res.getString("file15.payload");
					
					FileOutputStream out2 = new FileOutputStream(fileURL);
					p2 = new PrintStream(out2);
					
					
					p2.println(payload);
					outputReturn = "Null";
					

						
					System.out.println("---> " + outputReturn);
											
					if(opcode.equalsIgnoreCase("PCM_OP_CUST_MODIFY_CUSTOMER"))
					{    				
						System.out.println("Enter ---> PCM_OP_CUST_MODIFY_CUSTOMER");
					//-----------check error opcode modify customer
					CheckLoginFromEAI checkErrModifyCustomer = new CheckLoginFromEAI();			
					checkErrModifyCustomer.err_Bad_value(fileURL);	
					
					outputReturn = checkErrModifyCustomer.getServiceDetailFromTable(checkErrModifyCustomer.getLoginX(), checkErrModifyCustomer.getAccObjX(), checkErrModifyCustomer.getServiceTypeX());			
					
					}
					else if(opcode.equalsIgnoreCase("30001") || opcode.equalsIgnoreCase("30002"))
					{
						System.out.println("Enter ---> 30001 n 30002");
						String statusGet = "";
						
						Opcode30001n30002 opc = new Opcode30001n30002();
						
						opc.err_30001_30002(fileURL);
												
						if(!opc.wrongOpcode)
						{
							statusGet = opc.checkProdDiscPuchaseStatus(opc.purchasePoid,opc.getOpcodeType());
							
							if(statusGet.equalsIgnoreCase("3"))
							{
								outputReturn = "[EAI] '" + opc.getPurchasePoidString() + "' already cancelled in BRM";
							}
							else if(statusGet.equalsIgnoreCase("2"))
							{
								outputReturn = "[EAI] Discount/Product status is inActive in BRM";
							}
							else
							{
								outputReturn = "[OSM] Discount/Product still active, please re-trigger";
							}
						}
						else
						{
							outputReturn = "[EAI] Wrong opcode sent | " + opc.purchasePoid + " " + opc.getOpcodeType();
						}
					}	
					else if(opcode.equalsIgnoreCase("PCM_OP_CUST_UPDATE_SERVICES"))
					{
						System.out.println("Enter ---> PCM_OP_CUST_UPDATE_SERVICES");
						
						CheckUpdateServiceStatus ch = new CheckUpdateServiceStatus();
						
						ch.checkServiceStatusByFList(fileURL);
						
						String accountPoidInBRM = "Null";
						String accountNumberSent = "Null";
						String accountNumberInBRM = "Null";
						
						accountPoidInBRM = ch.serviceTiedToBA(ch.getServicePoid());
						
						
						if(accountPoidInBRM.equalsIgnoreCase("Null"))
						{
							outputReturn = "Service " + ch.getServicePoidString() + " Not Found in BRM";
						}
						else if(accountPoidInBRM.equalsIgnoreCase(ch.getAccountPoid()))
						{
							System.out.println("Account and service sent tied together in BRM");
							String statusReturn = ch.checkService_TStatus(ch.getServicePoid());
							
							if(statusReturn.equalsIgnoreCase("Null"))
							{
								outputReturn = "Service '" + ch.getServicePoidString() + "' Not found in BRM";
							}
							else if(statusReturn.equalsIgnoreCase(ch.getServiceStatus()))
							{
								outputReturn = "Service '" + ch.getServicePoidString() +"' status already UPDATE in BRM.EAI to update mapping, OSM to bypass";
							}
							else
							{
								outputReturn = "Service status in BRM is " + statusReturn + " but try to set status " + ch.getServiceStatus() + ". Please re-trigger";
							}
						}
						else
						{
							System.out.println("Account and service sent NOT tied together in BRM");
							accountNumberSent = ch.checkAccountNumber(ch.getAccountPoid());
							
							accountNumberInBRM = ch.checkAccountNumber(accountPoidInBRM);
							
							Vector checkServiceDetail= new Vector();
							
							String poid_id0 = "Null";
							String poid_Type = "Null";
							String loginGet = "Null";
							
							checkServiceDetail = ch.getServiceDetail(ch.getServicePoid());
							
							if(checkServiceDetail.size() > 0)
							{
						    	for(int y=0;y<checkServiceDetail.size();y++)
						    	{
						    		//System.out.println(y+1 + ":: " + y);
						    		Vector info = (Vector)checkServiceDetail.elementAt(y);
						    		poid_id0 = (String)info.elementAt(0);
						    		poid_Type = (String)info.elementAt(1);	
						    		loginGet = (String)info.elementAt(2);							
						    		
						    	}
						    	
						    	outputReturn = "[Siebel] Try to change status but '" + poid_Type + " " + poid_id0 + "' (Login:" + loginGet + ") sent Tied to other BA in BRM " + accountNumberInBRM + ". Not as send BA " + accountNumberSent;
							}
							else
							{
								outputReturn = "ERROR : Shouldnt be in this";
							}
						
						}
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_SUBSCRIPTION_PURCHASE_DEAL"))
					{
						System.out.println("Enter ---> PCM_OP_SUBSCRIPTION_PURCHASE_DEAL");
						OpcodePurchase o = new OpcodePurchase();
						
						o.err_Purchase(fileURL);
						
						if(o.getServicePoid().equalsIgnoreCase("Null"))
						{
							outputReturn = "No service poid found. This is account level product";
						}
						else
						{
							String result = o.checkServiceStatus(o.servicePoid);
							
							String accountPoidSent = "Null";
							
							if(result.equalsIgnoreCase("10100"))
							{
								
								accountPoidSent = o.serviceTiedToBA(o.getServicePoid());
								
								if(accountPoidSent.equalsIgnoreCase(o.getAccountPoid()))
								{
									Vector checkPurchase = new Vector();
									
									checkPurchase = o.checkProdDiscPuchaseStatus(o.getAccountPoid(), o.getServicePoid(),o.getPoidObj(), o.getPurchaseType());
									
									String purchasePoid = "";
									String created = "";
									String status = "";
									String finalStatus = "";
									
									if(checkPurchase.size()>0)
									{
								    	for(int y=0;y<checkPurchase.size();y++)
								    	{
								    		//System.out.println(y+1 + ":: " + y);
								    		Vector info = (Vector)checkPurchase.elementAt(y);
								    		purchasePoid = (String)info.elementAt(0);
								    		created = (String)info.elementAt(1);	
								    		status = (String)info.elementAt(2);							
								    		
								    	}
								    	
								    	if(status.equalsIgnoreCase("1"))
								    	{
								    		finalStatus = "Active";
								    	}
								    	else if(status.equalsIgnoreCase("2"))
								    	{
								    		finalStatus = "Inactive";
								    	}
								    	else if(status.equalsIgnoreCase("3"))
								    	{
								    		finalStatus = "Closed";
								    	}
								    	else
								    	{
								    		finalStatus = "Unknown";
								    	}
								    	
								    	outputReturn = "[EAI] " + o.getPurchaseType() + " already purchased on " + created + " with purchase poid " + purchasePoid + " and status is " + finalStatus;
									}
									else
									{
										
										String accEffectDate = "Null";
										
										accEffectDate = o.getAccEffectDate(o.getAccountPoid());
										
										int datePurch = Integer.parseInt(o.getDatePurchSent());
										
										if(!accEffectDate.equalsIgnoreCase("Null"))
										{
											
											int dateGet = Integer.parseInt(accEffectDate);
											
											
											
											System.out.println("Purchase date sent is " + o.getDatePurchSent() + " Account Effective date get is " + accEffectDate);
											
											
											if(datePurch < dateGet)
											{
												
												int resultDate = dateGet + 10;
												System.out.println("Purchase date sent is  " + resultDate);
												//System.out.println("Purchase date sent is EARLIER than account effective date. Need to fix\n\n");
												outputReturn = "Please replace field <START_T> and <END_T> with this value " + resultDate + " then re-trigger";
												
											}
											else
											{
												outputReturn = "Date Purchase GREATER then Account effective date. Should be no problem. BRM to check other error";
											}
											
											
										}
										else
										{
											outputReturn = "[EAI] Service is Active and no purchase happen";
										}
									}
								}
								else
								{

									String accountNumberSent = "Null";
									String accountNumberInBRM = "Null";
									
									accountNumberSent = o.checkAccountNumber(accountPoidSent);
									
									accountNumberInBRM = o.checkAccountNumber(o.getAccountPoid());
									
									Vector checkServiceDetail= new Vector();
									
									String poid_id0 = "Null";
									String poid_Type = "Null";
									String loginGet = "Null";
									
									checkServiceDetail = o.getServiceDetail(o.getServicePoid());
									
									if(checkServiceDetail.size() > 0)
									{
								    	for(int y=0;y<checkServiceDetail.size();y++)
								    	{
								    		//System.out.println(y+1 + ":: " + y);
								    		Vector info = (Vector)checkServiceDetail.elementAt(y);
								    		poid_id0 = (String)info.elementAt(0);
								    		poid_Type = (String)info.elementAt(1);	
								    		loginGet = (String)info.elementAt(2);							
								    		
								    	}
								    	
								    	outputReturn = "[Siebel] Try to purchase deal but '" + poid_Type + " " + poid_id0 + "' (Login:" + loginGet + ") sent Tied to other BA in BRM " + accountNumberInBRM + ". Not as send BA " + accountNumberSent;
									}
									else
									{
										outputReturn = "[EAI] Try to purchase deal but service not found in BRM";
									}
									

									
								
								}
							}
							else if(result.equalsIgnoreCase("10102"))
							{
								outputReturn = "[EAI] Service is InActive in BRM";
							}
							else if(result.equalsIgnoreCase("10103"))
							{
								outputReturn = "[EAI] Service poid sent is already Closed in BRM";
							}
							else
							{
								outputReturn = "[EAI] Service is 0.0.0.1 /service/telephony " + o.servicePoid + " 0 not found in BRM. Please re-create service then re-trigger";
							}
						}
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_CUST_MODIFY_PROFILE"))
					{
						//double check cost center
						CheckCostCenterMapping checkCost = new CheckCostCenterMapping();
						
						checkCost.err_Blank_Value(fileURL);
				    	
				    	
						String checkField = checkCost.checkFieldTableTmSvcProfile(checkCost.getTmSvcProfPoid());
				    	
				    	if(!checkField.equalsIgnoreCase("Null"))
				    	{
				    	
					    	String result = checkCost.checkMappingCostCenter(checkCost.getCostCenterVal(), checkCost.getIndustrialCod(), checkCost.getSegmentCod());
					    	
					    	//System.out.println("Result is " + result);
					    	
					    	if(result.equalsIgnoreCase("Null"))
					    	{
					    		outputReturn = "Cost Center mapping not found in BRM for -> Cost Center = " + checkCost.getCostCenterVal() + ", Industrial Code = " + checkCost.getIndustrialCod() + " Segment Code = " + checkCost.getSegmentCod();
					    	}
					    	else
					    	{
					    		outputReturn = "Cost center mapping found";  		
					    	}
				    	}
				    	else
				    	{
				    		outputReturn = "Field TM_FLD_TRANSLATED_NUM not exist in BRM"; 
				    	}
						
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_READ_OBJ"))
					{

				    	OpcodeReadObj opc = new OpcodeReadObj();
				    	
				    	opc.missingPoidInPurchasedTable(fileURL);
				    	
				    		
				    		String purchaseType = "Null";
				    		String result = "Null";
				    		
				    		if(opc.getPurchasedPoidString().indexOf("product") > -1)
				    		{
				    			purchaseType = "Product";
				    			
				    			result = opc.checkPurchaseTable(opc.getPurchasedPoid(), purchaseType);
				    			
				    			if(result.equalsIgnoreCase("Null"))
				    			{
				    				outputReturn = "[EAI] " + opc.getPurchasedPoidString() + " not found in BRM.";
				    			}
				    			else
				    			{
				    				outputReturn = "[OSM] Please re-trigger";
				    			}
				    			
				    			
				    		}
				    		else if(opc.getPurchasedPoidString().indexOf("discount") > -1)
				    		{
				    			purchaseType = "Discount";
				    			
				   			result = opc.checkPurchaseTable(opc.getPurchasedPoid(), purchaseType);
				    			
				    			if(result.equalsIgnoreCase("Null"))
				    			{
				    				outputReturn = "[EAI] " + opc.getPurchasedPoidString() + " not found in BRM.";
				    			}
				    			else
				    			{
				    				outputReturn = "[OSM] Please re-trigger";
				    			}
				    		}
				    		else
				    		{
				    			outputReturn = "Poid sent not found in BRM. " + opc.getPurchasedPoidString();
				    		}
				    	
				    	
				    
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_CUST_COMMIT_CUSTOMER"))
					{
						
						OpcodeCreateAccount opca = new OpcodeCreateAccount();
						
						String accountPoid = "Null";
						String created = "Null";
						
						opca.checkCreateAccOpcode(fileURL);
						
						Vector accountDetail = new Vector();
							
						//System.out.println("asdadasd " + opca.getAccountNumber());
						accountDetail = opca.checkAccountNumber(opca.getAccountNumber());
						
						if(accountDetail.size() > 0)
						{
					    	for(int y=0;y<accountDetail.size();y++)
					    	{
					    		//System.out.println(y+1 + ":: " + y);
					    		Vector info = (Vector)accountDetail.elementAt(y);
					    		accountPoid = (String)info.elementAt(0);
					    		created = (String)info.elementAt(1);						
					    		
					    	}
					    	
							outputReturn = "BA " + opca.getAccountNumber() + " already SUCCESS created in BRM on " + created + " with poid '/account " + accountPoid + "'";
							
						}
						else
						{
							
							outputReturn = "Account not yet created in BRM";
						}
					}
						 
					
					
					//check blank poid 
					CheckBlankValue checkBlank = new CheckBlankValue();
					checkBlank.err_Blank_Value(fileURL);
						
					if(checkBlank.getOpcodeLineReturn().indexOf("BLANK")> -1)
					
					outputReturn = "Field " + checkBlank.getOpcodeLineReturn() + " Null. Please fill in then re-trigger.";
					
					
					
					p2.close();
			    	
	
			    	
			    	
				}
				else
				{
					System.out.println("Vector is null");
					opcode = "Cannot find BRM opcode";
					
				}
			}
			else
			{
				System.out.println("No EAI ID");
				opcode = "Cannot find BRM opcode";
			}
			

			//p1.println(eaiID + "||" + auditDate + "||" + opcode);
			
			
		    
			
			p1.println(strLine + "|" + auditDate + "|" + agingCount +"|" + opcode + "|" + outputReturn);
			
			
			
			strLine = "Null";						
			eaiID = "Null";
			auditDate = "Null";
			opcode = "Null";
			payload = "Null";
			outputReturn = "Null";
			
			x++;
		}
    	
   
    	
    }

    
    
    
    
    public Vector checkEAITable(String eaiID)
    {
        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		
		try
		{
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();

			
			//String sqlStmt = "select int_msg_id,to_char(audit_date_time, 'dd-Mon-yyyy hh24:mi:ss') as audit_date_time,audit_param_2,payload "
			String sqlStmt = "select int_msg_id,cast(audit_date_time as varchar(40)) as audit_date_time,audit_param_2,payload "
					+ "from eai_custom.eai_audit_log "
					+ "where int_msg_id = '" + eaiID + "' and audit_type = 'WSRQ' order by audit_date_time desc";
			
			
			
			
            Vector LineInfo = null;
            System.out.println("------------query BRM NOVA");
            
            Vector allVector = new Vector();
            
           
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("int_msg_id"));
				LineInfo.add(rs.getString("audit_date_time"));
				LineInfo.add(rs.getString("audit_param_2"));
				LineInfo.add(rs.getString("payload"));
	
				
				
				System.out.println("<>-------------------<>");
				System.out.println("3: " + rs.getString("int_msg_id"));
				System.out.println("6: " + rs.getString("audit_date_time"));
				System.out.println("6: " + rs.getString("audit_param_2"));
				System.out.println("<>-------------------<>");
				
				//allVector = new Vector();
				allVector.add(LineInfo);
				//x++;
			}
			
			System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
			System.out.println("||||||||allVector||||||||" + allVector.size());
			
			//for(int y = 0;y<allVector.size();y++)
			//{
				
			//	System.out.println("---->" + allVector.elementAt(y));
			///}
			
			accc = allVector;
			
        }
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Fuckrror when trying to query. " + e);
		}
		finally
		{
			try
			{
				stmt.close();
				rs.close();
				conn.close();
			}
			catch(Exception e)
			{
				System.out.println("error closing stmt, rs, conn " + e);
			}
		}
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return accc;
    }
		
    
    public Vector checkEAITable_Miss_XREF(String eaiID)
    {
        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		
		try
		{
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();

			
			//String sqlStmt = "select int_msg_id,to_char(audit_date_time, 'dd-Mon-yyyy hh24:mi:ss') as audit_date_time,audit_param_2,payload "
			String sqlStmt = "select int_msg_id,cast(audit_date_time as varchar(40)) as audit_date_time,audit_param_2,payload "
					+ "from eai_custom.eai_audit_log "
					+ "where int_msg_id = '" + eaiID + "' and audit_type = 'ERR' order by audit_date_time desc";
			
			
			
			
            Vector LineInfo = null;
            System.out.println("------------query BRM NOVA");
            
            Vector allVector = new Vector();
            
           
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("int_msg_id"));
				LineInfo.add(rs.getString("audit_date_time"));
				LineInfo.add(rs.getString("audit_param_2"));
				LineInfo.add(rs.getString("payload"));
	
				
				
				System.out.println("<>-------------------<>");
				System.out.println("0: " + rs.getString("int_msg_id"));
				System.out.println("1: " + rs.getString("audit_date_time"));
				System.out.println("2: " + rs.getString("audit_param_2"));
				System.out.println("3: " + rs.getString("payload"));
				System.out.println("<>-------------------<>");
				
				//allVector = new Vector();
				allVector.add(LineInfo);
				//x++;
			}
			
			System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
			System.out.println("||||||||allVector||||||||" + allVector.size());
			
			//for(int y = 0;y<allVector.size();y++)
			//{
				
			//	System.out.println("---->" + allVector.elementAt(y));
			//}
			
			accc = allVector;
			
        }
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Fuckrror when trying to query. " + e);
		}
		finally
		{
			try
			{
				stmt.close();
				rs.close();
				conn.close();
			}
			catch(Exception e)
			{
				System.out.println("error closing stmt, rs, conn " + e);
			}
		}
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return accc;
    }
    
    
    
    private Connection getDBConn(String dbLogin, String dbPassword, String dbIp)
    {
        Connection con = null;
        try
        {

        	con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + dbIp + ")(PORT=1521))(CONNECT_DATA=(SERVICE_NAME = NEAIPRD)))", dbLogin, dbPassword);
        	
        	//con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 10.41.23.166) (PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.41.23.167) (PORT = 1521))(LOAD_BALANCE = yes)(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = HBRMPRD) (FAILOVER_MODE =(TYPE = SESSION)(METHOD = BASIC)(RETRIES = 20)(DELAY = 5))))", dbLogin, dbPassword);
        	
        }
        catch(Exception e)
        {
            System.out.println("Error create new connection to database, " + e);
            return null;
        }
        return con;
    }
    
    
    

    
    
    
    
    
 
    
    

}
