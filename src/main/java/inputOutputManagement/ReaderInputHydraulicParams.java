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


package inputOutputManagement;

import java.util.ArrayList;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

import java.io.*;


/**
 * The Class ReaderInputData reads the input files extracting the data of interests.
 * INPUTS:
 * pathToShape: Path to the file with the params
 * code profile
 * 
 * OUTPUTS:
 * Arraylist with:
 * 
 * thickness of each layer
 * 
 * In case of Beta model:
 * beta_exponentialModel
 * saturated_conductivity_exponentialModel
 * saturated_waterContent_exponentialModel
 * 
 * In case of VG model:
 * saturated_waterContent_VG
 * residual_waterContent_VG
 * saturated_conductivity_VG
 * n_VG
 * tau_VG
 */
public class ReaderInputHydraulicParams {

	//INPUT
	public String pathToShape;	
	public String codeProfile;
	
	//OUTPUT
	public ArrayList <Double> thickness= new ArrayList <Double>();

	public ArrayList <Double> beta_exponentialModel= new ArrayList <Double>();
	public ArrayList <Double> saturated_conductivity_exponentialModel= new ArrayList <Double>();
	public ArrayList <Double> saturated_waterContent_exponentialModel= new ArrayList <Double>();

	public ArrayList <Double> saturated_waterContent_VG= new ArrayList <Double>();
	public ArrayList <Double> residual_waterContent_VG= new ArrayList <Double>();
	public ArrayList <Double> saturated_conductivity_VG= new ArrayList <Double>();
	public ArrayList <Double> n_VG= new ArrayList <Double>();
	public ArrayList <Double> tau_VG= new ArrayList <Double>();
	public ArrayList <Double> alpha_VG= new ArrayList <Double>();
	
	public ArrayList <String> pathToHydraData= new ArrayList <String>();




	public void process() throws Exception { 

		File f = new File(pathToShape);
        ShapefileDataStore shapefile = new ShapefileDataStore(f.toURI().toURL());

        SimpleFeatureIterator features = shapefile.getFeatureSource().getFeatures().features();
        SimpleFeature shp;
        

        
        while (features.hasNext()) {
        	shp = features.next();
        	String code=(String) shp.getAttribute("P_code");
        	
        	if(code.equals(codeProfile)){
        		
        										
        		
        		String depth=(String)shp.getAttribute("Depth");    		
        		thickness.add(Double.parseDouble(depth.split("-")[1])-Double.parseDouble(depth.split("-")[0]));
        		

				beta_exponentialModel.add((double) shp.getAttribute("beta_exp"));
				
				saturated_conductivity_exponentialModel.add((double)shp.getAttribute("Ks_exp"));
				
				saturated_waterContent_exponentialModel.add((double)shp.getAttribute("theta_exp"));
        		
				n_VG.add((double)shp.getAttribute("n_VG"));
				
				tau_VG.add((double)shp.getAttribute("tau_VG"));
				
				saturated_conductivity_VG.add((double)shp.getAttribute("Ks_VG"));
				
				saturated_waterContent_VG.add((double)shp.getAttribute("theta_s_VG"));
				
				residual_waterContent_VG.add((double)shp.getAttribute("theta_r_VG"));

				alpha_VG.add((double)shp.getAttribute("alpha_VG"));
				
				pathToHydraData.add((String)shp.getAttribute("hydra_data"));

        	}
        	
        }
        
        
        

        features.close();
        shapefile.dispose();




	}






}
