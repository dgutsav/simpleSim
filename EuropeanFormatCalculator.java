import java.util.ArrayList;
import java.util.List;

class EuropeanFormatCalculator {
    private static int valC = 80, valE = 256, valN = 576, valM = 16;
    private static int Ac1 = 28, Ac2 = 10, Ac3 = 0, Ac4 = 4;
    private static int Ae1 = 12, Ae2 = 12, Ae3 = 3, Ae4 = 5;
    private static int Af1 = 52, Af2 = 54, Af4 = 5;
    private static int Am2 = 4, Am3 = 6, Am4 = 0;
    private static int targetDist = 18;

    static class EFC_Solution {
        List<Integer> solution;
        double distance;
        String result;

        public EFC_Solution(List<Integer> s, double dist, String res) {
            this.solution = s;
            this.distance = dist;
            this.result = res;
        }

        public String getSolutionString() {
            String str = String.valueOf(this.solution.get(0));
            for (int i = 1; i < this.solution.size(); i++) {
                str = str + "," + this.solution.get(i);
            }
            return str;
        }
    }

    private static int sq(int val, int target) {
        int diff = val - target;
        return diff * diff;
    }

    private static boolean validate(double d) {
        return d % 1 == 0;
    }

    private static String visualize(int c1, int c2, int c3, int c4, int e1, int e2, int e3, int e4, int f1, int f2,
            int f4, int m2, int m3, int m4) {
        double qc1 = c1 / 2.0f;
        double qc2 = (qc1 + c2) / 2.0f;
        double qc3 = (qc2 + c3) / 2.0f;
        double qc4 = (qc3 + c4) / 2.0f;
        double qcFinal = 5.0f; // constant
        if (!validate(qc1))
            return null;
        if (!validate(qc2))
            return null;
        if (!validate(qc3))
            return null;
        if (!validate(qc4))
            return null;

        double qm2 = m2 / 2.0f;
        double qm3 = (qm2 + m3) / 2.0f;
        double qm4 = (qm3 + m4) / 2.0f;
        double qmFinal = 2.0f; // constant
        if (!validate(qm2))
            return null;
        if (!validate(qm3))
            return null;
        if (!validate(qm4))
            return null;

        double qec3 = qc2 / 2.0f;

        double qe1 = e1 / 2.0f;
        double qe2 = (qe1 + e2) / 2.0f;
        double qe3 = (qe2 + e3 + qm2) / 2.0f;
        double qe4 = (qe3 + e4 + qec3 + qc3) / 2.0f;
        double qeFinal = 16.0f; // constant
        if (!validate(qe1))
            return null;
        if (!validate(qe2))
            return null;
        if (!validate(qe3))
            return null;
        if (!validate(qe4))
            return null;

        double qfc2 = qc1 / 2.0f;
        double qfc3 = qfc2 / 2.0f;
        double qfc4 = (qfc3 + qec3) / 2.0f;
        if (!validate(qfc2))
            return null;
        if (!validate(qfc3))
            return null;
        if (!validate(qfc4))
            return null;

        double qf1 = f1 / 2.0f;
        double qf2 = (qf1 + f2 + qe1) / 2.0f;
        double qf3 = (qf2 + qe2) / 2.0f;
        double qf4 = (qf3 + f4 + qe3) / 2.0f;
        double qfFinal = 36.0;
        if (!validate(qf1))
            return null;
        if (!validate(qf2))
            return null;
        if (!validate(qf3))
            return null;
        if (!validate(qf4))
            return null;

        if (qfc4 % 1 == 0 && qfFinal == (qfc4 + qf4 + qe4) && (qe4 + qm3) == qeFinal && qcFinal == qc4
                && qm4 == qmFinal) {
            return "CL CHAMPIONS PATH: " + c1 + " -> " + qc1 + " + " + c2 + " -> " + qc2 + " + " + c3 + " -> " + qc3
                    + " + " + c4 + " -> " + qc4 + "\n" + "CL LEAGUE PATH: " + m2 + " -> " + qm2 + " + " + m3 + " -> "
                    + qm3 + " + " + m4 + " -> " + qm4 + "\n" + "EL CHAMPIONS PATH: " + qc2 + " -> " + qec3 + "\n"
                    + "EL LEAGUE PATH: " + e1 + " -> " + qe1 + " + " + e2 + " -> " + qe2 + " + " + e3 + " + " + qm2
                    + " -> " + qe3 + " + " + e4 + " + " + qec3 + " + " + qc3 + " -> " + qe4 + "\n"
                    + "ECL CHAMPIONS PATH: " + qc1 + " -> " + qfc2 + " -> " + qfc3 + " + " + qec3 + " -> " + qfc4 + "\n"
                    + "ECL LEAGUE PATH: " + f1 + " -> " + qf1 + " + " + f2 + " + " + qe1 + " -> " + qf2 + " + " + qe2
                    + " -> " + qf3 + " + " + f4 + " + " + qe3 + " -> " + qf4 + "\n";
        } else {
            return null;
        }
    }

    private static EFC_Solution selectClosest(int c, int m, int e, int f) {
        double min_distance = Integer.MAX_VALUE;
        String result = null;
        List<Integer> soln = new ArrayList<>();
        for (int c1 = 2; c1 < c; c1 += 2) {
            double qc1 = c1 / 2.0f;
            for (int c2 = 0; c2 < c - c1; c2++) {
                double qc2 = (qc1 + c2) / 2.0f;
                if (!validate(qc2))
                    continue;
                for (int c3 = 0; c3 < c - c1 - c2; c3++) {
                    double qc3 = (qc2 + c3) / 2.0f;
                    if (!validate(qc3))
                        continue;
                    int c4 = c - c1 - c2 - c3;
                    int cVal = c1 + 2 * c2 + 4 * c3 + 8 * c4;
                    if (cVal != valC)
                        continue;
                    for (int m2 = 2; m2 <= m; m2 += 2) {
                        for (int m3 = 0; m3 < m - m2; m3++) {
                            int m4 = m - m2 - m3;
                            int mVal = m2 + 2 * m3 + 4 * m4;
                            if (mVal != valM)
                                continue;
                            for (int e1 = 2; e1 < e; e1 += 2) {
                                for (int e2 = 0; e2 <= e - e1; e2++) {
                                    for (int e3 = 0; e3 <= e - e1 - e2; e3++) {
                                        int e4 = e - e1 - e2 - e3;
                                        int eVal = e1 + 2 * e2 + 4 * e3 + 8 * e4 + 2 * c1 + 4 * c2 + 4 * c3 + 6 * m2
                                                + 8 * m3;
                                        if (eVal != valE)
                                            continue;
                                        for (int f1 = 2; f1 < f; f1 += 2) {
                                            for (int f2 = 0; f2 < f - f1; f2++) {
                                                int f4 = f - f1 - f2;
                                                int nVal = f1 + 2 * f2 + 8 * f4 + 4 * e1 + 6 * e2 + 8 * e3 + 8 * e4
                                                        + 4 * c1 + 6 * c2
                                                        + 4 * c3 + 4 * m2;
                                                if (nVal != valN)
                                                    continue;
                                                double distance = Math.sqrt(sq(c1, Ac1) + sq(c2, Ac2) + sq(c3, Ac3)
                                                        + sq(c4, Ac4) + sq(e1, Ae1) + sq(e2, Ae2) + sq(e3, Ae3)
                                                        + sq(e4, Ae4) + sq(f1, Af1) + sq(f2, Af2) + sq(f4, Af4)
                                                        + sq(m2, Am2) + sq(m3, Am3) + sq(m4, Am4));
                                                if (distance <= targetDist && distance < min_distance) {
                                                    String res = visualize(c1, c2, c3, c4, e1, e2, e3, e4, f1, f2, f4,
                                                            m2, m3, m4);
                                                    if (res != null) {
                                                        result = res;
                                                        soln = new ArrayList<>(List.of(c1, c2, c3, c4, m2, m3, m4, e1,
                                                                e2, e3, e4, f1, f2,
                                                                f4));
                                                        min_distance = distance;
                                                        System.out.println(res);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (result != null) {
            EFC_Solution s = new EFC_Solution(soln, min_distance, result);
            return s;
        } else {
            return null;
        }
    }

    private static String getSolutionText(int c, int m, int e, int f, EFC_Solution s) {
        return "CL(C): " + c + " CL(M): " + m + " EL: " + e + " ECL: " + f + "\n" + s.result +
                "Distance: " + s.distance + "\n" +
                "c1: " + s.solution.get(0) + " c2: " + s.solution.get(1) + " c3: " + s.solution.get(2) + " c4: "
                + s.solution.get(3)
                + "\n" +
                "m2: " + s.solution.get(4) + " m3: " + s.solution.get(5) + " m4: " + s.solution.get(6) + "\n" +
                "e1: " + s.solution.get(7) + " e2: " + s.solution.get(8) + " e3: " + s.solution.get(9) + " e4: "
                + s.solution.get(10)
                + "\n" +
                "f1: " + s.solution.get(11) + " f2: " + s.solution.get(12) + " f4: " + s.solution.get(13) + "\n\n";
    }

    private static String getDemandText(int c, int m, int e, int f) {
        return "CL(C): " + c + " CL(M): " + m + " EL: " + e + " ECL: " + f + "\n";
    }

    private static String getFormatsText(int c, int m, int e, int f, EFC_Solution s) {
        return c + "," + m + "," + e + "," + f + "," + s.getSolutionString() + "\n";
    }

    public static List<Integer> getSolution(int cl, int mcl, int el, int ecl) {
        EFC_Solution s = selectClosest(cl, mcl, el, ecl);
        if (s != null) {
            FileOperations.writeToFile("formats/europe_swiss.csv", getFormatsText(cl, mcl, el, ecl, s));
            return s.solution;
        } else {
            FileOperations.writeToFile("formats/failed_demands.txt", getDemandText(cl, mcl, el, ecl));
            return null;
        }
    }

    public static void main(String args[]) {
        EFC_Solution s = selectClosest(42, 11, 32, 114);
        System.out.println(getSolutionText(42, 11, 32, 114, s));
    }
}