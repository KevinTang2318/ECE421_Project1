import java.math.BigDecimal;
import java.util.function.*;

public class PickShareImperative {
    public static void main(String[] args) {

        ShareInfo highPriced = new ShareInfo("", BigDecimal.valueOf(0));

        final Predicate<ShareInfo> isPriceLessThan500 = ShareUtil.isPriceLessThan(500);

        for(String symbol : Shares.symbols) {
            ShareInfo shareInfo = ShareUtil.getPrice(symbol);
            if(isPriceLessThan500.test(shareInfo))
                highPriced = ShareUtil.pickHigh(highPriced, shareInfo);
        }

        System.out.println("High priced under $500 is " + highPriced);
    }
}
