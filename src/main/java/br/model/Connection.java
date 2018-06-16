package br.model;

import com.mongodb.MongoClient;
import br.model.entities.Base;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class Connection {

    private Connection() {

    }

    private static Datastore datastore;

    public static Datastore get() {

        if (Connection.datastore == null) {

            Morphia m = new Morphia();

            m.mapPackage( Base.class.getPackage().getName() );

            Connection.datastore = m.createDatastore(new MongoClient(), "company");
        }

        return Connection.datastore;
    }
}
