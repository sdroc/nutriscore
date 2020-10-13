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

import java.util.ArrayList;
import java.util.List;
import org.sdroc.nutriscore.domain.Score;

/**
 * An abstract nutri score selection model that implements base listeners
 *
 * @author steph
 */
public abstract class AbstractNutriScoreSelectionModel implements NutriScoreSelectionModel {

    /**
     * list of listener
     */
    private final List<NutriScoreSelectionModelListener> listeners = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNutriScoreSelectionModelListener(NutriScoreSelectionModelListener listener) {
        listeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNutriScoreSelectionModelListener(NutriScoreSelectionModelListener listener) {
        listeners.remove(listener);
    }

    /**
     * Allow to fire events Must be used on derived classes
     *
     * @param oldScore the old nutri score
     * @param newScore the new nutri score
     */
    protected void fireSelectionChanged(Score oldScore, Score newScore) {
        listeners.forEach(listener -> 
            listener.selectionChanged(oldScore, newScore)
        );
    }
}
