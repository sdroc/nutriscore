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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.sdroc.nutriscore.domain.Score;

/**
 * Represent select component. This is a mutable class
 *
 * @author steph
 */
public class SelectedNutriScoreStep extends NutriScoreStep {

    /**
     * The constructor
     */
    public SelectedNutriScoreStep() {
        super(Score.A);
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(100f).deriveFont(Font.BOLD));
        setType(NutriScoreStepType.NORMAL);
        roundSize = 80;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Can be improved must be a normal step with selected appearance
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(20));
        GeneralPath path = new GeneralPath();
        path.moveTo(getWidth() - roundSize, 0);
        path.quadTo(getWidth(), 0, getWidth(), roundSize);
        path.lineTo(getWidth(), getHeight() - roundSize);
        path.quadTo(getWidth(), getHeight(), getWidth() - roundSize, getHeight());
        path.lineTo(roundSize, getHeight());
        path.quadTo(0, getHeight(), 0, getHeight() - roundSize);
        path.lineTo(0, roundSize);
        path.quadTo(0, 0, roundSize, 0);
        path.closePath();
        g2d.setClip(path);
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g2d.draw(path);
    }

    /**
     * Set score
     *
     * @param score the score
     */
    public void setScore(Score score) {
        super.setInternalScore(score);

    }

}
