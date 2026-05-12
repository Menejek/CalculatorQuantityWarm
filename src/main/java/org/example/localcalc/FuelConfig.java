package org.example.localcalc;

import java.util.List;

public class FuelConfig {

    public static class Field {
        public final String key;
        public final String label;
        public Field(String key, String label) {
            this.key = key;
            this.label = label;
        }
    }

    public static final List<Field> SOLID = List.of(
            new Field("C", "Углерод (C), %"), new Field("H", "Водород (H), %"),
            new Field("O", "Кислород (O), %"), new Field("S", "Сера (S), %"), new Field("W", "Влажность (W), %")
    );

    public static final List<Field> LIQUID = List.of(
            new Field("C", "Углерод (C), %"), new Field("H", "Водород (H), %"),
            new Field("S", "Сера (S), %"), new Field("O", "Кислород (O), %"), new Field("W", "Влажность (W), %")
    );

    public static final List<Field> GAS = List.of(
            new Field("CH4", "Метан (CH₄), %"), new Field("C2H6", "Этан (C₂H₆), %"),
            new Field("C3H8", "Пропан (C₃H₈), %"), new Field("C4H10", "Бутан (C₄H₁₀), %"),
            new Field("CO", "Угарный газ (CO), %"), new Field("H2", "Водород (H₂), %"),
            new Field("N2", "Азот (N₂), %")
    );


}
