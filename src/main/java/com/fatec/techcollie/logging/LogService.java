package com.fatec.techcollie.logging;

import com.fatec.techcollie.model.enums.Action;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final EntityManager entityManager;

    @Value("${spring.profiles.active}")
    private String profile;

    public void insertIntoLog(AuditingLogRequest audit) {
        String description = getDescription(audit);

        if (profile.equals("dev")) {
            entityManager.createNativeQuery("call insert_into_log(:authenticatedEmail, :action, :description, :tableName)")
                    .setParameter("authenticatedEmail", audit.getAuthenticatedEmail())
                    .setParameter("action", audit.getAction().name())
                    .setParameter("description", description)
                    .setParameter("tableName", audit.getTableName())
                    .executeUpdate();
        }
    }

    private String getDescription(AuditingLogRequest audit) {
        return switch (audit.getAction()) {
            case Action.INSERT -> String.format("Registro com id '%s' criado", audit.getRecordId());
            case Action.UPDATE -> String.format("Registro com id '%s' atualizado", audit.getRecordId());
            case Action.DELETE -> String.format("Registro com id '%s' excluido", audit.getRecordId());
        };
    }
}
