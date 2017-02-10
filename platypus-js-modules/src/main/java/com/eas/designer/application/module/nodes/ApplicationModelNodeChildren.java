package com.eas.designer.application.module.nodes;

import com.eas.client.model.Relation;
import com.eas.client.model.application.ApplicationDbEntity;
import com.eas.client.model.application.ApplicationDbModel;
import com.eas.client.model.application.ReferenceRelation;
import com.eas.designer.datamodel.nodes.EntityNode;
import com.eas.designer.datamodel.nodes.ModelNodeChildren;
import com.eas.designer.datamodel.nodes.RelationNode;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author mg
 */
public class ApplicationModelNodeChildren extends ModelNodeChildren<ApplicationDbEntity, Relation<ApplicationDbEntity>, ApplicationDbModel> {

    public ApplicationModelNodeChildren(ApplicationDbModel aModel, UndoRedo.Manager aUndoReciever, Lookup aLookup) {
        super(aModel, aUndoReciever, aLookup);
        entitiedUndoRecordrer = new ApplicationNodePropertiesUndoRecorder(aUndoReciever);
    }

    @Override
    protected EntityNode<ApplicationDbEntity> createEntityNode(ApplicationDbEntity key) throws Exception {
        Lookup lkp = Lookups.fixed(key);
        return new ApplicationEntityNode(key, undoReciever, new ProxyLookup(lookup, lkp));
    }

    @Override
    protected RelationNode<ApplicationDbEntity, Relation<ApplicationDbEntity>> createRelationNode(Relation<ApplicationDbEntity> relation) throws Exception {
        Lookup lkp = Lookups.fixed(relation);
        if (relation instanceof ReferenceRelation<?>) {
            return new ReferenceRelationNode((ReferenceRelation<ApplicationDbEntity>) relation, undoReciever, new ProxyLookup(lookup, lkp));
        } else {
            return super.createRelationNode(relation);
        }
    }

    @Override
    protected Collection getKeys() {
        return Stream.concat((Stream<Object>)super.getKeys().stream(),
                model.getReferenceRelations().stream().map(rr -> (Object)rr)).collect(Collectors.toList());
    }

}
