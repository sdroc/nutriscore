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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.sdroc.nutriscore.domain.Score;

/**
 * Define a step element of nutri score. other side
 *
 * @author steph
 */
public class NutriScoreStep extends JLabel {

    /**
     * The type. Can be START END for each limit of nutri score SELECTED for
     * elected apearance
     */
    private NutriScoreStepType type;
    /**
     * Round size for corner of nutri score
     */
    protected double roundSize = 40;

    /**
     * Set the type Change appearance of step
     *
     * @param theType The type
     */
    final public void setType(NutriScoreStepType theType) {
        type = theType;
    }

    public enum NutriScoreStepType {
        START,
        NORMAL,
        END
    }
    /**
     * The color palette
     */
    private static final Color[] STEP_COLORS = {Color.decode("#009142"),
        Color.decode("#70d421"),
        Color.decode("#f8e000"),
        Color.decode("#f08700"),
        Color.decode("#ec1800")
    };

    /**
     * Constructor with score. The NutriScore step is immutable
     *
     * @param score the score
     */
    public NutriScoreStep(Score score) {
        setInternalScore(score);
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(new Color(255, 255, 255, 128));
        setFont(getFont().deriveFont(70f));

        switch (score) {
            case A:
                setType(NutriScoreStepType.START);
                break;
            case E:
                setType(NutriScoreStepType.END);
                break;
            default:
                setType(NutriScoreStepType.NORMAL);
                break;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clipping(g2d);

        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Manage clipping for start and and steps
     *
     * @param g2d the graphic 
     */
    private void clipping(Graphics2D g2d) {
        GeneralPath path = null;
        // only create Path when start or end step
        // TODO can be calculated when component size change and keep 2 distinct paths
        if (type == NutriScoreStepType.START || type == NutriScoreStepType.END) {
            path = new GeneralPath();
            path.moveTo(0, roundSize);
            path.quadTo(0, 0, roundSize, 0);
            path.lineTo(getWidth(), 0);
            path.lineTo(getWidth(), getHeight());
            path.lineTo(roundSize, getHeight());
            path.quadTo(0, getHeight(), 0, getHeight() - roundSize);
            path.closePath();

        }
        // clip start
        if (type == NutriScoreStepType.START) {
            g2d.setClip(path);
        }
        // clip end use affine for inverse path
        if (type == NutriScoreStepType.END) {
            // path is initialized
            path.transform(AffineTransform.getScaleInstance(-1, 1));
            path.transform(AffineTransform.getTranslateInstance(getWidth(), 0));
            g2d.setClip(path);
        }
    }

    /**
     * Set score Color and text set
     *
     * @param score
     */
    protected final void setInternalScore(Score score) {
        setText(score.toString());
        setBackground(STEP_COLORS[score.ordinal()]);
    }
}
