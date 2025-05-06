package com.fatec.techcollie.builder;

import com.fatec.techcollie.logging.AuditingLogRequest;
import com.fatec.techcollie.model.enums.Action;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class AuditingLogRequestBuilder {

    private final AuditingLogRequest audit = new AuditingLogRequest();

    public AuditingLogRequestBuilder withAction(Action action) {
        this.audit.setAction(action);
        return this;
    }

    public AuditingLogRequestBuilder withEmail(String email) {
        this.audit.setAuthenticatedEmail(email);
        return this;
    }

    public AuditingLogRequestBuilder withRecordId(Integer id) {
        this.audit.setRecordId(id);
        return this;
    }

    public AuditingLogRequestBuilder withTableName(Class<?> clas) {
        Table annotation = clas.getAnnotation(Table.class);
        this.audit.setTableName(annotation == null ? clas.getName() : annotation.name());
        return this;
    }

    public AuditingLogRequest build() {
        return this.audit;
    }
}
