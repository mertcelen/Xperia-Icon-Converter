import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import brut.androlib.AndrolibException;
import brut.androlib.ApkDecoder;
import brut.directory.DirectoryException;


public class ConvertSystem {
	public static Calendar calendar;
	public static String deviceId;
	
	public static String init(){
		calendar = Calendar.getInstance();
		deviceId = findId();
		String str = "";
		if(!deviceId.equals("")){
			MainFrame.statusLabel.setText("Status : Device found "+  deviceId + "\n");
			getApps();
		}else{
			MainFrame.statusLabel.setText("Status : Device NOT found, try reconnect.\n");
		}
		return str;
	}
	
	public static void getApps(){
		Runtime run = Runtime.getRuntime();
		try {
			String line = "";
			Process dummy = run.exec("./adb shell pm list packages -f -3");//-3 parameter sometimes not display all apps
			BufferedReader buf = new BufferedReader(new InputStreamReader(dummy.getInputStream()));
			while ((line=buf.readLine())!=null) {
//				System.out.println(line);
				
//				get package from line
				line = line.substring(8);
				String[] a = line.split("=");
//				dummy = run.exec("");
				
				if(!a[0].contains("/system/") && !a[0].contains("/vendor/") && !a[0].contains("com.google.android") &&
						!a[0].contains("com.sony") && !a[0].equals(null) && !a[0].contains("com.android")){
					MainFrame.applicationList.addItem(a[0]);
					System.out.println(a[0]);
				}	
				line = buf.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String findId(){
		Runtime run = Runtime.getRuntime();
		String deviceID =null;
		try {
//			First kill existing server
//			run.exec("./adb kill-server");
//			MainFrame.textArea.append(calendar.getTime() + " Killing existing adb server.\n");
//			run.wait();
//			run.exec("./adb start-server");
//			MainFrame.textArea.append(calendar.getTime() + " Creating adb server.\n");
//			run.wait();
			String command = "./adb devices";
//			MainFrame.textArea.append(calendar.getTime() + " Getting device information from adb.\n");
			Process pr = run.exec(command);
			pr.waitFor();
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			deviceID=buf.readLine();//to just ignore headers
			deviceID=buf.readLine();
			String[] dummy = deviceID.split("	");
			deviceID = dummy[0];
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceID;
	}
	
	public static boolean convert(String str){
		Runtime run = Runtime.getRuntime();
		String command = "./adb pull " + str;
		try {
			MainFrame.textArea.append("Getting apk from device..\n");
			Process pr = run.exec(command);
			pr.waitFor();
			
//			decompile
			MainFrame.textArea.append("Decompile starts... \n");
			ApkDecoder decoder = new ApkDecoder();
			pr = run.exec("rm -rf apk/");
			
			pr.waitFor();
			decoder.setApkFile(new File("base.apk"));
			decoder.setOutDir(new File("apk"));
			decoder.decode();
			MainFrame.textArea.append("Decompile finished, now reading files... \n");
			int count = new File("apk/res/drawable-nodpi-v4/").list().length;
			MainFrame.textArea.append(count + " icon found\n");
			MainFrame.textArea.append("Copy them... \n");
			
			run.exec("mkdir base/res/drawable-nodpi-v4");
			
			File destinationFolder = new File("base/res/drawable-nodpi-v4");
		    File sourceFolder = new File("apk/res/drawable-nodpi-v4");
		    File[] listOfFiles = sourceFolder.listFiles();
//		    String name = setAppName();
		    String name = "alpha";
		    if (listOfFiles != null)
	        {
	            for (File child : listOfFiles )
	            {
	                // Move files to destination folder
	                child.renameTo(new File(destinationFolder + "/" + child.getName()));
	            }
	        }
		    new File("apk/res/xml/appfilter.xml").renameTo(new File("base/res/xml/appfilter.xml"));
		    
			MainFrame.textArea.append("Copied, now recompile... \n");
			pr = run.exec("java -jar apktool_2.1.0.jar b base");
			pr.waitFor();
			new File("base/dist/Framed.apk").renameTo(new File("converted.apk"));
			
			MainFrame.textArea.append("Compiled, now sign... \n");
			pr = run.exec("java -jar SignApk.jar testkey.x509.pem testkey.pk8 converted.apk " + name + ".apk");
			pr.waitFor();
			
			MainFrame.textArea.append("Apk ready,now push to device... \n");
			pr = run.exec("./adb install -r " + name + ".apk");
			pr.waitFor();
			
			MainFrame.textArea.append("Installed to phone, have a nice day!... \n");
			pr = run.exec("rm -rf base.apk converted.apk apk/ base/res/drawable-nodpi-v4/ alpha.apk base/build/");
			pr.waitFor();
			
			return true;
		} catch (IOException | AndrolibException | DirectoryException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static String setAppName(){
		String name = "";
//		first lets find it
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("apk/AndroidManifest.xml");
			Node dummy = doc.getElementsByTagName("application").item(0);
			Element e = doc.getDocumentElement();
			String id = e.getAttribute("android:label");
//			got id, not get specific name
			if(id.startsWith("@string/")){
				id = id.substring(8);
			}
//			now get name in strings xml
			
			doc = builder.parse("apk/res/values/strings.xml");
			NodeList liste = doc.getElementsByTagName("string");

			for(int i=0; i<liste.getLength();i++){
				Node n = liste.item(i);
				Element element = (Element) n;
				if(element.getAttribute("name").compareTo(id)==0){
					name = n.getTextContent();
					break;
				}
			}
			
			MainFrame.textArea.setText("App name is " + name);
//			ok now we can set this value
			builder.parse("base/AndroidManifest.xml");
			dummy = doc.getElementsByTagName("activity").item(0);
			e = (Element) dummy;
			e.setAttribute("android:label", name);
			
			
			} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return name;
	}
}
