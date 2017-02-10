package com.eas.designer.application.report;

import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.MultiFileLoader;


public class PlatypusReportDataObjectXLS extends PlatypusReportDataObject {

    @MIMEResolver.ExtensionRegistration(displayName="#LBL_PlatypusReport_layout_file", extension="xls", mimeType="application/ms-excel")
    public PlatypusReportDataObjectXLS(FileObject aJsFile, MultiFileLoader aLoader) throws Exception {
        super(aJsFile, aLoader);
    }
    
}
