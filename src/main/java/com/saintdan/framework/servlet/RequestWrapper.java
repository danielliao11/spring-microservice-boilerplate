package com.saintdan.framework.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 * Multiple reader for {@link HttpServletRequest}.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 20/10/2017
 * @since JDK1.8
 */
public class RequestWrapper extends HttpServletRequestWrapper {

  private ByteArrayOutputStream cachedBytes;

  public RequestWrapper(HttpServletRequest request) {
    super(request);
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    if (cachedBytes == null) {
      cacheInputStream();
    }

    return new CachedServletInputStream();
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream()));
  }

  private void cacheInputStream() throws IOException {
    /* Cache the inputstream in order to read it multiple times. For
     * convenience, I use apache.commons IOUtils
     */
    cachedBytes = new ByteArrayOutputStream();
    IOUtils.copy(super.getInputStream(), cachedBytes);
  }

  /* An inputstream which reads the cached request body */
  public class CachedServletInputStream extends ServletInputStream {

    private ByteArrayInputStream byteArrayInputStream;

    public CachedServletInputStream() {
      /* create a new input stream from the cached request body */
      byteArrayInputStream = new ByteArrayInputStream(cachedBytes.toByteArray());
    }

    @Override
    public int read() throws IOException {
      return byteArrayInputStream.read();
    }

    @Override
    public boolean isFinished() {
      return byteArrayInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
      throw new RuntimeException("Not implemented");
    }
  }
}
