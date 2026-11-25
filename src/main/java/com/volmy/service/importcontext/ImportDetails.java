package com.volmy.service.importcontext;

public class ImportDetails {

    private String importId;
    private ImportStatus status;
    private Integer progress;

    public ImportDetails(String importId) {
        this.importId = importId;
        this.progress = 0;
        this.status = ImportStatus.NOT_STARTED;
    }

    public String getImportId() {
        return this.importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public Integer getProgress() {
        return this.progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public ImportStatus getStatus() {
        return this.status;
    }

    public void setStatus(ImportStatus status) {
        this.status = status;
    }
}
