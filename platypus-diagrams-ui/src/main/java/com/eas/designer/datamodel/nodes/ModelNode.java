package com.eas.designer.datamodel.nodes;

import com.eas.client.SqlQuery;
import com.eas.client.model.Entity;
import com.eas.client.model.Model;
import com.eas.client.model.Relation;
import com.eas.client.model.gui.IconCache;
import java.awt.Image;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.openide.loaders.DataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;

/**
 * @author mg
 */
public class ModelNode<E extends Entity<?, SqlQuery, E>, R extends Relation<E>, M extends Model<E, SqlQuery>> extends AbstractNode {

    protected static final String DATAMODEL_ICON_NAME = "datamodel16.png"; //NOI18N
    protected DataObject dataObject;

    public ModelNode(ModelNodeChildren<E, R, M> aChildren, DataObject aDataObject) {
        super(aChildren);
        dataObject = aDataObject;
    }

    @Override
    public void destroy() throws IOException {
        ((ModelNodeChildren) getChildren()).removeNotify();
        super.destroy();
    }

    public boolean isVisibleRoot() {
        return true;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public String getName() {
        return dataObject.getName();
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.icon2Image(IconCache.getIcon(DATAMODEL_ICON_NAME));
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    public Node[] entitiesToNodes(Collection<E> entities) {
        if (entities != null) {
            return entities.stream()
                    .map(e -> ((ModelNodeChildren) getChildren()).nodeByEntity(e))
                    .filter(n -> n != null)
                    .collect(Collectors.toList()).toArray(new Node[]{});
        } else {
            return new Node[]{};
        }
    }

    public Node entityToNode(E entity) {
        Set<E> entities = new HashSet<>();
        entities.add(entity);
        Node[] res = entitiesToNodes(entities);
        if (res != null && res.length == 1) {
            return res[0];
        } else {
            return null;
        }
    }

    public Node[] relationsToNodes(Collection<R> relations) {
        if (relations != null) {
            return relations.stream()
                    .map(r -> ((ModelNodeChildren) getChildren()).nodeByRelation(r))
                    .filter(n -> n != null)
                    .collect(Collectors.toList()).toArray(new Node[]{});
        } else {
            return new Node[]{};
        }
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = new Sheet();
        Sheet.Set pSet = Sheet.createPropertiesSet();
        sheet.put(pSet);
        PropertySupport.Name nameProp = new PropertySupport.Name(this, PROP_NAME, "");
        pSet.put(nameProp);
        return sheet;
    }
}
