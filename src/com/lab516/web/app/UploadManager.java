package com.lab516.web.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab516.service.app.UploadService;

/**
 * 上传附件的查看和下载
 */
@Controller
@RequestMapping("/app/upload")
public class UploadManager {

	@Autowired
	private UploadService uploadService;

	// 展示图片或pdf
	@RequestMapping("display")
	public void display(String fileName, HttpServletResponse rep) throws Exception {
		File file = uploadService.findByName(fileName);

		try (OutputStream out = rep.getOutputStream(); InputStream in = new FileInputStream(file)) {
			FileCopyUtils.copy(new FileInputStream(file), out);
		}
	}

	// 下载附件
	@RequestMapping("download")
	public void download(HttpServletResponse rep, String fileName) throws Exception {
		File file = uploadService.findByName(fileName);

		try (OutputStream out = rep.getOutputStream(); InputStream in = new FileInputStream(file)) {
			rep.reset();
			rep.setHeader("Content-Disposition", "attachment; filename=" + uploadService.getOriginFileName(file));
			rep.setContentType("application/octet-stream; charset=utf-8");

			FileCopyUtils.copy(in, out);
		}
	}

}
