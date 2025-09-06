package com.exporum.admin.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 23.
 * @description :
 */
@Slf4j
public class XssHttpservletRequestWrapper extends HttpServletRequestWrapper {

    private final String sanitizedBody;

    public XssHttpservletRequestWrapper(HttpServletRequest request)  throws IOException {
        super(request);
        // Content-Type 체크
        if (isMultipartRequest(request)) {
            this.sanitizedBody = null; // 첨부파일 요청은 본문을 처리하지 않음
        } else {
            String body = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            this.sanitizedBody = sanitizeJson(body); // JSON 데이터 정리
        }

        log.info(STR."XSS Filter :::: \{this.sanitizedBody} IP :::: \{request.getRemoteAddr()}" );
    }

    private boolean isMultipartRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return sanitize(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = sanitize(values[i]);
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramMap = super.getParameterMap();
        paramMap.forEach((key, values) -> {
            for (int i = 0; i < values.length; i++) {
                values[i] = sanitize(values[i]);
            }
        });
        return paramMap;
    }

    private String sanitize(String input) {
        return input != null ? StringEscapeUtils.escapeHtml4(input) : null; // HTML 특수문자 이스케이프
    }

    private String sanitizeJson(String input) {
        if (!StringUtils.hasText(input)) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(input, Map.class);

            map.forEach((key, value) -> {
                if (value instanceof String) {
                    map.put(key, sanitize((String) value));
                }
            });
            return objectMapper.writeValueAsString(map);
        } catch (IOException e) {
            throw new RuntimeException("Failed to sanitize JSON", e);
        }
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {

        if (sanitizedBody == null) {
            return super.getInputStream(); // Multipart 요청의 경우 원래 InputStream 반환
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sanitizedBody.getBytes());
        return new DelegatingServletInputStream(byteArrayInputStream);

    }


    private static class DelegatingServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;

        public DelegatingServletInputStream(ByteArrayInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

    }

}
