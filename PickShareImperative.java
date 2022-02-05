import java.math.BigDecimal;
import java.util.function.*;

public class PickShareImperative {
    public static void main(String[] args) {

        int limit = 500;

        ShareInfo highPriced = new ShareInfo("", BigDecimal.valueOf(0));

        final Predicate<ShareInfo> isPriceLessThanLimit = ShareUtil.isPriceLessThan(limit);

        for(String symbol : Shares.symbols) {
            ShareInfo shareInfo = ShareUtil.getPrice(symbol);
            if(isPriceLessThanLimit.test(shareInfo))
                highPriced = ShareUtil.pickHigh(highPriced, shareInfo);
        }

        System.out.println("High priced under $" + limit + " is " + highPriced);
    }
}
