package ezen.ezencompany.util;

import java.io.File;

public class Path {
	// 최종경로는 D:\\EzenCompany\\Project\\src\\main\\webapp\\resources 리소스까지로 하는게 좋을듯함.
	static String Path ="D:\\EzenCompany\\Project\\src\\main\\webapp\\resources";	
	static String LinuxPath ="/usr/home";
	public static String getPath() {
		
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
		    //System.out.println("This is Unix or Linux");
			return LinuxPath;
		}
		return Path;
	}
	
	public static String getUploadPath(String realPath , String[] subPath) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(realPath);
		for(String s : subPath){
			buffer.append(File.separator); 
			buffer.append(s);
		}
		
		// 경로가 없으면 생성.
		File dir = new File(buffer.toString());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		return buffer.toString();
	}
}
