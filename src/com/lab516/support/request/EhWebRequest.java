package com.lab516.support.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lab516.base.Consts;
import com.lab516.support.conveter.StringConverter;

public class EhWebRequest extends BindResultRequest {

	public EhWebRequest(ModelAndViewContainer container, NativeWebRequest req) {
		super(req.getNativeRequest(HttpServletRequest.class), container.getModel());
	}

	public Object[] getParameterValues(String paramterName, Class fieldType) throws Exception {
		if (byte[].class.equals(fieldType) || Byte[].class.equals(fieldType)) {
			if (getContentLength() > Consts.MAX_UPLOAD_SIZE) {
				return null;
			}

			List<MultipartFile> files = getFiles(paramterName);
			Object[] results = new Object[files.size()];

			for (int i = 0; i < results.length; i++) {
				MultipartFile file = files.get(i);

				if (file != null) {
					results[i] = file.getBytes();
				}
			}

			return results;
		} else {
			String[] fieldVals = getParameterValues(paramterName);
			return StringConverter.convert(fieldVals, fieldType);
		}
	}

	public MultipartFile getFile(String name) {
		return ((MultipartHttpServletRequest) request).getFile(name);
	}

	public List<MultipartFile> getFiles(String name) {
		return ((MultipartHttpServletRequest) request).getFiles(name);
	}

}
