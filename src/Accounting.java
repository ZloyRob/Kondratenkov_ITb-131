import java.util.Date;

public class Accounting {
    private Date ds;
    private Date de;
    private int vol;
    private String res;
    private int userId;

    public Accounting(Date ds, Date de, int vol, String res, int userId) {
        this.ds = ds;
        this.de = de;
        this.vol = vol;
        this.res = res;
        this.userId = userId;
    }

    public String toString() {
        String formatString = "Пользователь: %d Ресурс: %s Объем: %d Дата начала: %s Дата окончания: %s";
        return String.format(formatString, userId, res, vol, ds, de);
    }

}
