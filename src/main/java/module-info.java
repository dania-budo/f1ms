module me.daniabudo.formula1ms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;
    requires okhttp3;
    requires org.json;
    opens me.daniabudo.formula1ms to javafx.fxml;
    exports me.daniabudo.formula1ms;
}