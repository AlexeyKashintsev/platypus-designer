package com.eas.designer.application.module;

import com.eas.client.model.application.ApplicationDbEntity;
import com.eas.client.model.application.ApplicationDbModel;
import com.eas.client.model.Relation;
import com.eas.designer.datamodel.nodes.ModelNode;

/**
 *
 * @author mg
 */
public interface ModelProvider {

    public ApplicationDbModel getModel() throws Exception;

    public ModelNode<ApplicationDbEntity, Relation<ApplicationDbEntity>, ApplicationDbModel> getModelNode() throws Exception;
    
}
