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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class FileList gets the list of the files associated to the input data in a user-specified directory
 * INPUTS:
 * - pathToInputFolder path to the folder with measured data;
 * - profile_code 
 * 
 * OUTPUT: 
 * list: list of the file 
 */
public class FileList {
	
	//IN
	public String pathToInputFolder;	
	public String profile_code;
	
	//OUT
	public Object [] list;


	public void process() throws Exception { 
		
	// extracts the list of the file in the specified folder 
	File curDir = new File(pathToInputFolder);
	File[] filesList = curDir.listFiles();
	list=list(filesList);
	
	if(list.length==0) System.out.println("No file in the specified directory");

	}
	
	
	
	private Object [] list(File[] filesList) throws Exception{

		Object [] list = null;

		ArrayList<String> arrayString = new ArrayList <String>();

		//iterates over the file to get only the .csv files with the input data
		
		for(int i=0;i<filesList.length;i++){

			if(filesList[i].getName().contains(profile_code)){
				arrayString.add(filesList[i].toString());
			}

		}
		
		
		//oreder the list according to the sequence (specified in the name of the file)
		list=arrayString.toArray();
		Arrays.sort(list);
		

		return list;
	}
}
