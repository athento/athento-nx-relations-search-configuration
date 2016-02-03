/*
 * (C) Copyright 2007 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Nuxeo - initial API and implementation
 *
 * $Id: DocumentModelFunctions.java 30568 2008-02-25 18:52:49Z ogrisel $
 */

package org.nuxeo.ecm.platform.ui.web.tag.fn;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.core.Manager;
import org.nuxeo.ecm.core.NXCore;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentLocation;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.impl.DocumentLocationImpl;
import org.nuxeo.ecm.core.api.model.DocumentPart;
import org.nuxeo.ecm.core.api.model.Property;
import org.nuxeo.ecm.core.lifecycle.LifeCycle;
import org.nuxeo.ecm.core.lifecycle.LifeCycleException;
import org.nuxeo.ecm.core.lifecycle.LifeCycleService;
import org.nuxeo.ecm.core.schema.FacetNames;
import org.nuxeo.ecm.core.schema.SchemaManager;
import org.nuxeo.ecm.core.schema.types.ComplexType;
import org.nuxeo.ecm.core.schema.types.Field;
import org.nuxeo.ecm.core.schema.types.ListType;
import org.nuxeo.ecm.core.schema.types.Schema;
import org.nuxeo.ecm.core.schema.types.Type;
import org.nuxeo.ecm.core.utils.DocumentModelUtils;
import org.nuxeo.ecm.directory.DirectoryException;
import org.nuxeo.ecm.directory.Session;
import org.nuxeo.ecm.directory.api.DirectoryService;
import org.nuxeo.ecm.platform.mimetype.interfaces.MimetypeEntry;
import org.nuxeo.ecm.platform.mimetype.interfaces.MimetypeRegistry;
import org.nuxeo.ecm.platform.types.TypeManager;
import org.nuxeo.ecm.platform.types.adapter.TypeInfo;
import org.nuxeo.ecm.platform.ui.web.directory.DirectoryFunctions;
import org.nuxeo.ecm.platform.ui.web.directory.DirectoryHelper;
import org.nuxeo.ecm.platform.ui.web.rest.RestHelper;
import org.nuxeo.ecm.platform.ui.web.rest.api.URLPolicyService;
import org.nuxeo.ecm.platform.ui.web.util.BaseURL;
import org.nuxeo.ecm.platform.ui.web.util.ComponentUtils;
import org.nuxeo.ecm.platform.url.DocumentViewImpl;
import org.nuxeo.ecm.platform.url.api.DocumentView;
import org.nuxeo.ecm.platform.url.codec.DocumentFileCodec;
import org.nuxeo.runtime.api.Framework;

/**
 * Document model functions.
 *
 * @author <a href="mailto:at@nuxeo.com">Anahide Tchertchian</a>
 * @author <a href="mailto:og@nuxeo.com">Olivier Grisel</a>
 */
public final class AthentoFunctions implements LiveEditConstants {

    private static final Log log = LogFactory.getLog(DocumentModelFunctions.class);

    private static final String JSESSIONID = "JSESSIONID";

    private static final String i18n_prefix = "%i18n";

    private static final String NXEDIT_URL_VIEW_ID = "nxliveedit.faces";

    private static final String NXEDIT_URL_SCHEME = "nxedit";

    private static MimetypeRegistry mimetypeService;

    private static TypeManager typeManagerService;

    private static DirectoryService dirService;

    private static LifeCycleService lifeCycleService;

    // static cache of default viewId per document type shared all among
    // threads
    private static final Map<String, String> defaultViewCache = Collections.synchronizedMap(new HashMap<String, String>());

    // Utility class.
    private AthentoFunctions() {
    }

    
    public static DocumentModel getDocumentById (final String documentId){
    	 DocumentModel doc = null;
    	 final CoreSession coreSession = (CoreSession) Component.getInstance("documentManager");
         if (StringUtils.isNotBlank(documentId)) {
             try {
                 doc = coreSession.getDocument(new IdRef(documentId));
             } catch (ClientException e) {
                 log.info(String.format("Could not find document with id %s", documentId));
             }
         }
         return doc;	
    }
    
    public static boolean getDocProperty(DocumentModel document){
    	boolean value= false;
	    if (document != null) {	  
	    	
	    	value = (Boolean) document.getProperty("athentosetup:Light_Search").getValue();
	    	
	    }
    	return value;			
    }
}
