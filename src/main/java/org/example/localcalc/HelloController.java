package org.example.localcalc;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class HelloController {

    @FXML private ToggleGroup modeGroup;
    @FXML private RadioButton rbSolid, rbLiquid, rbGas;
    @FXML private GridPane inputGrid;
    @FXML private Label resultLabel;

    private final CalculatorEngine engine = new CalculatorEngine();
    private String currentType = "SOLID";
    private final Map<String, TextField> fields = new HashMap<>();

    @FXML
    public void initialize() {
        modeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                rebuildFields();
            }
        });
        rebuildFields();
    }

    private void rebuildFields() {
        Toggle selected = modeGroup.getSelectedToggle();
        if (selected == null) return;

        currentType = (String) ((RadioButton) selected).getUserData();

        inputGrid.getChildren().clear();
        fields.clear();
        resultLabel.setText("Результат: —");

        List<FuelConfig.Field> config;
        switch (currentType) {
            case "SOLID":
                config = FuelConfig.SOLID;
                break;
            case "LIQUID":
                config = FuelConfig.LIQUID;
                break;
            case "GAS":
                config = FuelConfig.GAS;
                break;
            default:
                config = FuelConfig.SOLID;
                break;
        }

        for (int i = 0; i < config.size(); i++) {
            FuelConfig.Field f = config.get(i);

            Label lbl = new Label(f.label);
            lbl.setStyle("-fx-font-size: 13px;");

            TextField tf = new TextField();
            tf.setPromptText("0");
            tf.setStyle("-fx-font-size: 13px; -fx-pref-width: 160px;");

            tf.setTextFormatter(createNumberFormatter());

            inputGrid.add(lbl, 0, i);
            inputGrid.add(tf, 1, i);
            fields.put(f.key, tf);
        }
    }

    @FXML
    private void onCalculate() {
        try {
            Map<String, Double> inputs = new HashMap<>();
            for (Map.Entry<String, TextField> e : fields.entrySet()) {
                String val = e.getValue().getText().trim();
                if (val.isEmpty()) throw new IllegalArgumentException("Заполните все поля");
                inputs.put(e.getKey(), Double.parseDouble(val));
            }

            if (currentType.equals("GAS")) {
                double sum = inputs.values().stream()
                        .mapToDouble(Double::doubleValue)
                        .sum();
                if (Math.abs(sum - 100.0) > 1.5) {
                    resultLabel.setText("Сумма газов ≠ 100% (сейчас: " + String.format("%.1f", sum) + " %");
                    return;
                }
            }

            double res = engine.calculate(currentType, inputs);
            String unit = currentType.equals("GAS") ? "кДж/м³" : "кДж/кг";
            resultLabel.setText(String.format("НТС: %.0f %s", res, unit));
        } catch (NumberFormatException e) {
            resultLabel.setText("Введите корректные числа");
        }catch (IllegalArgumentException e) {
            resultLabel.setText(e.getMessage());
        }
    }

    private TextFormatter<Double> createNumberFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*\\.?\\d*") && !newText.contains("..")) {
                return change;
            }
            return null;
        };
        return new TextFormatter<>(filter);
    }
}
