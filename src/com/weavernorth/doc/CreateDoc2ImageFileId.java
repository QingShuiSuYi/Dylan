package com.weavernorth.doc;

import java.io.InputStream;

import org.apache.axis.encoding.Base64;

import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.file.ImageFileManager;

import com.weavernorth.file.IOUtil;
/**
 * 根据imageFileId创建带附件的文档
 * @author Dylan
 *
 */
public class CreateDoc2ImageFileId {
	
	public static String create(String SecId,String imageFileId) {
		
		ImageFileManager im = new ImageFileManager();
		im.getImageFileInfoById(Integer.parseInt(imageFileId));
		InputStream inputStream = im.getInputStream();
		byte[] content = IOUtil.inputStream2byte(inputStream);
		DocServiceImpl service = new DocServiceImpl();
		DocAttachment da = new DocAttachment();
		//da.setDocid(0);
		da.setImagefileid(Integer.parseInt(imageFileId));
		da.setFilecontent(Base64.encode(content));
		da.setFilerealpath(im.getFileRealPath());
		da.setIszip(Integer.parseInt(im.getIsZip()));
		da.setFilename(im.getImageFileName());
	
		da.setDocfiletype(im.getImageFileType());
		da.setIsextfile("0");
		DocInfo doc = new DocInfo();//创建文档
		doc.setDoccreaterid(3);//
		doc.setDoccreatertype(0);
		doc.setAccessorycount(1);
		doc.setSeccategory(Integer.parseInt(SecId));//子目录id
		doc.setOwnerid(3);
		doc.setDocStatus(1);
		doc.setId(0);
		doc.setDocType(2);
		doc.setDocSubject(im.getImageFileName());
		//doc.setDoccontent();
		doc.setAttachments(new DocAttachment[] { da });
		String session = "";
		int strdocid = -1;
		try {
			session = service.login("ywy", "1", 0, "127.0.0.1");
			strdocid = service.createDoc(doc, session); 
		} catch (Exception e) {
			System.out.println("用户验证未通过");
			e.printStackTrace();
		}
		System.out.println("新文档id:"+strdocid);
		return strdocid + "";
	}
	


}
