package co.com.crediya.sendemail;

import org.springframework.beans.factory.annotation.Value;

public final class NotificationConstants {
    private NotificationConstants() {}
    public static final String SUBJECT_TEMPLATE = "Reporte diario";
    public static final String BODY_TEMPLATE = "Estimado/a usuario,\n\nTotal aprobados: %d, Monto total: %s";
}

