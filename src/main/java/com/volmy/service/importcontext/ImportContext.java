package com.volmy.service.importcontext;

import jakarta.inject.Singleton;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ImportContext {

    private final Map<String, ImportDetails> imports = new ConcurrentHashMap<>();

    public String startNew() {
        String importId = UUID.randomUUID().toString();
        this.imports.put(importId, new ImportDetails(importId));
        return importId;
    }

    public void update(String importId, Integer progress, ImportStatus status) {
        ImportDetails importDetails = this.imports.get(importId);
        if (importDetails != null) {
            importDetails.setProgress(progress);
            importDetails.setStatus(status);
        }
    }

    public ImportDetails get(String importId) {
        return this.imports.get(importId);
    }

    public void complete(String importId) {
        ImportDetails importDetails = this.imports.get(importId);
        importDetails.setProgress(100);
        importDetails.setStatus(ImportStatus.SUCCESS);
    }
}
