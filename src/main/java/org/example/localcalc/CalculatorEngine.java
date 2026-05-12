package org.example.localcalc;

import java.util.Map;

public class CalculatorEngine {

    public double calculate(String type, Map<String, Double> inputs) {
        switch (type) {
            case "SOLID":
            case "LIQUID":
                double c = inputs.getOrDefault("C", 0.0);
                double h = inputs.getOrDefault("H", 0.0);
                double o = inputs.getOrDefault("O", 0.0);
                double s = inputs.getOrDefault("S", 0.0);
                double w = inputs.getOrDefault("W", 0.0);
                return calculateSolidLiquid(c, h, o, s, w);
            case "GAS":
                double ch4 = inputs.getOrDefault("CH4", 0.0);
                double c2h6 = inputs.getOrDefault("C2H6", 0.0);
                double c3h8 = inputs.getOrDefault("C3H8", 0.0);
                double c4h10 = inputs.getOrDefault("C4H10", 0.0);
                double c5h12 = inputs.getOrDefault("C5H12", 0.0);
                double co = inputs.getOrDefault("CO", 0.0);
                double h2 = inputs.getOrDefault("H2", 0.0);
                return calculateGas(ch4, c2h6, c3h8, c4h10, c5h12, co, h2);
            default:
                return 0.0;
        }
    }

    public double calculateSolidLiquid(double C, double H, double O, double S, double W) {
        return 339 * C + 1256 * H - 109 * (O - S) - 25.14 * (9 * H + W);
    }

    public double calculateGas(double CH4, double C2H6, double C3H8, double C4H10, double C5H12, double CO, double H2) {
        return 358 * CH4 + 638 * C2H6 + 913 * C3H8 + 1187 * C4H10 + 1461 * C5H12 + 127 * CO + 108 * H2;
    }
}
