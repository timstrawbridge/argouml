// $Id$
// Copyright (c) 1996-99 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.uml.ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.model.ModelFacade;
import org.argouml.uml.diagram.activity.ui.UMLActivityDiagram;
import org.argouml.uml.diagram.collaboration.ui.UMLCollaborationDiagram;
import org.argouml.uml.diagram.deployment.ui.UMLDeploymentDiagram;
import org.argouml.uml.diagram.sequence.ui.UMLSequenceDiagram;
import org.argouml.uml.diagram.state.ui.UMLStateDiagram;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.argouml.uml.diagram.use_case.ui.UMLUseCaseDiagram;

/**
 * UMTreeCellRenderer determines how the entries in the Navigationpane
 * and ToDoList will be represented graphically.
 *
 * <p>In particular it makes decisions about the icons
 *  to use in order to display a Navigationpane artifact depending on the kind
 *  of object to be displayed.
 *
 * This class must be efficient as it is called many 1000's of times.
 */
public class UMLTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final String BUNDLE = "UMLMenu";

    // get localised strings once only
    private static String activity = Translator.localize(BUNDLE, "label.activity-diagram");
    private static String sequence = Translator.localize(BUNDLE, "label.sequence-diagram");
    private static String collaboration = Translator.localize(BUNDLE, "label.collaboration-diagram");
    private static String deployment = Translator.localize(BUNDLE, "label.deployment-diagram");
    private static String state = Translator.localize(BUNDLE, "label.state-chart-diagram");
    private static String usecase = Translator.localize(BUNDLE, "label.usecase-diagram");
    private static String classDiag = Translator.localize(BUNDLE, "label.class-diagram");
    private static String name = Translator.localize(BUNDLE, "label.name");
    private static String typeName = Translator.localize(BUNDLE, "label.type");
    
    ////////////////////////////////////////////////////////////////
    // TreeCellRenderer implementation

    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean _hasFocus) {
            
            if(value instanceof DefaultMutableTreeNode){
                value = ((DefaultMutableTreeNode)value).getUserObject();
            }
        
        Component r =
            super.getTreeCellRendererComponent(
                tree,
                value,
                sel,
                expanded,
                leaf,
                row,
                _hasFocus);

        if (r instanceof JLabel) {
            JLabel lab = (JLabel) r;
            
            Icon icon =
                ResourceLoaderWrapper.getResourceLoaderWrapper().lookupIcon(
                    value);

            if (icon != null)
                lab.setIcon(icon);

            // setting the tooltip
            String type = null;
            if (ModelFacade.isAModelElement(value)) {
                type = ModelFacade.getUMLClassName(value);
            } else if (value instanceof UMLDiagram) {
                if (value instanceof UMLActivityDiagram)
                    type = activity;
                else if (value instanceof UMLSequenceDiagram)
                    type = sequence;
                else if (value instanceof UMLCollaborationDiagram)
                    type = collaboration;
                else if (value instanceof UMLDeploymentDiagram)
                    type = deployment;
                else if (value instanceof UMLStateDiagram)
                    type = state;
                else if (value instanceof UMLUseCaseDiagram)
                    type = usecase;
                else if (value instanceof UMLClassDiagram)
                    type = classDiag;
            }
            
            if (type != null) {
                StringBuffer buf = new StringBuffer();
                buf.append("<html>");
                buf.append(name);
                buf.append(' ');
                buf.append(lab.getText());
                buf.append("<br>");
                buf.append(typeName);
                buf.append(' ');
                buf.append(type);
                lab.setToolTipText(buf.toString());
            }
            else {
                lab.setToolTipText(lab.getText());
            }
        }
        return r;
    }

} /* end class UMLTreeCellRenderer */
