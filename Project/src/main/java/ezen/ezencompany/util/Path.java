package ezen.ezencompany.util;

import java.io.File;

public class Path {
	// 최종경로는 D:\\EzenCompany\\Project\\src\\main\\webapp\\resources 리소스까지로 하는게 좋을듯함.
	static String Path ="D:\\EzenCompany\\Project\\src\\main\\webapp";	
	
	public static String getPath() {
		
		String osName = System.getProperty("os.name").toLowerCase();
		
		//System.out.println("os.name property: " + osName);
		
		if (osName.contains("win")) {
			Path = Path.replace("//", "/");
		} else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
		    System.out.println("This is Unix or Linux");
		}else {
		    System.out.println("오류");
		}
        
		return Path;
	}
	
	public static String getUploadPath(String[] subPath) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(getPath());
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
