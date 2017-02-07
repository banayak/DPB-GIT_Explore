# DPB

Dealer Performance Bonus (DPB) Program is a system for calculating payment to dealers based on several Key Performance Indicators (KPI’s).
Dealer in MBUSA may be of Smart, Sprinter or MB cars. Each dealer gets bonus for selling the car. All vehicle transaction data is stored in VISTA.

1.	Every day at 7 AM the DPB script executes and loads the data from VISTA file in DPB system.
2.	The data is stored in two table DPB_UNBLK_VEH and DPB_BLK_VEH 
3.	DPB then takes data from DPB_UNBLK_VEH table and calculates bonus. The calculated bonus is stored in DPB_CALC table
4.	The payment files are generated for each dealer and after confirmation from dealer the file is sent to CoFiCo system for payment processing
5.	DPB then generates reports i.e. daily, monthly, quarterly, yearly etc.
