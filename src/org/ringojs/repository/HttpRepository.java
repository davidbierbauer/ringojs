/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ringojs.repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author david
 */
public class HttpRepository extends AbstractRepository
{

    protected URL url;
    protected HttpResource resource;
    protected long lastModified=-1;
    protected boolean exists=false;
    protected Resource[] repositories;

    public HttpRepository(URL url)
    {
        this.url = url;
        this.resource = new HttpResource(url);
        this.lastModified = resource.lastModified;
        this.exists = resource.exists;
        this.path=url.getPath();
        this.name=url.getFile();
        System.out.println("path="+path);
    }

    private URL createCleanURL(String name) throws MalformedURLException
    {
        String path = "";
        String strURL = url.toString();

        if (!strURL.toString().endsWith("/"))
        {
            strURL += "/";
        }

        path = strURL + name;

    //  System.out.println("path=" + path);

        return new URL(path);
    }

    @Override
    protected Resource lookupResource(String name) throws IOException
    {
        return new HttpResource(this.createCleanURL(name));
    }

    @Override
    protected AbstractRepository createChildRepository(String name) throws IOException
    {
        return new HttpRepository(this.createCleanURL(name));
    }

    @Override
    protected void getResources(List<Resource> list, boolean recursive) throws IOException
    {
    }

    public Repository[] getRepositories() throws IOException
    {
        return (Repository[]) repositories;
    }

    public long lastModified()
    {
        return lastModified;
    }

    public long getChecksum() throws IOException
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
}
