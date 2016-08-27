package com.lab516.service.app;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lab516.base.BaseUtils;

@Service
public class UploadService {

	private final String UPLOAD_DIR = "libupload";

	/** 验证Email的正则 */
	private final Pattern PATTERN = Pattern.compile("[^\\u4E00-\\u9FA5\\w\\.]");

	private final int FILE_PREFIX_LEN = BaseUtils.getUUID().length();

	@Autowired
	private ServletContext context;

	public String save(MultipartFile mtFile) throws IOException {
		String fileName = BaseUtils.getUUID() + mtFile.getOriginalFilename();
		fileName = PATTERN.matcher(fileName).replaceAll("_");
		mtFile.transferTo(findByName(fileName));
		return fileName;
	}

	public void delete(String fileName) throws IOException {
		findByName(fileName).delete();
	}

	public File findByName(String fileName) throws IOException {
		String path = context.getRealPath("/");
		String webappPath = new File(path).getParent();
		String uploadPath = webappPath + File.separator + UPLOAD_DIR;

		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdir();
		}

		File file = new File(uploadPath + File.separator + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}

		return file;
	}

	public String getOriginFileName(File file) throws UnsupportedEncodingException {
		String fileName = file.getName().substring(FILE_PREFIX_LEN);
		return URLEncoder.encode(fileName, "UTF-8");
	}

}
