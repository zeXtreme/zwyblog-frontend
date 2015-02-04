package me.zwy.getattach;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.BASE64DecoderStream;

public class Demo {

	public static void main(String[] args) throws Exception{
		File file = new File("C:/Users/zWX229503/Desktop/eml文件附件提取/(不好意思，请以此份为准）田震燃+综治监察.eml");
		InputStream is = new FileInputStream(file);
		MimeMessage mail = new MimeMessage(Session.getDefaultInstance(new Properties()), is);
		Multipart part = (Multipart) mail.getContent();
		int count = part.getCount();
		for(int i=0;i<count;i++){
			BodyPart bp = part.getBodyPart(i);
			System.out.println(bp.getContent());
			if(bp.getContent() instanceof MimeMultipart){
				Multipart bps = (Multipart) bp.getContent();
				int counts = bps.getCount();
				for(int j=0;j<counts;j++){
					BodyPart bpss = bps.getBodyPart(j);
					System.out.println(bpss.getContent());
				}
			}else if(bp.getContent() instanceof BASE64DecoderStream){
				String fileName = bp.getFileName();
				String[] fs = fileName.split("\\?");
				String fn = new String(Base64.getDecoder().decode(fs[3]), fs[1]);
				BASE64DecoderStream in = (BASE64DecoderStream) bp.getContent();
				FileOutputStream out = new FileOutputStream("D:/test/" + fn);
				int n;
				while((n = in.read()) != -1){
					out.write(n);
				}
				in.close();
				out.flush();
				out.close();
			}
		}
	}

}
