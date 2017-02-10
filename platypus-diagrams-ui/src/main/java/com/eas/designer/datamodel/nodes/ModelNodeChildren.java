package com.eas.designer.datamodel.nodes;

import com.eas.client.SqlQuery;
import com.eas.client.model.Entity;
import com.eas.client.model.Model;
import com.eas.client.model.ModelEditingListener;
import com.eas.client.model.Relation;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.openide.ErrorManager;
import org.openide.awt.UndoRedo;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author mg
 */
public abstract class ModelNodeChildren<E extends Entity<?, SqlQuery, E>, R extends Relation<E>, M extends Model<E, SqlQuery>> extends Children.Keys<Object> implements ModelEditingListener<E> {

    protected M model;
    protected NodePropertiesUndoRecorder entitiedUndoRecordrer;
    protected UndoRedo.Manager undoReciever;
    protected Lookup lookup;
    protected java.util.Map<E, EntityNode> entitiesLocator;
    protected java.util.Map<R, RelationNode> relationsLocator;

    public ModelNodeChildren(M aModel, UndoRedo.Manager aUndoReciever, Lookup aLookup) {
        super();
        model = aModel;
        undoReciever = aUndoReciever;
        entitiedUndoRecordrer = new EntityNodePropertiesUndoRecorder(aUndoReciever);
        lookup = aLookup;
    }

    protected void validateLocators() {
        if (entitiesLocator == null) {
            entitiesLocator = new HashMap<>();
        }
        if (relationsLocator == null) {
            relationsLocator = new HashMap<>();
        }
        Node[] lnodes = getNodes();// May be implicit locator = null code invocation
        for (Node node : lnodes) {
            if (node instanceof EntityNode) {
                EntityNode<E> entityNode = (EntityNode<E>) node;
                entitiesLocator.put(entityNode.getEntity(), entityNode);
            } else if (node instanceof RelationNode) {
                RelationNode<E, R> relationNode = (RelationNode<E, R>) node;
                relationsLocator.put(relationNode.getRelation(), relationNode);
            }
        }
    }

    public EntityNode nodeByEntity(E e) {
        validateLocators();
        return entitiesLocator.get(e);
    }

    public RelationNode nodeByRelation(R r) {
        validateLocators();
        return relationsLocator.get(r);
    }

    @Override
    protected void addNotify() {
        entitiesLocator = null;
        relationsLocator = null;
        model.addEditingListener(this);
        setKeys(getKeys());
    }

    @Override
    public void removeNotify() {
        entitiesLocator = null;
        relationsLocator = null;
        model.removeEditingListener(this);
        setKeys(Collections.EMPTY_SET);
    }

    @Override
    protected Node[] createNodes(Object key) {
        try {
            entitiesLocator = null;
            relationsLocator = null;
            Node createdNode = key instanceof Entity ? createEntityNode((E) key) : createRelationNode((R) key);
            createdNode.addPropertyChangeListener(entitiedUndoRecordrer);
            return new Node[]{createdNode};
        } catch (Exception ex) {
            ErrorManager.getDefault().notify(ex);
            return null;
        }
    }

    protected Collection getKeys() {
        return model.getEntities().values();
    }

    @Override
    protected void destroyNodes(Node[] aNodes) {
        try {
            entitiesLocator = null;
            relationsLocator = null;
            for (Node node : aNodes) {
                node.removePropertyChangeListener(entitiedUndoRecordrer);
                node.destroy();
            }
            super.destroyNodes(aNodes);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    protected abstract EntityNode<E> createEntityNode(E key) throws Exception;

    protected RelationNode<E, R> createRelationNode(R key) throws Exception {
        Lookup lkp = Lookups.fixed(key);
        return new RelationNode(key, undoReciever, new ProxyLookup(lookup, lkp));
    }

    @Override
    public void entityAdded(E e) {
        setKeys(getKeys());
    }

    @Override
    public void entityRemoved(E e) {
        setKeys(getKeys());
    }

    @Override
    public void relationAdded(Relation<E> aRelation) {
        setKeys(getKeys());
    }

    @Override
    public void relationRemoved(Relation<E> aRelation) {
        setKeys(getKeys());
    }

    @Override
    public void entityIndexesChanged(E e) {
    }
}
