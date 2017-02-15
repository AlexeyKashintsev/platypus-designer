package com.eas.client.model.gui.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;

/**
 *
 * @author mg
 */
public class IconsListCellRenderer extends DefaultListCellRenderer {

    protected List<Icon> extraIcons = new ArrayList<>();
        
    public void addIcon(Icon anIcon) {
        if (getIcon() == null) {
            setIcon(anIcon);
        } else {
            extraIcons.add(anIcon);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        extraIcons.stream().forEach((icon) -> {
            icon.paintIcon(this, g, 2, 0);
        });
    }
}
