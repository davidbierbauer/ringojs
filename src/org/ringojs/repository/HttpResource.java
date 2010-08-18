/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ringojs.repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class HttpResource extends AbstractResource
{

    boolean exists = false;
    long contentLength=0;
    long lastModified=-1;
    URL url;

    public HttpResource(URL url)
    {
        this.url = url;
        this.getMeta();
        this.path=url.getPath();
        this.name=url.getFile();
    }

    public long getLength()
    {
        return contentLength;
    }

    public InputStream getInputStream() throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        return connection.getInputStream();
    }

    public long lastModified()
    {
        return lastModified;
    }

    public boolean exists() throws IOException
    {
        return exists;
    }


    public URL getUrl() throws UnsupportedOperationException, MalformedURLException
    {
        return url;
    }

    private void getMeta() 
    {
        try
        {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() < 400)
            {
                lastModified = connection.getLastModified();
                contentLength = connection.getContentLength();
                exists = true;
            }
        } catch (IOException ex)
        {
            Logger.getLogger(HttpResource.class.getName()).log(Level.WARNING, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(HttpResource.class.getName()).log(Level.WARNING, null, ex);
        }
    }
}
