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
package org.sdroc.nutriscore.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.net.ssl.HttpsURLConnection;

import org.sdroc.nutriscore.domain.Score;

/**
 * Service used to retrieve nutri score values
 *
 * @author steph
 */
public class NutriScoreService {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(NutriScoreService.class.getName());
    /**
     * Stream use to read random integer
     */
    private final Stream<Integer> stream;
    /**
     * Request for random integer
     */
    private static final String REQUEST = "https://www.random.org/integers/?num=1&min=0&max=4&col=1&base=10&format=plain&rnd=new";

    /**
     * Lock object for synchronize thread
     */
    private final Object lock = new Object();

    /**
     * Allow to retrieve immediatly first value
     */
    private boolean nextValue = false;

    public NutriScoreService() {
        stream = Stream.iterate(0, i -> {
            synchronized (lock) {
                Integer value;
                do {
                    value = getScoreValue();
                    if (value == null) {
                        LOG.warning("Cannot retrieve nutri score");
                    }
                    try {
                        if (nextValue) {
                            lock.wait(10000);
                        } else {
                            nextValue = true;
                        }
                    } catch (InterruptedException ex) {
                        LOG.log(Level.SEVERE, null, ex);
                    }

                } while (value == null);
                return value;
            }

        });
    }

    /**
     * Create score Stream
     *
     * @return the score steam
     */
    public Stream<Score> getScoreStream() {

        return stream.map((Integer i) -> {
            return Score.values()[i];
        });
    }

    /**
     * Connect to url and retrieve an integer between 0 to 4 value;
     *
     * @return the integer between 0 to 4
     */
    public Integer getScoreValue() {
        try {

            URL myUrl = new URL(REQUEST);
            HttpsURLConnection conn = (HttpsURLConnection) myUrl.openConnection();
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            try (BufferedReader br = new BufferedReader(isr)) {
                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    return Integer.valueOf(inputLine);
                }
            }

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
