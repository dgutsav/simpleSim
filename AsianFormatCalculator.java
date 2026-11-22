import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AsianFormatCalculator {
    public static List<Integer> getSolutionList(int acle, int target_acle, int acl2, int target_acl2, int acgl,
            int target_acgl) {
        List<Integer> sol = new ArrayList<>();
        double dist = Double.MAX_VALUE;
        for (int e1 = 0; e1 <= acle; e1 += 2) {
            for (int e2 = 0; e2 <= acle - e1; e2++) {
                int e3 = acle - e1 - e2;
                double qe1 = e1 / 2.0f;
                double qe2 = (e2 + qe1) / 2.0f;
                if (qe2 != Math.floor(qe2))
                    continue;
                double val_e = e3 + qe2;
                if (val_e == target_acle) {
                    for (int c1 = 0; c1 <= acl2; c1 += 2) {
                        for (int c2 = 0; c2 <= acl2 - c1; c2++) {
                            int c3 = acl2 - c1 - c2;
                            double qc1 = c1 / 2.0f;
                            double qc2 = (c2 + qc1) / 2.0f;
                            if (qc2 != Math.floor(qc2))
                                continue;
                            double val_c = c3 + qc2 + qe1 + qe2;
                            if (val_c == target_acl2) {
                                for (int g1 = 0; g1 <= acgl; g1 += 2) {
                                    for (int g2 = 0; g2 <= acgl - g1; g2++) {
                                        int g3 = acgl - g1 - g2;
                                        double qg1 = g1 / 2.0f;
                                        double qg2 = (g2 + qg1) / 2.0f;
                                        if (qg2 != Math.floor(qg2))
                                            continue;
                                        double val_g = g3 + qg2 + qc1 + qc2;
                                        if (val_g == target_acgl) {
                                            double d = c1 + e1 + g1;
                                            if (d < dist) {
                                                dist = d;
                                                System.out.println(
                                                        "e1: " + e1 + " e2: " + e2 + " e3: " + e3 + " c1: " + c1
                                                                + " c2: "
                                                                + c2 + " c3: " + c3 + " g1: " + g1 + " g2: " + g2
                                                                + " g3: " + g3 + " distance: " + dist);
                                                int[] s = { e1, e2, e3, c1, c2, c3, g1, g2, g3 };
                                                sol = Arrays.stream(s).boxed().toList();
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
        return sol;
    }

    private static String getFormatsText(int acle, int target_acle, int acl2, int target_acl2, int acgl,
            int target_acgl, List<Integer> s) {
        int[] arr = { acle, target_acle, acl2, target_acl2, acgl, target_acgl };
        List<Integer> x = new ArrayList<>(Arrays.stream(arr).boxed().toList());
        x.addAll(s);
        String str = String.valueOf(x.get(0));
        for (int i = 1; i < x.size(); i++) {
            str = str + "," + x.get(i);
        }
        str = str + "\n";
        return str;
    }

    public static List<Integer> getSolution(int acle, int target_acle, int acl2, int target_acl2, int acgl,
            int target_acgl) {
        List<Integer> s = getSolutionList(acle, target_acle, acl2, target_acl2, acgl, target_acgl);
        if (s != null && s.size() > 0) {
            FileOperations.writeToFile("formats/asia.csv",
                    getFormatsText(acle, target_acle, acl2, target_acl2, acgl, target_acgl, s));
            return s;
        } else {
            return null;
        }
    }

    public static void main(String args[]) {
        getSolutionList(5, 3, 5, 6, 8, 5);
    }
}