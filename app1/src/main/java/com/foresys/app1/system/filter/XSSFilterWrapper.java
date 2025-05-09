package com.foresys.app1.system.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class XSSFilterWrapper extends HttpServletRequestWrapper {

	private byte[] rawData;

	/** RequestBody XSS 필터의 처리부 */
	public XSSFilterWrapper(HttpServletRequest request) {
		super(request);

		try {
			InputStream inputStream = request.getInputStream();
			this.rawData = replaceXSS(IOUtils.toByteArray(inputStream));
		} catch (Exception e) {
			log.error("XSSFilterWrapper Error :: ", e);
		}
	}

	private byte[] replaceXSS(byte[] data) {
		String strData = new String(data);

		strData = strData
				.replaceAll("\\<", "&lt;")
				.replaceAll("\\>", "&gt;")
				.replaceAll("\\(", "&#40;")
				.replaceAll("\\)", "&#41;")
				.replaceAll("\\\'", "&#39;")
				.replaceAll("\\#", "&#35;");

		return strData.getBytes();
	}

	private String replaceXSS(String value) {

		if(value != null) {
			value = value
					.replaceAll("\\<", "&lt;")
					.replaceAll("\\>", "&gt;")
					.replaceAll("\\(", "&#40;")
					.replaceAll("\\)", "&#41;")
					.replaceAll("\\\'", "&#39;")
					.replaceAll("\\#", "&#35;");
		}

		return value;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		if (this.rawData == null) {
			return super.getInputStream();
		}

		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				// TODO Auto-generated method stub
				return byteArrayInputStream.read();
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

	@Override
	public String getQueryString() {
		return replaceXSS(super.getQueryString());
	}

	@Override
	public String getParameter(String name) {
		return replaceXSS(super.getParameter(name));
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> params = super.getParameterMap();

		if (params != null) {
			params.forEach((key, value) -> {

				for (int i = 0; i < value.length; i++) {
					value[i] = replaceXSS(value[i]);

				}
			});
		}

		return params;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] params = super.getParameterValues(name);

		if (params != null) {

			for (int i = 0; i < params.length; i++) {
				params[i] = replaceXSS(params[i]);

			}
		}

		return params;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
	}
}
