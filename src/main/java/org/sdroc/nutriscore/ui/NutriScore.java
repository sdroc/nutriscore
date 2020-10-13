/* 
 * Copyright (C) 2020 steph.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.sdroc.nutriscore.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import org.sdroc.nutriscore.domain.Score;

/**
 *
 * @author steph
 */
public class NutriScore extends JRootPane implements NutriScoreSelectionModelListener {

    /**
     * The selection model
     */
    private NutriScoreSelectionModel selectionModel;
    /**
     * The selected component use to display selection
     */
    private final SelectedNutriScoreStep selectScoreStep;

    /**
     * The glass pane used to display section on front
     */
    private final JPanel nutriScoreGlassPane;

    /**
     * The pane that contains law of nutri score
     */
    private final JPanel nutriScoreContentPane;

    /**
     * The extra padding for selection
     */
    private int padding = 50;

    /**
     * The soft transition annimation component
     */
    private final AnimatedNutriScoreStep animatedNutriScoreStep;

    /**
     * Activate soft transition
     */
    private boolean animated = true;

    public boolean isAnimated() {
        return animated;
    }

    /**
     * The constructor
     */
    public NutriScore() {
        nutriScoreContentPane = (JPanel) getContentPane();
        // add border for create a padding
        nutriScoreContentPane.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
        // set minim and preferred size
        setMinimumSize(new Dimension(640, 240));
        setPreferredSize(new Dimension(640, 240));
        setMaximumSize(new Dimension(1024, 480));
        // configure default selection model and attach listener to view
        setSelectionModel(new DefaultNutriScoreSelectionModel());
        // set the layout to place step and create step
        nutriScoreContentPane.setLayout(new GridLayout(1, 5));
        for (Score score : Score.values()) {
            nutriScoreContentPane.add(new NutriScoreStep(score));
        }

        // create select step on glasspane
        selectScoreStep = new SelectedNutriScoreStep();
        // no layout copute directly bounds of component
        nutriScoreGlassPane = new JPanel(null);
        setGlassPane(nutriScoreGlassPane);

        nutriScoreGlassPane.add(selectScoreStep);
        nutriScoreGlassPane.setOpaque(false);
        nutriScoreGlassPane.setVisible(true);
        // attach listener to recompute bounds if conponent size change
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                computeBounds(selectScoreStep);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // Do nothing
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // Do nothing
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // Do nothing
            }
        });
        // create animated manager
        animatedNutriScoreStep = new AnimatedNutriScoreStep(selectScoreStep);
        animatedNutriScoreStep.setDimension(this.getSize());
        this.addComponentListener(animatedNutriScoreStep);
    }

    /**
     * get the selection model
     *
     * @return the selection model
     */
    public NutriScoreSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectionChanged(Score oldScore, Score newScore) {
        if (animated) {
            // start animation
            animatedNutriScoreStep.setSource(oldScore);
            animatedNutriScoreStep.setTarget(newScore);
            animatedNutriScoreStep.start();
        } else {
            selectScoreStep.setScore(newScore);
            computeBounds(selectScoreStep);
        }

    }

    /**
     * Set the selection model
     *
     * @param theSelectionModel the selection model
     */
    private void setSelectionModel(NutriScoreSelectionModel theSelectionModel) {
        if (theSelectionModel != selectionModel) {
            if (selectionModel != null) {
                selectionModel.removeNutriScoreSelectionModelListener(this);
            }
            selectionModel = theSelectionModel;
            selectionModel.addNutriScoreSelectionModelListener(this);
        }

    }

    /**
     * Compute component bounds of selected score
     *
     * @param selectScoreStep
     */
    private void computeBounds(SelectedNutriScoreStep selectScoreStep) {
        int stepSize = (getWidth() - 2 * padding) / Score.values().length;
        selectScoreStep.setBounds((int) (stepSize * selectionModel.getSelection().ordinal()), 0, stepSize + 2 * padding, getHeight());
    }

    /**
     * Activate or Deactivate animation
     *
     * @param enabled Is enabled
     *
     */
    public void setAnimated(boolean enabled) {
        animated = enabled;
    }

}
