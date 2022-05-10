package edge.project3.util;

import java.security.SecureRandom;

public final class GenerateCodeUtil {
	private GenerateCodeUtil() {}
	
	public static String generateCode() {
		
		String code;
		
		try {
			SecureRandom random = SecureRandom.getInstanceStrong();
			code = String.valueOf(random.nextInt(9000) + 1000);
			
		} catch (Exception e) {
			throw new RuntimeException("Problem when generating the random code.");
		}
		return code;
	}
	
}
