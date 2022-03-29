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

import java.io.IOException;
import java.util.ArrayList;

import inputOutputManagement.ReaderInputPTF;

public class PTF {

	public String pathToShape;
	public String P_code_id;
	public String depth_id;
	public String layer_number_id;
	public String clay_id;
	public String silt_id;
	public String sand_id;
	public String OM_id;
	public String bulk_density_id;
	public String pathToOutput;


	public ArrayList <Double> BD_list= new ArrayList <Double>();
	public ArrayList <Double> theta_s_list= new ArrayList <Double>();
	public ArrayList <Double> theta_r_list= new ArrayList <Double>();
	public ArrayList <Double> Ks_list= new ArrayList <Double>();
	public ArrayList <Double> n_list= new ArrayList <Double>();
	public ArrayList <Double> l_list= new ArrayList <Double>();
	public ArrayList <Double> alpha_list= new ArrayList <Double>();
	public ArrayList <Double> tau_list= new ArrayList <Double>();
	public ArrayList <String> depth_list= new ArrayList <String>();
	public ArrayList <Double> thickness_list= new ArrayList <Double>();




	public void process() throws Exception {

		ReaderInputPTF reader = new ReaderInputPTF();

		reader.pathToShape = pathToShape;
		reader.P_code_id = P_code_id;
		reader.depth_id = depth_id;
		reader.layer_number_id = layer_number_id;
		reader.clay_id = clay_id;
		reader.silt_id = silt_id;
		reader.sand_id = sand_id;
		reader.OM_id = OM_id;
		reader.bulk_density_id = bulk_density_id;

		reader.process();

		String d_1="0";


		for (int i = 0; i < reader.P_code.size(); i++) {

			Double OM_c=reader.OM.get(i)<=0.0?0.01:reader.OM.get(i);


			double sup = reader.layer_number.get(i).contains("1") ? 1 : 0;



			double BD = 1.51 + 0.0025 * reader.sand.get(i) - 0.0013 * reader.sand.get(i) * OM_c
					- 0.0006 * reader.clay.get(i) * OM_c
					- 0.0048 * reader.clay.get(i) * reader.clay.get(i) /60;


			BD_list.add(BD);

			double theta_s = 0.7919 + 0.001691 * reader.clay.get(i) - 0.29619 * BD
					- 0.000001491 * Math.pow(reader.silt.get(i), 2) + 0.0000821 * Math.pow(OM_c, 2)
					+ 0.02427 / reader.clay.get(i) + 0.01113 / reader.silt.get(i)
					+ 0.01472 * Math.log(reader.silt.get(i)) - 0.0000733 * OM_c * reader.clay.get(i)
					- 0.000619 * BD * reader.clay.get(i) - 0.001183 * BD * OM_c
					- 0.0001664 * sup * reader.silt.get(i);

			theta_s_list.add(theta_s);

			double Ks = Math.exp(7.755 + 0.0352 * reader.silt.get(i) + 0.93 * sup - 0.967 * Math.pow(BD, 2)
			- 0.000484 * Math.pow(reader.clay.get(i), 2) - 0.000322 * Math.pow(reader.silt.get(i), 2)
			+ 0.001 / reader.silt.get(i) - 0.0748 / OM_c - 0.643 * Math.log(reader.silt.get(i))
			- 0.01398 * BD * reader.clay.get(i) - 0.1673 * BD * OM_c
			+ 0.02986 * sup * reader.clay.get(i) - 0.03305 * sup * reader.silt.get(i));

			Ks_list.add(Ks);

			double alpha = Math.exp(-14.96 + 0.03135 * reader.clay.get(i) + 0.0351 * reader.silt.get(i)
			+ 0.646 * OM_c + 15.29 * BD - 0.192 * sup - 4.671 * Math.pow(BD, 2)
			- 0.000781 * Math.pow(reader.clay.get(i), 2) - 0.00687 * Math.pow(OM_c, 2)
			+ 0.0449 / OM_c + 0.0663 * Math.log(reader.silt.get(i))
			+ 0.1482 * Math.log(OM_c) - 0.04546 * BD * reader.silt.get(i)
			- 0.4852 * BD * OM_c + 0.00673 * sup * reader.clay.get(i));

			alpha_list.add(alpha);

			double var = 0.0202 + 0.0006193 * Math.pow(reader.clay.get(i), 2) - 0.001136 * Math.pow(OM_c, 2)
					- 0.2316 * Math.log(OM_c) - 0.03544 * BD * reader.clay.get(i)
					+ 0.00283 * BD * reader.silt.get(i) + 0.0488 * BD * OM_c;
			double l = 10 * (Math.exp(var) - 1) / (Math.exp(var) + 1);

			l_list.add(l);

			double n = Math.exp(-25.23-0.02195*reader.clay.get(i)+0.0074*reader.silt.get(i)-0.194*OM_c
					+45.5*BD-7.24*Math.pow(BD,2)+0.0003658*Math.pow(reader.clay.get(i), 2)
					+0.002885*Math.pow(OM_c,2) -12.81/BD-0.1524/reader.silt.get(i)
					-0.01958/OM_c-0.2876*Math.log(reader.silt.get(i))-0.0709*Math.log(OM_c)
					-44.6*Math.log(BD)-0.02264*BD*reader.clay.get(i)+0.0896*BD*OM_c+0.00718*sup*reader.clay.get(i))
					+ 1;

			n_list.add(n);


			double theta_r = 0;

			theta_r_list.add(theta_r);


			
			String d_2=String.valueOf(reader.depth.get(i));
			String Depth=(d_1+"-"+d_2);
			depth_list.add(Depth);
			

			double thikness=reader.depth.get(i)-Double.valueOf(d_1);
			thickness_list.add(thikness);
			

			
			try{
			if(Double.valueOf(reader.layer_number.get(i))<Double.valueOf(reader.layer_number.get(i+1))){
			d_1=d_2;
			} else{
				d_1="0";
			}}catch(Exception ex) {
				System.out.println("Error " + ex.getMessage());
			}
			
			
		}


		WriterOutput writer =new WriterOutput();

		writer.P_code=reader.P_code;
		writer.pathToOut=pathToOutput;
		writer.BD=BD_list;
		writer.theta_s=theta_s_list;
		writer.theta_r=theta_r_list;
		writer.Ks=Ks_list;
		writer.n=n_list;
		writer.l=l_list;
		writer.alpha=alpha_list;
		writer.depth=depth_list;
		writer.thickness=thickness_list;
		writer.OM=reader.OM;
		writer.layer_number=reader.layer_number;
		writer.clay=reader.clay;
		writer.silt=reader.silt;
		writer.sand=reader.sand;
		
		writer.process();


	}

}
