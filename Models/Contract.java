package Models;

public class Contract {
    private String id;
    private String player_id;
    
    private enum Type {
        Permanent,
        Temporary
    }

    private Type type;
    private int expires;
    private int salary;

    public Contract(String player, Type type, int expires, int salary) {
        this.player_id = player;
        this.type = type;
        this.expires = expires;
        this.salary = salary;
    }

    public static Contract signNewContract(String player, int expires, int salary) {
        return new Contract(player, Type.Permanent, expires, salary);
    }

    public static Contract newLoanDeal(String player, int expires, int salary) {
        return new Contract(player, Type.Temporary, expires, salary);
    }

    public boolean isLoanContract() {
        return this.type == Type.Temporary;
    }

    public int getSalary() {
        return this.salary;
    }

    public int getExpires() {
        return this.expires;
    }

    public void extend(int years) {
        this.expires += years;
    }
}