package com.gl.ceir.config.feature.usermanual;

public class FeatureDocumentDTO {
    private String featureName;
    private String lang;
    private String docName;


    public FeatureDocumentDTO(String featureName, String lang, String docName) {
        this.featureName = featureName;
        this.lang = lang;
        this.docName = docName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeatureDocumentDTO{");
        sb.append("featureName='").append(featureName).append('\'');
        sb.append(", lang='").append(lang).append('\'');
        sb.append(", docName='").append(docName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
