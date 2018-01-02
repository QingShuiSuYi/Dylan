package com.weavernorth.servlet.file;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weaver.file.FileUpload;
import weaver.general.DynamicServlet;
/**
 * 保存附件到E8
 * @author Dylan
 *
 */
public class Upload extends DynamicServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doProcess(HttpServletRequest request,HttpServletResponse response) throws ServletException,
			IOException {
		FileUpload fu = new FileUpload(request);
		String[] fileFieldNames = {"file1"};
		String[] imageids = fu.uploadFiles(fileFieldNames);
		System.out.println("imageids:" + Arrays.toString(imageids));
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(Arrays.toString(imageids));
	}

}
