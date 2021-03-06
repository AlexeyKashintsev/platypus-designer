package com.eas.designer.application.query;

import net.sf.jsqlparser.parser.ParseException;
import org.openide.text.Annotation;

public class SqlErrorAnnotation extends Annotation {

    protected ParseException ex;

    public SqlErrorAnnotation(ParseException aEx) {
        super();
        ex = aEx;
    }

    @Override
    public String getAnnotationType() {
        return "org-netbeans-spi-editor-hints-parser_annotation_err";
    }

    @Override
    public String getShortDescription() {
        return ex.getMessage();
    }
}