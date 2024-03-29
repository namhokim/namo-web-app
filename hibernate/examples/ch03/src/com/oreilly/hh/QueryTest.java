package com.oreilly.hh;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.oreilly.hh.data.*;

import java.sql.Time;
import java.util.*;

/**
 * Retrieve data as objects
 */
public class QueryTest {

    /**
     * Retrieve any tracks that fit in the specified amount of time.
     *
     * @param length the maximum playing time for tracks to be returned.
     * @param session the Hibernate session that can retrieve data.
     * @return a list of {@link Track}s meeting the length restriction.
     */
    public static List tracksNoLongerThan(Time length, Session session) {
		Query query = session.createQuery("from Track as track " +
                          "where track.playTime <= :length");
        query.setTime("length", length);
        return query.list();
    }   

    /**
     * Look up and print some tracks when invoked from the command line.
     */
    public static void main(String args[]) throws Exception {
        // Create a configuration based on the properties file we've put
        // in the standard place.
        Configuration config = new Configuration();
        config.configure();

        // Get the session factory we can use for persistence
        SessionFactory sessionFactory = config.buildSessionFactory();

        // Ask for a session using the JDBC information we've configured
        Session session = sessionFactory.openSession();
        try {
            // Print the tracks that will fit in five minutes
            List tracks = tracksNoLongerThan(Time.valueOf("00:05:00"),
                                             session);
            for (ListIterator iter = tracks.listIterator() ;
                 iter.hasNext() ; ) {
                Track aTrack = (Track)iter.next();
                System.out.println("Track: \"" + aTrack.getTitle() +
                                   "\", " + aTrack.getPlayTime());
            }
        } finally {
            // No matter what, close the session
            session.close();
        }

        // Clean up after ourselves
        sessionFactory.close();
    }
}
