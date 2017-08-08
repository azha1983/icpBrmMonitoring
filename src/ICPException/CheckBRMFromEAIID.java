package ICPException;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Vector;

import brm.CheckSBLStatus;



public class CheckBRMFromEAIID {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
	   public String checkEAIOPcodeSentDetail(String EAI_ID) throws Exception
	    {

			
			String returnValue = "Null";
			String resultBRMResp = "Null";
			
			String eaiID = "Null";
			String auditDate = "Null";
			String opcode = "Null";
			String payload = "Null";
			String fileURL = "Null";
			String outputReturn = "Null";
			String agingCount = "Null";
			
			boolean doubleCheckCancel = false;
			
			PrintStream p2;
			
			CheckingOpcodeFromEAI co = new CheckingOpcodeFromEAI();
			
		    if(!EAI_ID.equalsIgnoreCase("Null"))
			{
				
				Vector output1 = new Vector();
				
				output1 = co.checkEAITable(EAI_ID);
				
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
					
					//---------------------------------------------------------------------------------->>
					CheckBRMErrResponse cbrm = new CheckBRMErrResponse();
					
			    	resultBRMResp = cbrm.CheckErrRespPayload(eaiID);
			    	
			    	if(resultBRMResp.equalsIgnoreCase("Null"))
			    	{
			    		resultBRMResp = cbrm.CheckErrRespPayload2nd(eaiID);
			    	}
					
					
			    	if(resultBRMResp.indexOf("TRANS") > -1 || resultBRMResp.indexOf("NAP") > -1 || resultBRMResp.indexOf("CONNECT") > -1 || resultBRMResp.indexOf("STREAM_EOF") > -1  )
			    	{
			    		outputReturn = "[OSM] Please re-trigger";
			    	}
			    	else if(resultBRMResp.indexOf("GST") > -1 )
			    	{
			    		//outputReturn = "[BRM] GST inflight order issue. BRM to update missing tax code";
			    		outputReturn = "[OSM] Please re-trigger";
			    	}
			    	else if(resultBRMResp.indexOf("XREF") > -1 )
			    	{
			    		outputReturn = "[EAI] Unsync Asset Integration ID between SIEBEL - EAI";
			    	}
			    	else if(resultBRMResp.indexOf("BAD_ARG") > -1 )
			    	{
			    		outputReturn = "[BRM] BRM to investigate. ERR_BAD_ARG issue";
			    	}
			    	else
			    	{
						
					System.out.println("---> " + outputReturn);
					
					//check blank poid 
					CheckBlankValue checkBlank = new CheckBlankValue();
					checkBlank.err_Blank_Value(fileURL);
						
					if(checkBlank.getOpcodeLineReturn().indexOf("BLANK")> -1)
					{
					outputReturn = "[EAI] Field " + checkBlank.getOpcodeLineReturn() + " Null. Please fill in then re-trigger.";
					}
					else
					{																
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
						
						String productType = "Null";
						
						if(opc.getOpcodeType().equalsIgnoreCase("30001"))
							productType = "/purchased_product";	
						else if(opc.getOpcodeType().equalsIgnoreCase("30002"))
							productType = "/purchased_discount";
						else
							productType = "Unknown";
												
						if(!opc.wrongOpcode)
						{
							statusGet = opc.checkProdDiscPuchaseStatus(opc.purchasePoid,opc.getOpcodeType());
							
							if(statusGet.equalsIgnoreCase("3"))
							{
								doubleCheckCancel = true;
								String prodType = "Null";
								prodType = opc.checkProdType(opc.purchasePoid,opc.getOpcodeType());
								outputReturn = "[EAI] Purchased Poid = '" + opc.getPurchasePoidString() + "'+Product/Discount Type = '" + prodType + "' already cancelled in BRM";
							}
							else if(statusGet.equalsIgnoreCase("2"))
							{
								outputReturn = "[EAI] Discount/Product status is inActive in BRM, please re-trigger";
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
								outputReturn = "[EAI] Service '" + ch.getServicePoidString() + "' Not found in BRM";
							}
							else if(statusReturn.equalsIgnoreCase(ch.getServiceStatus()))
							{
								outputReturn = "[OSM] Service '" + ch.getServicePoidString() +"' status already UPDATE in BRM.EAI to update mapping, OSM to re-trigger";
							}
							else
							{

								String alias_t_value = ch.checkAlias_TStatus(ch.getServicePoid());
								
								if(alias_t_value.equalsIgnoreCase("2"))
								{
									outputReturn = "[BRM] Service status in BRM is " + statusReturn + " but try to set status " + ch.getServiceStatus() + ". Value in alias_t is more than 1.";
								}
								else if(alias_t_value.equalsIgnoreCase("1"))
								{
									outputReturn = "[OSM] Service status in BRM is " + statusReturn + " but try to set status " + ch.getServiceStatus() + ". OSM to re-trigger.";
								}
								else if (alias_t_value.equalsIgnoreCase("0"))
								{
									outputReturn = "[BRM] Service status in BRM is " + statusReturn + " but try to set status " + ch.getServiceStatus() + ". No value in alias_t table.";
								}
								else
								{
									outputReturn = "[BRM] Service status in BRM is " + statusReturn + " but try to set status " + ch.getServiceStatus() + ". BRM to check error";
								}
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
									System.out.println("Purchase date to be patch is  " + resultDate);
									//System.out.println("Purchase date sent is EARLIER than account effective date. Need to fix\n\n");
									outputReturn = "[OSM] Please replace field <START_T> and <END_T> with this value " + resultDate + " then re-trigger";
									
								}
								else
								{
									//outputReturn = "Date Purchase GREATER than Account effective date. Should be no problem. BRM to check other error";
									outputReturn = "[OSM] Please re-trigger";
								}
								
								
							}
							else
							{
								outputReturn = "[BRM] Cannot get account effective date";
							}
						
							
							
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
												outputReturn = "[OSM] Please replace field <START_T> and <END_T> with this value " + resultDate + " then re-trigger";
												
											}
											else
											{
												//outputReturn = "Date Purchase GREATER than Account effective date. Should be no problem. BRM to check other error";
												outputReturn = "[OSM] Please re-trigger";
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
									
									accountNumberInBRM = o.checkAccountNumber(accountPoidSent);//accountpoidInBRM
									
									accountNumberSent = o.checkAccountNumber(o.getAccountPoid());
									
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
								outputReturn = "[EAI] Service poid sent is InActive in BRM";
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
					else if(opcode.equalsIgnoreCase("PCM_OP_CUST_MODIFY_PROFILE"))//nanti update balik
					{
						//double check cost center
						/*CheckCostCenterMapping checkCost = new CheckCostCenterMapping();
						
						checkCost.err_Blank_Value(fileURL);
				    	
				    	
						String checkField = checkCost.checkFieldTableTmSvcProfile(checkCost.getTmSvcProfPoid());
						
				    	if(!checkField.equalsIgnoreCase("Null"))
				    	{
				    	
					    	String result = checkCost.checkMappingCostCenter(checkCost.getCostCenterVal(), checkCost.getIndustrialCod(), checkCost.getSegmentCod());
					    	
					    	
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
				    	}*/
						
						outputReturn = "BRM to check. Modify profile - cost center mapping issue."; 
						
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
				    			outputReturn = "[EAI] Poid sent not found in BRM. " + opc.getPurchasedPoidString();
				    		}
				    	
				    	
				    
					}
					else if(opcode.equalsIgnoreCase("40007"))
					{
						
						Opcode40007 op4 = new Opcode40007();
						
						String serviceAccPoidinBRM = "Null";
						
						op4.checkTransferServiceFList(fileURL);
						
						if(!op4.getServiceTransfer().equalsIgnoreCase("Null"))
						{
							//check service tied 1st
							String BAtiedInBRM = "Null";
							String BAFrom = "Null";
							String BATo = "Null";
							
							serviceAccPoidinBRM = op4.serviceTiedToBA(op4.getServiceTransfer());
							
							if(!serviceAccPoidinBRM.equalsIgnoreCase("Null"))
							{
								if(op4.getAccPoidFrom().equalsIgnoreCase(serviceAccPoidinBRM))
								{
									System.out.println("acc poid sent same with in the FList. service tied is right, check billinfo");
									//acc poid sent same with in the FList. service tied is right, check billinfo
									outputReturn = "[BRM] Service tied to right BA and transfer service not success. BRM to check other error";
								}
								else if(op4.getAccPoidTo().equalsIgnoreCase(serviceAccPoidinBRM))
								{
									System.out.println("acc poid sent same with TO acc obj. Means service already transfer. OSM to bypass");
									//acc poid sent same with TO acc obj. Means service already transfer. OSM to bypass
									String loginReturn = "Null";
									
									BATo = op4.checkAccountNumber(serviceAccPoidinBRM);
									BAFrom = op4.checkAccountNumber(op4.getAccPoidFrom());
									loginReturn = op4.serviceLoginInBRM(op4.serviceTransfer);
									
									outputReturn = "[OSM] Service "  + op4.getServiceTransferString() + "(Login:" + loginReturn + ") already transfer from BA " + BAFrom + " to BA " + BATo + ". OSM to bypass";
								}
								else
								{
									System.out.println("acc poid sent not same in the FList either FROM or TO. Get BA and ask L2 to check");
									//acc poid sent not same in the FList either FROM or TO. Get BA and ask L2 to check
									String loginReturn = "Null";
									
									BAtiedInBRM = op4.checkAccountNumber(serviceAccPoidinBRM);
									BAFrom = op4.checkAccountNumber(op4.getAccPoidFrom());
									loginReturn = op4.serviceLoginInBRM(op4.serviceTransfer);
									
									outputReturn = "[Siebel] Try to transfer service but "  + op4.getServiceTransferString() + "(Login:" + loginReturn + ") tied to BA " + BAtiedInBRM + " in BRM. Not as sent BA " + BAFrom;
														
								}
							}
							else
							{
								outputReturn = "[EAI] Try to transfer service but " + op4.getServiceTransferString() + " not found in BRM. Please re-create service, then re-trigger" ;
							}
							
							
						}
						else
						{
							//System.out.println("[EAI] Failed to transfer service due to no service poid sent in the FList. Please fill in service to be transfer, then OSM to re-trigger.");
							outputReturn = "[EAI] Failed to transfer service due to no service poid sent in the FList. Please fill in service to be transfer, then OSM to re-trigger.";
						}
						
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_SEARCH"))
					{					
				    	OpcodeSearch ops = new OpcodeSearch();
				    	
				    	ops.checkCreateAccOpcode(fileURL);
				    	
				    	if(ops.isCheckOpcodeSearchType())
				    	{
				    		//outputReturn = "Check proceed";
				    		
				    		String accountPoidInDB = "Null";
				    		
				    		accountPoidInDB = ops.serviceTiedToBA(ops.getSvcPoidSent());
				    		
				    		if(accountPoidInDB.equalsIgnoreCase("Null"))
				    		{
				    			outputReturn = "[EAI] " + ops.getSvcPoidSentString() + " not found in BRM. Please re-create service then re-trigger";
				    			
				    		}
				    		else
				    		{
				    			if(accountPoidInDB.equalsIgnoreCase(ops.getAccPoidSent()))
				    			{
				    				outputReturn = "[OSM] Service poid sent tied to right BA. Should be no problem. Please re-trigger";
				    			}
				    			else
				    			{
				    				String loginInBRM = "Null";
				    				String BAtiedinBRM = "Null";
				    				String BAsent = "Null";
				    				
				    				BAtiedinBRM = ops.checkAccountNumber(accountPoidInDB);
				    				BAsent = ops.checkAccountNumber(ops.getAccPoidSent());
				    				loginInBRM = ops.serviceLoginInBRM(ops.getSvcPoidSent());
				    				
				    				
				    				outputReturn = "[Siebel] Service " + ops.getSvcPoidSentString() + " (login:" + loginInBRM + ") tied to BA " + BAtiedinBRM + " in BRM. Not as sent BA " + BAsent;
				    			}
				    			
				    		}
				    		
				    	}			    	
				    	else
				    	{
				    		outputReturn = "[BRM] Search opcode others. BRM to check";
				    	}
					}
					else if(opcode.equalsIgnoreCase("PCM_OP_CUST_COMMIT_CUSTOMER"))
					{
						OpcodeCreateAccount opca = new OpcodeCreateAccount();
						
						
						
						opca.checkCreateAccOpcode(fileURL);
						
						String accountPoid = "Null";
						String created = "Null";
						
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
					    	
							outputReturn = "[OSM] BA " + opca.getAccountNumber() + " already SUCCESS created in BRM on " + created + " with poid '/account " + accountPoid + "'. OSM to re-trigger/bypass depend on next task";
							
						}
						else
						{
							
							outputReturn = "[BRM] Account not yet created in BRM. BRM to check";
						}
						//outputReturn = "[OSM] Please re-trigger"; 
					}
					

					
					}
			    	}
					//p2.close();
			    	

			    	
			    	
				}
				else
				{
					System.out.println("Vector is null");
					opcode = "Cannot find BRM opcode";
					
					System.out.println("lalalala");
					Vector output2 = new Vector();
					
					output2 = co.checkEAITable_Miss_XREF(EAI_ID);
					
					if(output2.size()>0)
					{
			    		System.out.println("Vector 1 not null...proceed");
			    		
				    	for(int u=0;u<output2.size();u++)
				    	{
				    		System.out.println(u+1 + ":: " + u);
				    		Vector info = (Vector)output2.elementAt(u);
				    		eaiID = (String)info.elementAt(0);
				    		auditDate = (String)info.elementAt(1);	
				    		opcode = (String)info.elementAt(2);	
				    		payload = (String)info.elementAt(3);	
							
				    		//System.out.println(eaiID + "||||" + auditDate + "===" + eaiID + "||||" + auditDate);
				    		break;
				    		
				    	}
				    	
						fileURL = res.getString("file15.payload");
						
						FileOutputStream out2 = new FileOutputStream(fileURL);
						p2 = new PrintStream(out2);
						
						
						p2.println(payload);
						outputReturn = "Null"; 
						
						boolean returnValueEAI = false;
						
						CheckXREF_Mapping xref = new CheckXREF_Mapping();
						
						returnValueEAI = xref.checkXref_Err_Return(fileURL);
						
						if(returnValueEAI)
						{
							outputReturn = "[EAI] Unsync Asset Integration ID between SIEBEL - EAI";
						}
						else
						{
							outputReturn = "[EAI] EAI Exception";
						}
						
						opcode = "No Opcode";
				    	
					}
					else
					{
						System.out.println("No EAI ID");
						opcode = "Cannot find BRM opcode";
					}
					
				}
			}
			else
			{
				System.out.println("No EAI ID - need to check either this is Create Account opcode");
				opcode = "Cannot find BRM opcode";
			}
			

			//p1.println(eaiID + "||" + auditDate + "||" + opcode);
			
			
		if(opcode == null)
			opcode = "No Opcode";
		
		CheckBRMErrResponse cbrm = new CheckBRMErrResponse();
		
    	resultBRMResp = cbrm.CheckErrRespPayload(eaiID);
    	
    	if(resultBRMResp.equalsIgnoreCase("Null"))
    	{
    		resultBRMResp = cbrm.CheckErrRespPayload2nd(eaiID);
    	}

    	
    	if(resultBRMResp.indexOf("TRANS") > -1 || resultBRMResp.indexOf("NAP") > -1 || resultBRMResp.indexOf("CONNECT") > -1 || resultBRMResp.indexOf("STREAM_EOF") > -1  )
    	{
    		outputReturn = "[OSM] Connection issue. Please re-trigger";
    	}
    	else if(resultBRMResp.indexOf("GST") > -1 )
    	{
    		//outputReturn = "[BRM] GST inflight order issue. BRM to update missing tax code";
    		outputReturn = "[OSM] Please re-trigger";
    	}
    	else if(resultBRMResp.indexOf("XREF") > -1 )
    	{
    		outputReturn = "[EAI] Unsync Asset Integration ID between SIEBEL - EAI";
    	}
    	else if(resultBRMResp.indexOf("BAD_ARG") > -1 )
    	{
    		if(doubleCheckCancel==false)
    		outputReturn = "[BRM] BRM to investigate. ERR_BAD_ARG issue";
    	}
    	else
    	{
    		outputReturn = outputReturn;
    	}
			
		System.out.println(EAI_ID + "|" + auditDate + "|" + opcode + "|" + outputReturn);
		
		returnValue = EAI_ID + "|" + auditDate + "|" + opcode + "|" + outputReturn;
			
			
		return returnValue;
		
	    }
}
