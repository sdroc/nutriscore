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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.Timer;
import org.sdroc.nutriscore.domain.Score;

/**
 * Allaow to animate transition between change of nutri score
 *
 * @author steph
 */
public class AnimatedNutriScoreStep implements ComponentListener {

    /**
     * Max number of frame to animate
     */
    private static final int FRAME_MAX = 25;

    /**
     * listener that compute animation
     */
    private final ActionListenerImpl actionListenerImpl = new ActionListenerImpl();

    /**
     * The timer used to animate
     */
    private final Timer timer;

    /**
     * The construtor
     *
     * @param step the step to animate
     */
    public AnimatedNutriScoreStep(SelectedNutriScoreStep step) {
        timer = new Timer(80, actionListenerImpl);
        actionListenerImpl.setTimer(timer);
        actionListenerImpl.setStep(step);

    }

    /**
     * Start animation when change occur
     */
    public void start() {
        actionListenerImpl.setFrameMax(FRAME_MAX);
        timer.restart();
    }

    /**
     * Set source of change (old score)
     *
     * @param score the score
     */
    void setSource(Score score) {
        actionListenerImpl.setSource(score);
    }

    /**
     * Set target of change (new score)
     *
     * @param score the score
     */
    void setTarget(Score score) {
        actionListenerImpl.setTarget(score);
    }

    public void setDimension(Dimension dimension) {
        actionListenerImpl.setDimension(dimension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void componentResized(ComponentEvent e) {
        actionListenerImpl.componentResized(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void componentMoved(ComponentEvent e) {
        // Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void componentShown(ComponentEvent e) {
        // Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void componentHidden(ComponentEvent e) {
        // Do nothing
    }

    /**
     * The action listener class for animation
     */
    private class ActionListenerImpl implements ActionListener, ComponentListener {

        /**
         * Number of animation frame
         */
        private int frameMax;

        /**
         * Dimension of contaier
         */
        private Dimension containerDimension;

        /**
         * Width of one step
         */
        private double stepWidth;

        /**
         * The mid frame value for change score value in selected component
         */
        private int midFrame;

        /**
         * the computed width of selected component
         */
        private int width;

        /**
         * the computed height of selected component
         */
        private int height;

        /**
         * Set dimension of container
         *
         * @param dimension the dimension
         */
        public void setDimension(Dimension dimension) {
            this.containerDimension = dimension;
        }

        /**
         * Current frame of animation
         */
        private int currentFrame;

        /**
         * Source score. Used to calculate x value
         */
        private Score source;

        /**
         * The timer scheduler
         */
        private Timer timer;

        /**
         * The padding out of selected bounds
         */
        private final int padding = 50;

        /**
         * Target score. Used to calculate x value
         */
        private Score target;

        /**
         * Set the timer to stop at end of animation
         *
         * @param timer
         */
        public void setTimer(Timer timer) {
            this.timer = timer;
        }

        /**
         * Set the source score
         *
         * @param source the score
         */
        public void setSource(Score source) {
            this.source = source;
        }

        /**
         * Set the target score
         *
         * @param target the score
         */
        public void setTarget(Score target) {
            this.target = target;
        }

        /**
         * Set Frame Number used to animate
         *
         * @param frameNumber
         */
        public void setFrameMax(int frameNumber) {
            this.frameMax = frameNumber;
            this.midFrame = frameNumber / 2;
            currentFrame = 0;
        }

        /**
         * Set the step component to animate
         *
         * @param step The step component
         */
        public void setStep(SelectedNutriScoreStep step) {
            this.step = step;
        }
        /**
         * The step component
         */
        private SelectedNutriScoreStep step;

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            currentFrame++;
            computeBounds();
            if (currentFrame == frameMax) {
                timer.stop();
            }
        }

        /**
         * Compute X start position from Score
         *
         * @return
         */
        double computeX(Score score) {
            return stepWidth * score.ordinal();
        }

        /**
         * Compute step width
         *
         * @return width
         */
        private double computeStepWidth() {
            return (containerDimension.getWidth() - 2 * padding) / Score.values().length;
        }

        /**
         * Compute bounds of selected component
         *
         */
        private void computeBounds() {

            if (currentFrame == midFrame) {
                step.setScore(target);
            }
            if (source != null && target != null) {
                double sourceX = computeX(source);
                double targetX = computeX(target);
                double trajectoryLength = computeTrajectoryLength(sourceX, targetX);
                // set bound of selected component acording to current frame
                step.setBounds((int) (sourceX
                        + trajectoryLength * currentFrame / frameMax), 0, width, height);
            }

        }

        /**
         * Get delta between source and target
         *
         * @param sourceValue
         * @param targetValue
         * @return
         */
        private double computeTrajectoryLength(double sourceValue, double targetValue) {
            return targetValue - sourceValue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void componentResized(ComponentEvent e) {

            containerDimension = e.getComponent().getSize();
            stepWidth = computeStepWidth();

            width = (int) (stepWidth + 2 * padding);
            height = (int) containerDimension.getHeight();

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void componentMoved(ComponentEvent e) {
            // Do nothing
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void componentShown(ComponentEvent e) {
            // Do nothing
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void componentHidden(ComponentEvent e) {
            // Do nothing
        }
    }
}
