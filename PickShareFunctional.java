import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;


public class PickShareFunctional {
    
    public static ShareInfo findHighPriced(Stream<String> symbolStream) {

        // Create a list of share info
        List<ShareInfo> shareInfoList = symbolStream.map(s -> ShareUtil.getPrice(s)).collect(Collectors.toList());

        return shareInfoList.stream().filter(ShareUtil.isPriceLessThan(500)).max((x, y) -> x.price.compareTo(y.price)).get();

    }

    public static void main(String[] args) {
        System.out.println("High priced under $500 is " + findHighPriced(Shares.symbols.stream()));
    }
}
