module org.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires com.jfoenix;
    requires org.controlsfx.controls;

    requires java.sql;

    opens org.esprit.model to javafx.base, org.hibernate.orm.core;
    opens org.esprit to javafx.fxml, javafx.graphics;
    exports org.esprit.confirmpage;
    opens org.esprit.confirmpage to javafx.fxml;
    exports org.esprit.newcontract;
    opens org.esprit.newcontract to javafx.fxml;
    exports org.esprit.login;
    opens org.esprit.login to javafx.fxml;
}