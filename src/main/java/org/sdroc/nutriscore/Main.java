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
package org.sdroc.nutriscore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.sdroc.nutriscore.domain.Score;
import org.sdroc.nutriscore.service.NutriScoreService;
import org.sdroc.nutriscore.ui.NutriScore;
import org.sdroc.nutriscore.ui.NutriScoreFrame;

/**
 * The main application
 *
 * @author steph
 */
public class Main implements KeyListener {

    /**
     * The nutri score service
     */
    private final NutriScoreService nutriScoreService;
    /**
     * The nutriScoreFrame
     */
    private NutriScoreFrame nutriScoreFrame;
    /**
     * The nutriScore
     */
    private NutriScore nutriScore;
    /**
     * The score label
     */
    private JLabel nutriScoreLabel;

    /**
     * The score label
     */
    private JLabel nutriScoreTitle;

    /**
     * NutriScore singleton
     *
     * @return the NutriScore
     */
    private NutriScore getNutriScore() {
        if (nutriScore == null) {
            nutriScore = new NutriScore();
        }
        return nutriScore;
    }

    /**
     * NutriScoreFrame singleton
     *
     * @return the NutriScoreFrame
     */
    private NutriScoreFrame getNutriScoreFrame() {
        if (nutriScoreFrame == null) {
            nutriScoreFrame = new NutriScoreFrame();
        }
        return nutriScoreFrame;
    }

    /**
     * Label score singleton
     *
     * @return the labelScore
     */
    private JLabel getNutriScoreLabel() {
        if (nutriScoreLabel == null) {
            nutriScoreLabel = new JLabel("_");
            nutriScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nutriScoreLabel.setFont(nutriScoreLabel.getFont().deriveFont(30f));
        }
        return nutriScoreLabel;
    }

    /**
     * Label score singleton
     *
     * @return the labelScore
     */
    private JLabel getNutriScoreTitle() {
        if (nutriScoreTitle == null) {
            nutriScoreTitle = new JLabel("_");
            nutriScoreTitle.setHorizontalAlignment(SwingConstants.CENTER);
            nutriScoreTitle.setFont(nutriScoreTitle.getFont().deriveFont(100f));
            nutriScoreTitle.setForeground(Color.DARK_GRAY);
            nutriScoreTitle.setText("NUTRI-SCORE");
        }
        return nutriScoreTitle;
    }

    /**
     * The application main instance
     *
     */
    public Main() {
        nutriScoreService = new NutriScoreService();
        getNutriScoreFrame().getContentPane().add(getNutriScoreTitle(), BorderLayout.PAGE_START);
        getNutriScoreFrame().getContentPane().add(getNutriScore());
        getNutriScoreFrame().getContentPane().add(getNutriScoreLabel(), BorderLayout.PAGE_END);

        getNutriScoreFrame().pack();

        getNutriScoreFrame().addKeyListener(this);
        // activate stream and connect to ui
        nutriScoreService.getScoreStream().forEach((Score score) -> {
            LOG.info(String.format("Receive %s nutri score ", score.toString()));
            getNutriScore().getSelectionModel().setSelection(score);
            getNutriScoreLabel().setText(score.toString());
        });

    }
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    /**
     * The application entry point
     *
     * @param args nothing
     */
    public static void main(String[] args) {
        new Main();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            getNutriScore().setAnimated(!getNutriScore().isAnimated());
            LOG.info(String.format("Animated soft transition %s", getNutriScore().isAnimated()));
        }
    }

}
