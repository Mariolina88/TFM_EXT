/*
 * Copyright (c) 2019 CNR ISAFOM. All rights reserved.
 * Do not remove these lines if you modify the code. Please communicate changes
 * to the address below.
 * If either part of this code is reused, or it is ported to other
 * languages/platforms, the origin must be cited and made visible to final users.
 *
 * For more information:
 * - marialaura.bancheri@cnr.isafom.it
 * - angelo.basile@cnr.it
 */


package pedoTransfer;

import java.io.FileWriter;
import java.util.ArrayList;



public class WriterOutput {

	//INPUT
	public ArrayList <String> P_code= new ArrayList <String>();
	public String pathToOut;
	
	public ArrayList <Double> BD= new ArrayList <Double>();
	public ArrayList <Double> theta_s= new ArrayList <Double>();
	public ArrayList <Double> theta_r= new ArrayList <Double>();
	public ArrayList <Double> Ks= new ArrayList <Double>();
	public ArrayList <Double> n= new ArrayList <Double>();
	public ArrayList <Double> l= new ArrayList <Double>();
	public ArrayList <Double> alpha= new ArrayList <Double>();
	public ArrayList <String> depth= new ArrayList <String>();
	public ArrayList <Double> thickness= new ArrayList <Double>();
	public ArrayList <Double> OM= new ArrayList <Double>();
	public ArrayList <String> layer_number= new ArrayList <String>();
	public ArrayList <Double> clay= new ArrayList <Double>();
	public ArrayList <Integer> silt= new ArrayList <Integer>();
	public ArrayList <Integer> sand= new ArrayList <Integer>();




	public void process() throws Exception { 


		FileWriter csvWriter = new FileWriter(pathToOut);  

		


		//header
		csvWriter.append("P_code,X,Y,UC_sigla,profilo,Layer,Depth,thickness,"
				+ "doOptim,hydra_data,theta_r_VG,theta_s_VG,alpha_VG,n_VG,Ks_VG,"
				+ "tau_VG,beta_exp,theta_exp,Ks_exp,clay,silt,sand,bulk_den,OM,Soil_type,Class_USDA,WT_depth");	
		csvWriter.append('\n');



		for(int i=0;i<P_code.size();i++){

			int id=5000+i;
			csvWriter.append(String.valueOf(id));
			csvWriter.append(",");
			
			//X
			csvWriter.append("null");
			csvWriter.append(",");
			
			//Y
			csvWriter.append("null");
			csvWriter.append(",");
			
			//UC_sigla
			csvWriter.append("null");
			csvWriter.append(",");
			
			//profile
			csvWriter.append(P_code.get(i));
			csvWriter.append(",");
			
			//layer
			csvWriter.append(String.valueOf(layer_number.get(i)));
			csvWriter.append(",");
			
			//depth
			csvWriter.append(depth.get(i));
			csvWriter.append(",");
			
			//thickness
			csvWriter.append(String.valueOf(thickness.get(i)));
			csvWriter.append(",");
			
			
			//doOptim
			csvWriter.append("false");
			csvWriter.append(",");
			
			//hydra_data
			csvWriter.append("null");
			csvWriter.append(",");
			
			//theta_r
			csvWriter.append(String.valueOf(theta_r.get(i)));
			csvWriter.append(",");
			
			//theta_s
			csvWriter.append(String.valueOf(theta_s.get(i)));
			csvWriter.append(",");
			
			//alpha
			csvWriter.append(String.valueOf(alpha.get(i)));
			csvWriter.append(",");
			
			//n
			csvWriter.append(String.valueOf(n.get(i)));
			csvWriter.append(",");
			
			//Ks
			csvWriter.append(String.valueOf(Ks.get(i)));
			csvWriter.append(",");

			

			
			//tau
			csvWriter.append(String.valueOf(l.get(i)));
			csvWriter.append(",");
			
			//beta
			csvWriter.append("0");
			csvWriter.append(",");
			
			//theta_exp
			csvWriter.append("0");
			csvWriter.append(",");
			
			//Ks_exp
			csvWriter.append("0");
			csvWriter.append(",");
			
			//clay
			csvWriter.append(String.valueOf(clay.get(i)));
			csvWriter.append(",");
			
			//silt
			csvWriter.append(String.valueOf(silt.get(i)));
			csvWriter.append(",");
			
			//sand
			csvWriter.append(String.valueOf(sand.get(i)));
			csvWriter.append(",");
			
			//BD
			csvWriter.append(String.valueOf(BD.get(i)));
			csvWriter.append(",");
			

			//OM
			csvWriter.append(String.valueOf(OM.get(i)));
			csvWriter.append(",");
			
			//Soil_type
			csvWriter.append("null");
			csvWriter.append(",");
			
			//class_USDA
			csvWriter.append("null");
			csvWriter.append(",");
			
			//WT_depth
			csvWriter.append("null");
	
			csvWriter.append('\n');
		}



		csvWriter.flush();  
		csvWriter.close(); 



	}




}
