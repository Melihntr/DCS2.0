package CleanCode;

/**
 * C1.3: Koşul Optimizasyonu
 * Amaç: İç içe geçmiş if bloklarını temizlemek ve okunabilirliği artırmak.
 */
public class ConditionOptimization {

    // ❌ KÖTÜ YAKLAŞIM: İç içe geçmiş (Nested) koşullar
    public String checkAccessBad(int age, boolean hasPermission) {
        String result;
        if (age >= 18) {
            if (hasPermission) {
                result = "Erişim Onaylandı";
            } else {
                result = "Yetki Yok";
            }
        } else {
            result = "Yaş Yetersiz";
        }
        return result;
    }

    // ✅ İYİ YAKLAŞIM: Guard Clauses (Erken Dönüş)
    public String checkAccessGood(int age, boolean hasPermission) {
        // Negatif durumları başta eleyip metoddan çıkıyoruz
        if (age < 18) {
            return "Yaş Yetersiz";
        }

        if (!hasPermission) {
            return "Yetki Yok";
        }

        // Pozitif ana akış en sonda tertemiz kalıyor
        return "Erişim Onaylandı";
    }

    public static void main(String[] args) {
        ConditionOptimization example = new ConditionOptimization();
        System.out.println(example.checkAccessGood(20, true));
        System.out.println(example.checkAccessGood(15, false));
    }
}
