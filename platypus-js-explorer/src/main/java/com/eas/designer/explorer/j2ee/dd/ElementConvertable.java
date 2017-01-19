package com.eas.designer.explorer.j2ee.dd;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Allowing conversion to XML element.
 * @author vv
 */
public interface ElementConvertable {

    /**
     * Converts to XML element.
     * @param aDoc
     * @return XML element
     */
    Element getElement(Document aDoc);
}
