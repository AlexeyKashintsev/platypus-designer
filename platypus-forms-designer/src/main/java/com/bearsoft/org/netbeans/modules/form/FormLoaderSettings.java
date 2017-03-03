/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package com.bearsoft.org.netbeans.modules.form;

import java.awt.Color;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import org.openide.util.HelpCtx;
import org.openide.util.NbPreferences;

/**
 * Settings for the form editor.
 */
public class FormLoaderSettings implements HelpCtx.Provider {

    private static final FormLoaderSettings INSTANCE = new FormLoaderSettings();
    /**
     * Property name of the selectionBorderSize property
     */
    public static final String PROP_SELECTION_BORDER_SIZE = "selectionBorderSize"; // NOI18N
    /**
     * Property name of the selectionBorderColor property
     */
    public static final String PROP_SELECTION_BORDER_COLOR = "selectionBorderColor"; // NOI18N
    /**
     * Property name of the connectionBorderColor property
     */
    public static final String PROP_CONNECTION_BORDER_COLOR = "connectionBorderColor"; // NOI18N
    /**
     * Property name of the dragBorderColor property
     */
    public static final String PROP_DRAG_BORDER_COLOR = "dragBorderColor"; // NOI18N
    /**
     * Property name of the guidingLineColor property
     */
    public static final String PROP_GUIDING_LINE_COLOR = "guidingLineColor"; // NOI18N
    /**
     * Property name of the formDesignerBorderColor property
     */
    public static final String PROP_FORMDESIGNER_BORDER_COLOR =
            "formDesignerBorderColor"; // NOI18N
    /**
     * Property name of the gridX property
     */
    public static final String PROP_GRID_X = "gridX"; // NOI18N
    /**
     * Property name of the gridY property
     */
    public static final String PROP_GRID_Y = "gridY"; // NOI18N
    /**
     * Property name of the applyGridToPosition property
     */
    public static final String PROP_APPLY_GRID_TO_POSITION = "applyGridToPosition"; // NOI18N
    /**
     * Property name of the applyGridToSize property
     */
    public static final String PROP_APPLY_GRID_TO_SIZE = "applyGridToSize"; // NOI18N
    static final int AUTO_NAMING_ON = 1;
    /**
     * Property name of the toolBarPalette property
     */
    public static final String PROP_PALETTE_IN_TOOLBAR = "toolBarPalette"; // NOI18N
    /**
     * Property name of the assistantShown property.
     */
    public static final String PROP_ASSISTANT_SHOWN = "assistantShown"; // NOI18N
    /**
     * Property name of the designerLAF property.
     */
    public static final String PROP_DESIGNER_LAF = "designerLAF"; // NOI18N
    static final int AUTO_RESOURCE_DEFAULT = 0;
    static final int AUTO_RESOURCE_ON = 1;
    static final int AUTO_RESOURCE_OFF = 2;
    /**
     * Array of package names to search for property editors used in Form Editor
     */
    private static final int MIN_SELECTION_BORDER_SIZE = 1;
    private static final int MAX_SELECTION_BORDER_SIZE = 15;
    private static final int MIN_GRID_X = 2;
    private static final int MIN_GRID_Y = 2;

    public static Preferences getPreferences() {
        return NbPreferences.forModule(FormLoaderSettings.class);
    }

    public static FormLoaderSettings getInstance() {
        return INSTANCE;
    }

    /**
     * Getter for the selectionBorderSize option.
     *
     * @return selection border size.
     */
    public int getSelectionBorderSize() {
        return getPreferences().getInt(PROP_SELECTION_BORDER_SIZE, 1);
    }

    /**
     * Setter for the selectionBorderSize option.
     *
     * @param value selection border size.
     */
    public void setSelectionBorderSize(int value) {
        if (value < MIN_SELECTION_BORDER_SIZE) {
            value = MIN_SELECTION_BORDER_SIZE;
        } else if (value > MAX_SELECTION_BORDER_SIZE) {
            value = MAX_SELECTION_BORDER_SIZE;
        }


        getPreferences().putInt(PROP_SELECTION_BORDER_SIZE, value);
    }

    /**
     * Getter for the selectionBorderColor option.
     *
     * @return color of selection border.
     */
    public java.awt.Color getSelectionBorderColor() {
        int rgb = getPreferences().getInt(PROP_SELECTION_BORDER_COLOR, new Color(255, 164, 0).getRGB());
        return new Color(rgb);
    }

    /**
     * Setter for the selectionBorderColor option
     *
     * @param value color of selection border.
     */
    public void setSelectionBorderColor(java.awt.Color value) {
        if (value == null) {
            return;
        }
        getPreferences().putInt(PROP_SELECTION_BORDER_COLOR, value.getRGB());
    }

    /**
     * Getter for the connectionBorderColor option.
     *
     * @return color of connection border.
     */
    public java.awt.Color getConnectionBorderColor() {
        int rgb = getPreferences().getInt(PROP_CONNECTION_BORDER_COLOR, Color.red.getRGB());
        return new Color(rgb);
    }

    /**
     * Setter for the connectionBorderColor option.
     *
     * @param value color of connection border.
     */
    public void setConnectionBorderColor(java.awt.Color value) {
        if (value == null) {
            return;
        }
        getPreferences().putInt(PROP_CONNECTION_BORDER_COLOR, value.getRGB());
    }

    /**
     * Getter for the dragBorderColor option.
     *
     * @return color of drag border.
     */
    public java.awt.Color getDragBorderColor() {
        int rgb = getPreferences().getInt(PROP_DRAG_BORDER_COLOR, Color.gray.getRGB());
        return new Color(rgb);
    }

    /**
     * Setter for the dragBorderColor option.
     *
     * @param value color of drag border.
     */
    public void setDragBorderColor(java.awt.Color value) {
        if (value == null) {
            return;
        }
        getPreferences().putInt(PROP_DRAG_BORDER_COLOR, value.getRGB());
    }

    /**
     * Getter for the guidingLineColor option.
     *
     * @return color of guiding lines.
     */
    public java.awt.Color getGuidingLineColor() {
        int rgb = getPreferences().getInt(PROP_GUIDING_LINE_COLOR, new Color(143, 171, 196).getRGB());
        return new Color(rgb);

    }

    /**
     * Setter for the dragBorderColor option.
     *
     * @param value color of guiding lines.
     */
    public void setGuidingLineColor(java.awt.Color value) {
        if (value == null) {
            return;
        }
        getPreferences().putInt(PROP_GUIDING_LINE_COLOR, value.getRGB());
    }

    /**
     * Getter for the gridX option.
     *
     * @return size of horizontal grid.
     */
    public int getGridX() {
        return getPreferences().getInt(PROP_GRID_X, 10);
    }

    /**
     * Setter for the gridX option.
     *
     * @param value size of horizontal grid.
     */
    public void setGridX(int value) {
        if (value < MIN_GRID_X) {
            value = MIN_GRID_X;
        }
        getPreferences().putInt(PROP_GRID_X, value);
    }

    /**
     * Getter for the gridY option.
     *
     * @return size of vertical grid.
     */
    public int getGridY() {
        return getPreferences().getInt(PROP_GRID_Y, 10);
    }

    /**
     * Setter for the gridY option.
     *
     * @param value size of vertical grid.
     */
    public void setGridY(int value) {
        if (value < MIN_GRID_Y) {
            value = MIN_GRID_Y;
        }
        getPreferences().putInt(PROP_GRID_Y, value);
    }

    /**
     * Getter for the applyGridToPosition option.
     *
     * @return determines whether position of component should snap to grid.
     */
    public boolean getApplyGridToPosition() {
        return getPreferences().getBoolean(PROP_APPLY_GRID_TO_POSITION, true);
    }

    /**
     * Setter for the applyGridToPosition option.
     *
     * @param value determines whether position of component should snap to
     * grid.
     */
    public void setApplyGridToPosition(boolean value) {
        getPreferences().putBoolean(PROP_APPLY_GRID_TO_POSITION, value);
    }

    /**
     * Getter for the applyGridToSize option.
     *
     * @return determines whether size of component should snap to grid.
     */
    public boolean getApplyGridToSize() {
        return getPreferences().getBoolean(PROP_APPLY_GRID_TO_SIZE, true);

    }

    /**
     * Setter for the applyGridToSize option.
     *
     * @param value determines whether size of component should snap to grid.
     */
    public void setApplyGridToSize(boolean value) {
        getPreferences().putBoolean(PROP_APPLY_GRID_TO_SIZE, value);
    }

    public int getAutoSetComponentName() {
        return AUTO_NAMING_ON;
    }

    public boolean isPaletteInToolBar() {
        return getPreferences().getBoolean(PROP_PALETTE_IN_TOOLBAR, false);
    }

    public void setPaletteInToolBar(boolean value) {
        getPreferences().putBoolean(PROP_PALETTE_IN_TOOLBAR, value);
    }

    /**
     * Getter for the formDesignerBackgroundColor option.
     *
     * @return background color of the designer.
     */
    public java.awt.Color getFormDesignerBackgroundColor() {
        return new JButton().getBackground();

    }

    /**
     * Getter for the formDesignerBorderColor option.
     *
     * @return color of the border of the designer.
     */
    public java.awt.Color getFormDesignerBorderColor() {
        int rgb = getPreferences().getInt(PROP_FORMDESIGNER_BORDER_COLOR, new Color(224, 224, 255).getRGB());
        return new Color(rgb);

    }

    /**
     * Setter for the formDesignerBorderColor option.
     *
     * @param value color of the border of the designer.
     */
    public void setFormDesignerBorderColor(java.awt.Color value) {
        if (value == null) {
            return;
        }
        getPreferences().putInt(PROP_FORMDESIGNER_BORDER_COLOR, value.getRGB());
    }

    /**
     * Getter for the assistantShown option.
     *
     * @return <code>true</code> if the assistant should be shown, *      * return <code>false</code> otherwise.
     */
    public boolean getAssistantShown() {
        return getPreferences().getBoolean(PROP_ASSISTANT_SHOWN, true);
    }

    /**
     * Setter for the foldGeneratedCode option.
     *
     * @param value determines whether the assistant should be shown.
     */
    public void setAssistantShown(boolean value) {
        getPreferences().putBoolean(PROP_ASSISTANT_SHOWN, value);
    }

    /**
     * This method must be overriden. It returns display name of this options.
     *
     * @return display name.
     */
    public String displayName() {
        return FormUtils.getBundleString("CTL_FormSettings"); // NOI18N
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("gui.configuring"); // NOI18N
    }
}
