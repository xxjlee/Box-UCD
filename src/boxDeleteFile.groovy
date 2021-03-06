/*
*	Licensed Materials - Property of IBM Corp.
*
*	Created by Tim Bula on 2016-02-22.
*	Copyright (c) 2016 IBM. All rights reserved. 
*	
*	U.S. Government Users Restricted Rights - Use, duplication or disclosure restricted by
*	GSA ADP Schedule Contract with IBM Corp.
*
*	Author: Tim Bula
*	Plugin: Box Utilities
*	Filename: boxDeleteFile.groovy
*/

import com.urbancode.air.AirPluginTool
import java.util.Map
import java.util.LinkedHashMap
import com.box.sdk.*
import org.bouncycastle.*
import org.bouncycastle.openssl.*
import com.eclipsesource.json.*
import org.jose4j.*
import java.io.File
import java.io.FileInputStream

def apTool = new AirPluginTool(this.args[0], this.args[1])
props = apTool.getStepProperties()
final def workDir = new File('.').canonicalFile

def dev_token = props['dev_token']
def file_name = props['file_name']
//def file_id = props['file_id'] for the future if we want to save the file id property somehow
//how many levels down you want to search
def version = props['version']

//new connection to box using the dev token. Need to use the set property from the auth call in the future
System.out.println(dev_token);//this is not working need help on where to place the property after getting it from the readout
BoxAPIConnection api = new BoxAPIConnection(dev_token);

//print out user information just to confirm that it works
BoxUser.Info user_info = BoxUser.getCurrentUser(api).getInfo();
System.out.format("Welcome, %s <%s>!\n", userInfo.getName(), userInfo.getLogin());

//get root level folder to upload file. Should give user flexibility to define the folder in the future?
BoxFolder root_folder = BoxFolder.getRootFolder(api);
System.out.println(root_folder.getInfo());

//searchAndDeleteFolder(root_folder, version);
Iterable<BoxItem.Info> items =  root_folder.search(file_name);
for (BoxItem.Info itemInfo : items) {
    System.out.println(itemInfo.getName());
    if (itemInfo.getName().equals(file_name) && itemInfo instanceof BoxFile.Info) {
	    if (itemInfo.getParent().getName().equals(version)) {
	        BoxFile.Info fileInfo = (BoxFile.Info) itemInfo;
	  	    BoxFile file = fileInfo.getResource();
	        boxFile.delete();
	 	}
	}
}

/*
	  List<BoxFolder.Info> boxFolderInfoList =  boxItemInfo.getPathCollection();
	  for (BoxFolder.Info boxFolderInfo1 : boxFolderInfoList) {
		  System.out.println(boxFolderInfo1.getName());
	  }

	  if boxFolderInfoList.get(boxFolderInfoList.size() - 1).getName.equals(version) {
	  	BoxFolder.Info boxFolderInfo = (BoxFolder.Info) boxItemInfo;
	  	BoxFolder boxFolder = boxFolderInfo.getResource();
	  }
	  
	  //System.out.println(boxFolderInfo.getName());
	  */
