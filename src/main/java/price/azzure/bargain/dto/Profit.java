package price.azzure.bargain.dto;

import lombok.Data;

@Data
public class Profit {
    double profit;
    String date;

    public Profit(double profit, String date) {
        this.profit = profit;
        this.date = date;
    }
}
