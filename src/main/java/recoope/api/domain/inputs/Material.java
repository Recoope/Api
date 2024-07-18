package recoope.api.domain.inputs;

import jakarta.validation.constraints.NotNull;

public class Material {
    @NotNull
    private String material;

    public String get() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
