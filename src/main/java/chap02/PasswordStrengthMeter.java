package chap02;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {
        if (s==null || s.trim().isEmpty()) return PasswordStrength.INVALID;

        int metCounts = getCountOfMet(s);
        if (metCounts <= 1) return PasswordStrength.WEAK;
        if (metCounts == 2) return PasswordStrength.REGULAR;
        return PasswordStrength.STRONG;
    }
    private int getCountOfMet(String s){
        int metCounts=0;
        if (s.length() >= 8) metCounts++;
        if (existNumber(s)) metCounts++;
        if (existUpperCase(s)) metCounts++;
        return metCounts;
    }

    private boolean existNumber(String s){
        for (int i=0; i<s.length(); i++){
            if ('1' <= s.charAt(i) & '9' >= s.charAt(i)) return true;
        }
        return false;
    }
    private boolean existUpperCase(String s){
        for (char c : s.toCharArray()){
            if (Character.isUpperCase(c)) return true;
        }
        return false;
    }
}
