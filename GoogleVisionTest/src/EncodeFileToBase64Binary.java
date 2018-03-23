import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class EncodeFileToBase64Binary {
	//檔案都能變成Base64
	public String getBase64(File file) {
		String encodedfile = null;
	    try {
	        FileInputStream fileInputStreamReader = new FileInputStream(file);
	        byte[] bytes = new byte[(int)file.length()];
	        fileInputStreamReader.read(bytes);
	        encodedfile = new String(Base64.encodeBase64(bytes),"UTF-8");
	        fileInputStreamReader.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return encodedfile;
	}
}
