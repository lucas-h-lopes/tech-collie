package com.fatec.techcollie.logging;

import com.fatec.techcollie.model.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditingLogRequest {

    private String authenticatedEmail;
    private Object recordId;
    private String tableName;
    private Action action;
}
