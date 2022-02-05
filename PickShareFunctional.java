import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
import java.util.function.*;


public class PickShareFunctional {
    
    public static Predicate<ShareInfo> isPriceLessThanLimit;

    public static ShareInfo findHighPriced(Stream<String> symbolStream) {

        // Create a list of share info
        List<ShareInfo> shareInfoList = symbolStream.map(s -> ShareUtil.getPrice(s)).collect(Collectors.toList());

        return shareInfoList.stream().filter(isPriceLessThanLimit).max((x, y) -> x.price.compareTo(y.price)).get();

    }

    public static void main(String[] args) {

        int limit = 500;

        isPriceLessThanLimit = ShareUtil.isPriceLessThan(limit);

        System.out.println("High priced under $" + limit + " is " + findHighPriced(Shares.symbols.stream()));
    }
}
