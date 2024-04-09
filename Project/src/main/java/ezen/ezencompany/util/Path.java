package ezen.ezencompany.util;

public class Path {
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
	}
