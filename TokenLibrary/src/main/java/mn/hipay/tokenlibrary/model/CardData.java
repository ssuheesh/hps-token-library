package mn.hipay.tokenlibrary.model;

public class CardData {
    public String cardId;
    public String tokenId;
    public String bin;
    public String last4digits;
    public String holder;
    public String expiryMonth;
    public String expiryYear;
    public String cardbrand;
    public String bankno;
    public String bankname;

    public CardData(String cardId, String tokenId, String bin, String last4digits, String holder, String expiryMonth, String expiryYear, String cardbrand, String bankno, String bankname) {
        this.cardId = cardId;
        this.tokenId = tokenId;
        this.bin = bin;
        this.last4digits = last4digits;
        this.holder = holder;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cardbrand = cardbrand;
        this.bankno = bankno;
        this.bankname = bankname;
    }


    @Override
    public String toString() {
        return "CardData{" +
                "cardId='" + cardId + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", bin='" + bin + '\'' +
                ", last4digits='" + last4digits + '\'' +
                ", holder='" + holder + '\'' +
                ", expiryMonth='" + expiryMonth + '\'' +
                ", expiryYear='" + expiryYear + '\'' +
                ", cardbrand='" + cardbrand + '\'' +
                ", bankno='" + bankno + '\'' +
                ", bankname='" + bankname + '\'' +
                '}';
    }
}
