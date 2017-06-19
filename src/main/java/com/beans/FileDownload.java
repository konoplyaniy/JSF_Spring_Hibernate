package com.beans;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.InputStream;

@ManagedBean
public class FileDownload {

    private StreamedContent file;

    public FileDownload() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/HostIniSwitcher.7z");
        file = new DefaultStreamedContent(stream, "", "HostIniSwitcher.7z");
    }

    public StreamedContent getFile() {
        return file;
    }
}

