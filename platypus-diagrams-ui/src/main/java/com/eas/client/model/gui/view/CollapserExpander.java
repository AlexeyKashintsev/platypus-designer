package com.eas.client.model.gui.view;

import com.bearsoft.routing.QuadTree;
import com.eas.client.model.Entity;
import com.eas.client.model.gui.edits.MoveEntityEdit;
import com.eas.client.model.gui.view.entities.EntityView;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.undo.UndoableEditSupport;

/**
 *
 * @author mg
 */
public class CollapserExpander {

    public static <E extends Entity<?, ?, E>> void expand(QuadTree<EntityView<E>> aEntitiesIndex, EntityView<E> eView, Rectangle aField, int dy, UndoableEditSupport aUndoSupport) {
        Rectangle futureBounds = new Rectangle(eView.getBounds());
        futureBounds.height += dy;
        List<EntityView<E>> testStops = findIntersecting(aEntitiesIndex, futureBounds);
        if (testStops != null && !testStops.isEmpty()) {
            Set<EntityView<E>> lprocessed = new HashSet<>();
            List<EntityView<E>> l2Move = new ArrayList<>();
            l2Move.add(eView);
            int i = 0;
            while (i < l2Move.size()) {
                EntityView<E> lEntityComponent = l2Move.get(i);
                if (!lprocessed.contains(lEntityComponent)) {
                    Rectangle tilBottom = new Rectangle();
                    Rectangle bounds = lEntityComponent.getBounds();
                    tilBottom.x = bounds.x;
                    tilBottom.y = bounds.y + bounds.height;
                    tilBottom.width = bounds.width;
                    tilBottom.height = aField.height - tilBottom.y;

                    List<EntityView<E>> lstops = findIntersecting(aEntitiesIndex, tilBottom);
                    lstops.remove(eView);
                    l2Move.addAll(lstops);

                    if (lEntityComponent != eView) {
                        Point llocation = lEntityComponent.getLocation();
                        Point lnewlocation = new Point(llocation.x, llocation.y + dy);
                        assert lEntityComponent instanceof EntityView<?>;
                        MoveEntityEdit<E> ledit = new MoveEntityEdit<>(lEntityComponent.getEntity(), llocation, lnewlocation);
                        ledit.redo();
                        aUndoSupport.postEdit(ledit);
                    }
                    lprocessed.add(lEntityComponent);
                }
                ++i;
            }
        }
    }

    protected static <E extends Entity<?, ?, E>> List<EntityView<E>> sortRectsByTop(List<EntityView<E>> toSort) {
        List<EntityView<E>> lres = new ArrayList<>();
        Map<Integer, List<EntityView<E>>> lsorter = new TreeMap<>();
        for (int i = 0; i < toSort.size(); i++) {
            EntityView<E> lelement = toSort.get(i);
            int ltop = lelement.getLocation().y;
            List<EntityView<E>> lsortElement = lsorter.get(ltop);
            if (lsortElement == null) {
                lsortElement = new ArrayList<>();
                lsorter.put(ltop, lsortElement);
            }
            lsortElement.add(lelement);
        }
        Set<Entry<Integer, List<EntityView<E>>>> lentries = lsorter.entrySet();
        if (lentries != null) {
            for (Entry<Integer, List<EntityView<E>>> le : lentries) {
                if (le != null && le.getValue() != null) {
                    List<EntityView<E>> lsortElement = le.getValue();
                    lres.addAll(lsortElement);
                }
            }
        }
        return lres;
    }

    public static <E extends Entity<?, ?, E>> void collapse(QuadTree<EntityView<E>> aEntitiesIndex, EntityView<E> eView, Rectangle aField, int dy, UndoableEditSupport aUndoSupport) {
        Set<EntityView<E>> lprocessed = new HashSet<>();
        List<EntityView<E>> l2Move = new ArrayList<>();
        l2Move.add(eView);
        int i = 0;
        while (i < l2Move.size()) {
            EntityView<E> lentity = l2Move.get(i);
            if (!lprocessed.contains(lentity)) {
                Rectangle tilBottom = new Rectangle();
                Rectangle bounds = lentity.getBounds();
                tilBottom.x = bounds.x;
                tilBottom.y = bounds.y + bounds.height;
                tilBottom.width = bounds.width;
                tilBottom.height = aField.height - tilBottom.y;

                List<EntityView<E>> lstops = findIntersecting(aEntitiesIndex, tilBottom);
                l2Move.addAll(lstops);
                lprocessed.add(lentity);
            }
            ++i;
        }
        lprocessed.clear();
        List<EntityView<E>> lsorted = sortRectsByTop(l2Move);
        for (i = 0; i < lsorted.size(); i++) {
            EntityView<E> lEntityView = lsorted.get(i);
            if (lEntityView != eView && !lprocessed.contains(lEntityView)) {
                Rectangle aboveBounds = lEntityView.getBounds();
                aboveBounds.y -= dy;
                aboveBounds.height = dy;

                boolean linsetsContains = true;
                aEntitiesIndex.remove(lEntityView.getBounds(), lEntityView);
                try {
                    List<EntityView<E>> res = findIntersecting(aEntitiesIndex, aboveBounds);
                    linsetsContains = res != null && !res.isEmpty();
                } finally {
                    aEntitiesIndex.insert(lEntityView.getBounds(), lEntityView);
                }
                if (!linsetsContains) {
                    Point llocation = lEntityView.getLocation();
                    Point lnewlocation = new Point(llocation.x, llocation.y - dy);
                    MoveEntityEdit<E> ledit = new MoveEntityEdit<>(lEntityView.getEntity(), llocation, lnewlocation);
                    ledit.redo();
                    aUndoSupport.postEdit(ledit);
                }
            }
            lprocessed.add(lEntityView);
        }
    }

    private static <E extends Entity<?, ?, E>> List<EntityView<E>> findIntersecting(QuadTree<EntityView<E>> aEntitiesIndex, Rectangle aCriterion) {
        List<EntityView<E>> res = aEntitiesIndex.query(aCriterion);
        for (int i = res.size() - 1; i >= 0; i--) {
            if (!res.get(i).getBounds().intersects(aCriterion)) {
                res.remove(i);
            }
        }
        return res;
    }
}
