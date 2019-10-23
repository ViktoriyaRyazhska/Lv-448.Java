package academy.softserve.museum.entities.dto;

import academy.softserve.museum.entities.ExhibitType;

import java.util.List;

public class ExhibitDto {
    private ExhibitType type;
    private String name;
    private String material;
    private String technique;
    private Long audienceId;
    private List<Long> authorsId;

    public ExhibitDto(ExhibitType type, String name, String material, String technique, Long audienceId, List<Long> authorsId) {
        this.type = type;
        this.name = name;
        this.material = material;
        this.technique = technique;
        this.audienceId = audienceId;
        this.authorsId = authorsId;
    }

    public ExhibitType getType() {
        return type;
    }

    public void setType(ExhibitType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public Long getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(Long audienceId) {
        this.audienceId = audienceId;
    }

    public List<Long> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<Long> authorsId) {
        this.authorsId = authorsId;
    }
}
