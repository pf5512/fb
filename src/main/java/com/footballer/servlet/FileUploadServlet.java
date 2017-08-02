package com.footballer.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by ian on 7/26/14.
 */
public class FileUploadServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);

    private static final String TMP_DIR_PATH = "/tmp";
    private File tmpDir;
    private static final String DESTINATION_DIR_PATH ="/opt/fb";
    private File destinationDir;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        tmpDir = new File(TMP_DIR_PATH);
        logger.debug("Temp file path:", TMP_DIR_PATH);
        if(!tmpDir.isDirectory()) {
            throw new ServletException(TMP_DIR_PATH + " is not a directory");
        }

        //String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
        destinationDir = new File(DESTINATION_DIR_PATH);
        logger.debug("Target path is:", DESTINATION_DIR_PATH);
        if(!destinationDir.isDirectory()) {
            throw new ServletException(DESTINATION_DIR_PATH+" is not a directory");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("start to upload file ...");
        PrintWriter out = response.getWriter();
        
        response.setContentType("text/plain");
        response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers",
				"origin, content-type, accept, authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
        

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory ();

        // Set the size threshold, above which content will be stored on disk.
        fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB

        // Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(tmpDir);

        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

        uploadHandler.setFileSizeMax(10*1024*1024); //10 MB

        try {
            // Parse the request
            List items = uploadHandler.parseRequest(request);
            Iterator itr = items.iterator();
            while(itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                // Handle Form Fields.
                if(item.isFormField()) {
                    out.println("File Name = "+item.getFieldName()+", Value = "+item.getString());
                } else {

                    //Handle Uploaded files.
                    out.println("Field Name = "+item.getFieldName()+
                            ", File Name = "+item.getName()+
                            ", Content type = "+item.getContentType()+
                            ", File Size = "+item.getSize());

                    // Write file to the ultimate location.
                    String randomPath = UUID.randomUUID().toString();
                    String path = DESTINATION_DIR_PATH + "/" + randomPath;

                    logger.debug("new file is uploaded:", path + "/" + item.getName());

                    File file = new File(path, item.getName());
                    
                    file.getParentFile().mkdir();
                    file.createNewFile();

                    out.println("File path:" + file.getPath());
                    item.write(file);
                }
                out.close();
            }
        } catch(FileUploadException ex) {
            log("Error encountered while parsing the request",ex);
        } catch(Exception ex) {
            log("Error encountered while uploading file",ex);
        } finally {
            if (null != out) {
                out.close();
            }
        }

    }
}
